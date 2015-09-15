package com.animals.app.controller.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.animals.app.service.googleoauth.Google2Api;
import com.animals.app.service.googleoauth.ServiceBuilder;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.scribe.builder.api.FacebookApi;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import com.animals.app.domain.User;
import com.animals.app.domain.UserRole;
import com.animals.app.domain.UserType;
import com.animals.app.repository.UserRepository;
import com.animals.app.repository.Impl.UserRepositoryImpl;

/**
 * Created by 41X on 8/16/2015.
 */
@Path("account")
@PermitAll
public class OAuthAuthorizationResource {
	
	private static Logger LOG = LogManager.getLogger(OAuthAuthorizationResource.class);
	
	private final Response NOT_FOUND = Response.status(Response.Status.NOT_FOUND).build();
	private final Response SERVER_ERROR = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

	private UserRepository userRep = new UserRepositoryImpl();

	// Google OAuth preferences
	private static final String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json";
	private static final String SCOPE = "https://mail.google.com/ https://www.googleapis.com/auth/userinfo.email";
	private static final Token EMPTY_TOKEN = null;
	private static final String callbackUrlPathG = "webapi/account/login/google_token";

	// Facebook OAuth preferences
	private static final String PROTECTED_RESOURCE_URL_FB = "https://graph.facebook.com/me";
	private static final String PROTECTED_RESOURCE_URL_FB2 = "https://graph.facebook.com/me?fields=picture.type(large)";
	private static final String callbackUrlGPathFacebook = "webapi/account/login/facebook_token";

	// Twitter OAuth preferences
	private static final String PROTECTED_RESOURCE_URL_TW = "https://api.twitter.com/1.1/account/verify_credentials.json";
	private static final String callbackUrlGPathTW = "webapi/account/login/twitter_token";

	@GET
	@Path("login/google") // http:localhost:8080/webapi/account/login/google
	public Response googleLogin(@Context HttpServletRequest req) {

		// Define URLs and callback
		String callbackUrlG = defineURL(req, "webapi/account/login/google", callbackUrlPathG);

		OAuthService service = null;
		
		try {
			service = new ServiceBuilder()
					.provider(Google2Api.class)
					.apiKey(socialsConfig.getProperty("google.apiKey")) 
					.apiSecret(socialsConfig.getProperty("google.apiSecret"))
					.callback(callbackUrlG)
					.scope(SCOPE)
					.offline(true)
					.build();

		} catch (Exception e) {
			LOG.error(e);
		}

		if (service == null) {
			return NOT_FOUND;
		}

		String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
		
		return Response.status(Response.Status.OK).entity(authorizationUrl).build();
	}
	

	@GET
	@Path("login/google_token") // http://localhost:8080/webapi/account/login/google_token
	public Response getGoogleAccessToken(@QueryParam("code") String token,
										 @QueryParam("error") String error,
										 @Context HttpServletRequest req) {

		// Define URLs and callback
		String callbackUrlG = defineURL(req, "webapi/account/login/google_token", callbackUrlPathG);	
		String pathMain = definePathMain(req, "webapi/account/login/google_token");
		String successURL = defineSuccessUrl(req, "webapi/account/login/google_token"); 
		

		if (error != null) {
			String entryUrl = pathMain + "/#/ua/user/login";
			return Response.temporaryRedirect(UriBuilder.fromUri(entryUrl).build()).build();
		}

		Verifier v = new Verifier(token);
		
		OAuthService service2 = null;
		try {
			service2 = new ServiceBuilder()
					.provider(Google2Api.class)
					.apiKey(socialsConfig.getProperty("google.apiKey")) 
					.apiSecret(socialsConfig.getProperty("google.apiSecret"))
					.callback(callbackUrlG)
					.scope(SCOPE)
					.offline(true)
					.build();

		} catch (Exception e) {
			LOG.error(e);
		}

		Token accessToken = new Token("ACCESS_TOKEN", "REFRESH_TOKEN");

		accessToken = service2.getAccessToken(EMPTY_TOKEN, v);

		String refreshGoogleToken = accessToken.getSecret();
		
		// Request protected resource
		OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
		service2.signRequest(accessToken, request);
		org.scribe.model.Response response = request.send();

		// JSON string from Google response
		String json = response.getBody();

		// parse string
		String googleId = null;
		String name = null;
		String link = null;
		String email = null;
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(json);

			name = (String) jsonObject.get("name");
			googleId = (String) jsonObject.get("id");
			link = (String) jsonObject.get("picture");
			email = (String) jsonObject.get("email");			

		} catch (ParseException e) {			
			LOG.error(e);
		} catch (NullPointerException ex) {			
			LOG.error(ex);
		}

		// getting userId from current session
		HttpSession session = req.getSession(true);

		// CASE 1: Editing user profile from MyCabinet. Check if session has parameters
		if (session.getAttribute("userId") != null) {

			// Check if user exist by googleId. If exist - we can't join accounts - will be error.
			// ERROR - when login - two accounts with the same GoogleID
			User existUserWithGoogleId = null;
			try {
				existUserWithGoogleId = userRep.getByGoogleId(googleId);
			} catch (Exception e) {
				LOG.error(e);
				return SERVER_ERROR;
			}

			if (existUserWithGoogleId != null) {
				// add params to redirect URL to inform frontend that account is already in use
				// by another user
				String errorUrl = successURL + "?join=error";
				return Response.temporaryRedirect(UriBuilder.fromUri(errorUrl).build()).build();
			}

			int userId = Integer.parseInt((String) session.getAttribute("userId"));
			
			// insert in User value of googleId and picture by userId
			User user = null;
			try {
				user = userRep.getById(userId);
				user.setGoogleId(googleId);
				user.setSocialPhoto(link);
				user.setEmail(email);
				userRep.update(user);

			} catch (Exception e) {
				LOG.error(e);
				return SERVER_ERROR;
			}

			session.setAttribute("successMesage", "Successful joining Google account");
			session.setAttribute("user", user);
			session.setAttribute("refreshGoogleToken", refreshGoogleToken);

			return Response.temporaryRedirect(UriBuilder.fromUri(successURL).build()).build();

		}

		// CASE 2: Login to site. Session is not set. Find User by googleId
		// CASE 3: Registration. Session is not set. Create User with GoogleId
		
		// Check if user exist by googleId
		User user = null;
		try {
			user = userRep.getByGoogleId(googleId);
		} catch (Exception e) {
			LOG.error(e);
			return SERVER_ERROR;
		}

		if (user != null) {
			// Case 2
			// creating Session for founded user. Setting params			
			setUpSuccessSession(user, session, "success login with GoogleId");
			session.setAttribute("refreshGoogleToken", refreshGoogleToken);

			// Entering to site with Session
			return Response.temporaryRedirect(UriBuilder.fromUri(successURL).build()).build();
		}

		// else CASE 3 - creating User to register
		User userToReg = createEmptyUser(name);

		userToReg.setEmail(email);
		userToReg.setSocialPhoto(link);
		userToReg.setGoogleId(googleId);		

		// inserting user to DB
		try {
			userRep.insert(userToReg);
		} catch (Exception e) {
			LOG.error(e);
			return SERVER_ERROR;
		}

		// creating session
		setUpSuccessSession(userToReg, session, "successful Registration with GoogleId");
		session.setAttribute("refreshGoogleToken", refreshGoogleToken);		

		// Entering to site with Session
		return Response.temporaryRedirect(UriBuilder.fromUri(successURL).build()).build();

	}	


	@GET
	@Path("login/google_login_direct") // http://localhost:8080/webapi/account/login/google_login_direct
	public Response directGoogleLoginWithOldAccessToken(@Context HttpServletRequest req,
													    @QueryParam("code") String refreshGoogleToken) {

		// Define URLs and callback
		String callbackUrlG = defineURL(req, "webapi/account/login/google_login_direct", callbackUrlPathG);
		String successURL = defineSuccessUrl(req, "webapi/account/login/google_login_direct"); 
		

		// Getting new access token with old refreshToken
		OAuthRequest request = new OAuthRequest(Verb.POST, "https://www.googleapis.com/oauth2/v3/token");
		request.addBodyParameter("grant_type", "refresh_token");
		request.addBodyParameter("refresh_token", refreshGoogleToken); 
		request.addBodyParameter("client_id", socialsConfig.getProperty("google.apiKey"));
		request.addBodyParameter("client_secret", socialsConfig.getProperty("google.apiSecret"));

		org.scribe.model.Response response = request.send();
		
		// JSON string from Google response
		String json = response.getBody();

		// parse string
		String new_access_token = null;

		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(json);

			new_access_token = (String) jsonObject.get("access_token");
			System.out.println("new accessToken is: " + new_access_token);

		} catch (ParseException e) {
			LOG.error(e);			
		} catch (NullPointerException ex) {
			LOG.error(ex);
		}

		// New request to protected resource with new accessToken and old refreshToken
		OAuthService service2 = null;
		try {
			service2 = new ServiceBuilder()
					.provider(Google2Api.class)
					.apiKey(socialsConfig.getProperty("google.apiKey")) 
					.apiSecret(socialsConfig.getProperty("google.apiSecret"))
					.callback(callbackUrlG)
					.scope(SCOPE)
					.build();

		} catch (Exception e) {
			LOG.error(e);
		}

		Token accessToken = new Token(new_access_token, refreshGoogleToken);

		// Request protected resource
		OAuthRequest request2 = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
		service2.signRequest(accessToken, request2);
		org.scribe.model.Response response2 = request2.send();

		// JSON string from Google response
		String json2 = response2.getBody();

		// parse string
		String googleId = null;
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(json2);

			googleId = (String) jsonObject.get("id");			

		} catch (ParseException e) {
			LOG.error(e);			
		} catch (NullPointerException ex) {
			LOG.error(ex);
		}

		// Login to site. Session is not set. Find User by googleId
		HttpSession session = req.getSession(true);

		// Check if user exist by googleId
		User user = null;
		try {
			user = userRep.getByGoogleId(googleId);			
		} catch (Exception e) {
			LOG.error(e);
			return SERVER_ERROR;
		}

		if (user == null) {
			return NOT_FOUND;
		}

		// creating Session for founded user. Setting params
		setUpSuccessSession(user, session, "success direct login with GoogleId");
		session.setAttribute("refreshGoogleToken", refreshGoogleToken);

		return Response.status(Response.Status.OK).entity(successURL).build();

	}

	@GET
	@Path("login/facebook") // http:localhost:8080/webapi/account/login/facebook
	public Response facebookLogin(@Context HttpServletRequest req) {

		// Define URLs and callback
		String callbackUrlF = defineURL(req, "webapi/account/login/facebook", callbackUrlGPathFacebook);
		
		OAuthService service = null;
		try {
			service = new ServiceBuilder()
					.provider(FacebookApi.class)
					.apiKey(socialsConfig.getProperty("facebook.apiKey")) 
					.apiSecret(socialsConfig.getProperty("facebook.apiSecret"))
					.callback(callbackUrlF)
					.build();

		} catch (Exception e) {
			LOG.error(e);			
		}

		if (service == null) {
			return Response.status(404).build();
		}

		String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
		
		return Response.status(Response.Status.OK).entity(authorizationUrl).build();

	}

	@GET
	@Path("login/facebook_token") // http://localhost:8080/webapi/account/login/facebook_token
	public Response getFacebookAccessToken(@QueryParam("code") String token,
										   @QueryParam("error") String error,
										   @Context HttpServletRequest req) {

		// Define URLs and callback
		String callbackUrlF = defineURL(req, "webapi/account/login/facebook_token", callbackUrlGPathFacebook);	
		String pathMain = definePathMain(req, "webapi/account/login/facebook_token");
		String successURL = defineSuccessUrl(req, "webapi/account/login/facebook_token"); 
		
		if (error != null) {
			String entryUrl = pathMain + "/#/ua/user/login";
			return Response.temporaryRedirect(UriBuilder.fromUri(entryUrl).build()).build();
		}

		Verifier v = new Verifier(token);

		OAuthService service = null;
		try {
			service = new ServiceBuilder()
					.provider(FacebookApi.class)
					.apiKey(socialsConfig.getProperty("facebook.apiKey")) 
					.apiSecret(socialsConfig.getProperty("facebook.apiSecret"))
					.callback(callbackUrlF).build();

		} catch (Exception e) {
			LOG.error(e);			
		}

		Token accessToken = service.getAccessToken(EMPTY_TOKEN, v);

		OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL_FB);
		OAuthRequest request2 = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL_FB2);

		service.signRequest(accessToken, request);
		service.signRequest(accessToken, request2);

		request.addHeader("GData-Version", "3.0");
		request2.addHeader("GData-Version", "3.0");

		org.scribe.model.Response response = request.send();
		org.scribe.model.Response response2 = request2.send();

		// JSON string from Facebook response
		String json = response.getBody();
		String json2 = response2.getBody();

		// parse string
		String facebookId = null;
		String name = null;
		String link = null;

		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(json);

			name = (String) jsonObject.get("name");
			facebookId = (String) jsonObject.get("id");
			
			
			JSONObject jsonObject2 = (JSONObject) jsonParser.parse(json2);

			JSONObject picture = (JSONObject) jsonObject2.get("picture");
			JSONObject data = (JSONObject) picture.get("data");
			link = (String) data.get("url");
//			System.out.println(link);
//			System.out.println(link.length());

		} catch (ParseException e) {
			LOG.error(e);			
		} catch (NullPointerException ex) {
			LOG.error(ex);
		}

		// getting userId from current session
		HttpSession session = req.getSession(true);

		// CASE 1: Editing user profile from MyCabinet. Check if session has parameters
		if (session.getAttribute("userId") != null) {

			// Check if user exist by googleId. If exist - we can't join accounts - will be error.
			// ERROR - when login - two accounts with the same GoogleID
			User existUserWithFBId = null;
			try {
				existUserWithFBId = userRep.getByFacebookId(facebookId);
			} catch (Exception e) {
				return SERVER_ERROR;
			}

			if (existUserWithFBId != null) {
				// add params to redirect URL to inform frontend that account is already in use by another user
				String errorUrl = successURL + "?join=error";
				return Response.temporaryRedirect(UriBuilder.fromUri(errorUrl).build()).build();
			}

			int userId = Integer.parseInt((String) session.getAttribute("userId"));
			
			// insert in User value of facebookId and picture by userId
			User user = null;
			try {
				user = userRep.getById(userId);
				user.setFacebookId(facebookId);
				user.setSocialPhoto(link);
				userRep.update(user);
				
			} catch (Exception e) {
				return SERVER_ERROR;
			}

			session.setAttribute("successMesage", "Successful joining Facebook account");
			session.setAttribute("user", user);
			session.setAttribute("facebookToken", accessToken.toString());

			return Response.temporaryRedirect(UriBuilder.fromUri(successURL).build()).build();

		}

		// CASE 2: Login to site. Session is not set. Find User by FacebookId
		// CASE 3: Registration. Session is not set. Create User with FacebookId		

		// Check if user exist by facebookId
		User user = null;
		try {
			user = userRep.getByFacebookId(facebookId);
		} catch (Exception e) {
			LOG.error(e);
			return SERVER_ERROR;
		}

		if (user != null) {
			// Case 2
			// creating Session for founded user. Setting params
			setUpSuccessSession(user, session, "success login with FacebookId");
			session.setAttribute("facebookToken", accessToken.toString());

			// Entering to site with Session
			return Response.temporaryRedirect(UriBuilder.fromUri(successURL).build()).build();

		}

		// else CASE 3
		// creating User to register
		User userToReg = createEmptyUser(name);		
			
		userToReg.setFacebookId(facebookId);
		userToReg.setSocialPhoto(link);			

		// inserting user to DB
		try {
			userRep.insert(userToReg);
		} catch (Exception e) {
			LOG.error(e);
			return SERVER_ERROR;
		}

		// creating session
		setUpSuccessSession(userToReg, session, "successful Registration with GoogleId");
		session.setAttribute("user", userToReg);
		session.setAttribute("facebookToken", accessToken.toString());

		// Entering to site with Session
		return Response.temporaryRedirect(UriBuilder.fromUri(successURL).build()).build();

	}

	@GET
	@Path("login/twitter") // http:localhost:8080/webapi/account/login/twitter
	public Response twitterLogin(@Context HttpServletRequest req) {
		
		// Define URLs and callback
		String callbackUrlTW = defineURL(req, "webapi/account/login/twitter", callbackUrlGPathTW);	
		
		OAuthService service = null;

		try {
			service = new ServiceBuilder().provider(TwitterApi.class)
					.apiKey(socialsConfig.getProperty("twitter.consumerKey")) 
					.apiSecret(socialsConfig.getProperty("twitter.consumerSecret"))
					.callback(callbackUrlTW)
					.build();

		} catch (Exception e) {
			LOG.error(e);
			e.printStackTrace();
		}

		if (service == null) {
			return Response.status(404).build();
		}

		Token requestToken = service.getRequestToken();
		String authorizationUrl = service.getAuthorizationUrl(requestToken);

		return Response.status(Response.Status.OK).entity(authorizationUrl).build();
		
	}

	@GET
	@Path("login/twitter_token") // http://localhost:8080/webapi/account/login/twitter_token
	public Response getTwitterAccessToken(@QueryParam("oauth_token") String oauth_token,
										  @QueryParam("oauth_verifier") String oauth_verifier, 
										  @QueryParam("denied") String error,
										  @Context HttpServletRequest req) {
		
		// Define URLs and callback
		String callbackUrlTW = defineURL(req, "webapi/account/login/twitter_token", callbackUrlGPathTW);	
		String pathMain = definePathMain(req, "webapi/account/login/twitter_token");
		String successURL = defineSuccessUrl(req, "webapi/account/login/twitter_token"); 		

		if (error != null) {
			String entryUrl = pathMain + "/#/ua/user/login";
			return Response.temporaryRedirect(UriBuilder.fromUri(entryUrl).build()).build();
		}

		Verifier v = new Verifier(oauth_verifier);
		Token requestToken = new Token(oauth_token, oauth_verifier);

		OAuthService service = null;
		try {
			service = new ServiceBuilder()
					.provider(TwitterApi.class)
					.apiKey(socialsConfig.getProperty("twitter.consumerKey")) 
					.apiSecret(socialsConfig.getProperty("twitter.consumerSecret"))
					.callback(callbackUrlTW)
					.build();

		} catch (Exception e) {
			LOG.error(e);			
		}

		Token accessToken = service.getAccessToken(requestToken, v);
		
		String tokenTw = accessToken.getToken();
		String secretTw = accessToken.getSecret();
	
		OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL_TW);
		service.signRequest(accessToken, request);
		org.scribe.model.Response response = request.send();
		
		// JSON string from Google response
		String json = response.getBody();

		// parse string
		String twitterId = null;
		String name = null;
		String link = null;
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(json);

			name = (String) jsonObject.get("name");
			twitterId = (String) jsonObject.get("id_str");	
			String link2 = (String) jsonObject.get("profile_image_url");
			link = link2.replace("_normal", "");			

		} catch (ParseException e) {
			LOG.error(e);			
		} catch (NullPointerException ex) {
			LOG.error(ex);
		}

		// getting userId from current session
		HttpSession session = req.getSession(true);

		// CASE 1: Editing user profile from MyCabinet. Check if session has parameters
		if (session.getAttribute("userId") != null) {

			// Check if user exist by googleId. If exist - we can't join accounts - will be error.
			// ERROR - when login - two accounts with the same GoogleID
			User existUserWithTWId = null;
			try {
				existUserWithTWId = userRep.getByTwitterId(twitterId);
			} catch (Exception e) {
				LOG.error(e);
				return SERVER_ERROR;
			}

			if (existUserWithTWId != null) {
				// add params to redirect URL to inform frontend that account is already in use by another user
				String errorUrl = successURL + "?join=error";
				return Response.temporaryRedirect(UriBuilder.fromUri(errorUrl).build()).build();
			}

			int userId = Integer.parseInt((String) session.getAttribute("userId"));
			
			// insert in User value of twitterId and picture by userId
			User user = null;
			try {
				user = userRep.getById(userId);
				user.setTwitterId(twitterId);
				user.setSocialPhoto(link);	
				userRep.update(user);

			} catch (Exception e) {
				LOG.error(e);
				return SERVER_ERROR;
			}

			session.setAttribute("successMesage", "Successful joining Twitter account");
			session.setAttribute("user", user);
			session.setAttribute("twitterToken", tokenTw);
			session.setAttribute("twitterSecret", secretTw);

			return Response.temporaryRedirect(UriBuilder.fromUri(successURL).build()).build();

		}

		// CASE 2: Login to site. Session is not set. Find User by FacebookId
		// CASE 3: Registration. Session is not set. Create User with FacebookId
		
		// Check if user exist by googleId
		User user = null;
		try {
			user = userRep.getByTwitterId(twitterId);
		} catch (Exception e) {
			LOG.error(e);
			return SERVER_ERROR;
		}

		if (user != null) {
			// Case 2
			// creating Session for founded user. Setting params
			setUpSuccessSession(user, session, "success login with Twitter");
			session.setAttribute("twitterToken", tokenTw);
			session.setAttribute("twitterSecret", secretTw);			

			// Entering to site with Session
			return Response.temporaryRedirect(UriBuilder.fromUri(successURL).build()).build();

		}

		// else CASE 3
		// creating User to register
		User userToReg = createEmptyUser(name);		
			
		userToReg.setTwitterId(twitterId);
		userToReg.setSocialPhoto(link);	

		// inserting user to DB
		try {
			userRep.insert(userToReg);
		} catch (Exception e) {
			LOG.error(e);
			return SERVER_ERROR;
		}

		// creating session
		setUpSuccessSession(userToReg, session, "successful Registration with Twitter");
		session.setAttribute("user", userToReg);
		session.setAttribute("twitterToken", tokenTw);
		session.setAttribute("twitterSecret", secretTw);

		// Entering to site with Session
		return Response.temporaryRedirect(UriBuilder.fromUri(successURL).build()).build();

	}
	
	@GET
	@Path("login/twitter_login_direct") // http://localhost:8080/webapi/account/login/twitter_login_direct
	public Response directTwitterLoginWithOldAccessToken(@Context HttpServletRequest req,
													    @QueryParam("token") String twitterToken,
														@QueryParam("secret") String twitterSecret) {
		
		// Define URLs and callback
		String callbackUrlTW = defineURL(req, "webapi/account/login/twitter_login_direct", callbackUrlGPathTW);	
		String pathMain = definePathMain(req, "webapi/account/login/twitter_login_direct");
		String successURL = defineSuccessUrl(req, "webapi/account/login/twitter_login_direct"); 		
		
		Token twitterAccessToken = new Token(twitterToken, twitterSecret);
		
		OAuthService service = null;
		try {
			service = new ServiceBuilder()
					.provider(TwitterApi.class)
					.apiKey(socialsConfig.getProperty("twitter.consumerKey")) 
					.apiSecret(socialsConfig.getProperty("twitter.consumerSecret"))
					.callback(callbackUrlTW)
					.build();

		} catch (Exception e) {
			LOG.error(e);			
		}
		
		// Request protected resource
		OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL_TW);
		service.signRequest(twitterAccessToken, request);
		org.scribe.model.Response response = request.send();		

		// JSON string from Twitter response
		String json = response.getBody();

		// parse string
		String twitterId = null;		

		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
		
			twitterId = (String) jsonObject.get("id_str");
		} catch (ParseException e) {			
			LOG.error(e);
		} catch (NullPointerException ex) {
			LOG.error(ex);
		}		

		// Login to site. Session is not set. Find User by googleId
		HttpSession session = req.getSession(true);

		// Check if user exist by twitterId
		User user = null;
		try {
			user = userRep.getByTwitterId(twitterId);			
		} catch (Exception e) {
			LOG.error(e);
			return SERVER_ERROR;
		}

		if (user == null) {
			return NOT_FOUND;
		}

		// creating Session for founded user. Setting params
		setUpSuccessSession(user, session, "success direct login with Twitter");
		session.setAttribute("twitterToken", twitterToken);
		session.setAttribute("twitterSecret", twitterSecret);

		return Response.status(Response.Status.OK).entity(successURL).build();		
		
	}
	

	private static void setUpSuccessSession(User user, HttpSession session, String success) {

		session.setAttribute("userName", user.getName());
		session.setAttribute("userId", user.getId().toString());
		session.setAttribute("userSurname", user.getSurname());
		session.setAttribute("socialLogin", user.getSocialLogin());
		session.setAttribute("userRoleId", user.getUserRole().get(0).getId().toString());
		session.setAttribute("userRole", user.getUserRole().get(0).getRole());
		session.setAttribute("successMesage", success);
		session.setAttribute("user", user);

		// creating string for accessToken
		String accessToken = (String) session.getId() + ":" + (String) session.getAttribute("userId");

		System.out.println("decoded accesToken - " + accessToken);

		String accessTokenEncoded = null;

		try {
			byte[] encoded = Base64.encodeBase64(accessToken.getBytes());
			accessTokenEncoded = new String(encoded, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("encoded accessToken -" + accessTokenEncoded);
		session.setAttribute("accessToken", accessTokenEncoded);

	}
	
	private String defineURL(HttpServletRequest req, String reqPath, String callback) {
		
		String pathAll = req.getRequestURL().toString();
		String pathMain = pathAll.replace(reqPath, "");
		String callbackUrl = pathMain + callback;
		return callbackUrl;		
	}
	
	private String definePathMain(HttpServletRequest req, String string) {
		String pathAll = req.getRequestURL().toString();
		String pathMain = pathAll.replace(string, "");
		return pathMain;
	}
	
	private String defineSuccessUrl(HttpServletRequest req, String string) {
		String pathAll = req.getRequestURL().toString();
		String pathMain = pathAll.replace(string, "");
		String SuccessUrl = pathMain + "#/ua/user/profile";
		return SuccessUrl;
	}
	
	private User createEmptyUser(String userName) {
		
		User userToReg = new User();
		
		String userLogin;
		if (userName != null && !userName.isEmpty()) {
			userLogin = userName;
		} else {
			userLogin = "unknown";
		}
		
		userToReg.setEmail("N/A");
		userToReg.setName(userLogin);
		userToReg.setSocialLogin(userLogin);
		userToReg.setSurname("N/A");
		userToReg.setActive(true);
		userToReg.setAddress("N/A");
		userToReg.setPhone("N/A");
		userToReg.setOrganizationInfo("N/A");
		userToReg.setOrganizationName("N/A");
		userToReg.setPassword(userLogin);

		UserRole userRole = new UserRole();
		userRole.setRole("гість");
		userRole.setId(3);
		List<UserRole> list = new ArrayList<UserRole>();
		list.add(userRole);
		userToReg.setUserRole(list);

		UserType userType = new UserType();
		userType.setId(1);
		userToReg.setUserType(userType);

		Date currentDate = new Date(new java.util.Date().getTime());		
		userToReg.setRegistrationDate(currentDate);
		
		return userToReg;
		
	}
	
	private static Properties socialsConfig = new Properties();
    {
        fetchConfig();
    }

    /**
     * Open a specific text file containing reCAPTCHA secret key
     * and verification URL.
     */
    private void fetchConfig() {
        
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("project.properties").getFile());

        try (InputStream input = new FileInputStream(file)) {
            socialsConfig.load(input);
        }
        catch (IOException ex){
            System.err.println("Cannot open and load mail server properties file. Put it on...");
        }
    }

}