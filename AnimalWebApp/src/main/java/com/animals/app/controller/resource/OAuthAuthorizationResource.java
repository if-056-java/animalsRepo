package com.animals.app.controller.resource;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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

	private final Response BAD_REQUEST = Response.status(Response.Status.BAD_REQUEST).build();
	private final Response NOT_FOUND = Response.status(Response.Status.NOT_FOUND).build();
	private final Response SERVER_ERROR = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

	private UserRepository userRep = new UserRepositoryImpl();

	// Google OAuth preferences
	private static final String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json";
	private static final String SCOPE = "https://mail.google.com/ https://www.googleapis.com/auth/userinfo.email";
	private static final Token EMPTY_TOKEN = null;
	private static final String apiKeyG = "1061082540782-02vuauouhb8v5caiavepvgkuuiv4t178.apps.googleusercontent.com";
	private static final String apiSecretG = "rYsnWUSHf4S2z-LHM1oMocJT";
	private static final String callbackUrlPathG = "webapi/account/login/google_token";

	// Facebook OAuth preferences
	private static final String PROTECTED_RESOURCE_URL_FB = "https://graph.facebook.com/me";
	private static final String PROTECTED_RESOURCE_URL_FB2 = "https://graph.facebook.com/me?fields=picture.type(large)";
	private static final String apiKeyF = "926304537416382";
	private static final String apiSecretF = "d4a862fa422e06f2e06614628a619683";
	private static final String callbackUrlGPathFacebook = "webapi/account/login/facebook_token";

	// Twitter OAuth preferences
	private static final String PROTECTED_RESOURCE_URL_TW = "https://api.twitter.com/1.1/account/verify_credentials.json";
	private static final String apiKeyTW = "C9Z3AJ4GVBYxa7exesFpZBZNn";
	private static final String apiSecretTW = "bYQldGWdEhWGVmnGlMnFQ7hYMFSHHGeWq3ANZyexgFZZ2JYPL6";
	private static final String callbackUrlGPathTW = "webapi/account/login/twitter_token";

	@GET
	@Path("login/google") // http:localhost:8080/webapi/account/login/google
	public Response googleLogin(@Context HttpServletRequest req) {

		// Define URLs and callback
		String pathAll = req.getRequestURL().toString();
		String pathMain = pathAll.replace("webapi/account/login/google", "");
		String callbackUrlG = pathMain + callbackUrlPathG;

		OAuthService service = null;

		try {
			service = new ServiceBuilder()
					.provider(Google2Api.class)
					.apiKey(apiKeyG)
					.apiSecret(apiSecretG)
					.callback(callbackUrlG)
					.scope(SCOPE)
					.offline(true)
					.build();

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (service == null) {
			return Response.status(404).build();
		}

		String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);

		System.out.println("url - " + authorizationUrl);

		return Response.status(Response.Status.OK).entity(authorizationUrl).build();
	}

	@GET
	@Path("login/google_token") // http://localhost:8080/webapi/account/login/google_token
	public Response getGoogleAccessToken(@QueryParam("code") String token,
										 @QueryParam("error") String error,
										 @Context HttpServletRequest req) {

		// Define URLs and callback
		String pathAll = req.getRequestURL().toString();
		String pathMain = pathAll.replace("webapi/account/login/google_token", "");
		String successURL = pathMain + "#/ua/user/profile";
		String callbackUrlG = pathMain + callbackUrlPathG;

		if (error != null) {
			String entryUrl = pathMain + "/#/ua/user/login";
			return Response.temporaryRedirect(UriBuilder.fromUri(entryUrl).build()).build();
		}

		Verifier v = new Verifier(token);

		System.out.println("token - " + token);

		OAuthService service2 = null;

		try {
			service2 = new ServiceBuilder()
					.provider(Google2Api.class)
					.apiKey(apiKeyG)
					.apiSecret(apiSecretG)
					.callback(callbackUrlG)
					.scope(SCOPE)
					.offline(true)
					.build();

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		Token accessToken = new Token("ACCESS_TOKEN", "REFRESH_TOKEN");

		accessToken = service2.getAccessToken(EMPTY_TOKEN, v);

		System.out.println(accessToken);

		String refreshGoogleToken = accessToken.getSecret();
		String accessGoogleToken = accessToken.getToken();

		// Request protected resource
		OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
		service2.signRequest(accessToken, request);
		org.scribe.model.Response response = request.send();

		System.out.println(response.getCode());
		System.out.println(response.getBody());

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
			System.out.println("The first name is: " + name);

			googleId = (String) jsonObject.get("id");
			System.out.println("id is: " + googleId);

			link = (String) jsonObject.get("picture");
			System.out.println("Link to photo: " + link);

			email = (String) jsonObject.get("email");
			System.out.println("Email: " + email);

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		// getting userId from current session
		HttpSession session = req.getSession(true);

		// CASE 1: Editing user profile from MyCabinet. Check if session has
		// parameters
		if (session.getAttribute("userId") != null) {

			// Check if user exist by googleId. If exist - we can't join
			// accounts - will be error.
			// ERROR - when login - two accounts with the same GoogleID
			User existUserWithGoogleId = null;
			try {

				existUserWithGoogleId = userRep.getByGoogleId(googleId);

			} catch (Exception e) {
				return SERVER_ERROR;
			}

			if (existUserWithGoogleId != null) {
				// add params to redirect URL to inform frontend that account is
				// already in use
				// by another user
				String errorUrl = successURL + "?join=error";
				return Response.temporaryRedirect(UriBuilder.fromUri(errorUrl).build()).build();
			}

			int userId = Integer.parseInt((String) session.getAttribute("userId"));
			System.out.println(userId);

			// insert in User value of googleId and picture by userId
			User user = null;
			try {

				user = userRep.getById(userId);
				user.setGoogleId(googleId);
				user.setSocialPhoto(link);

				userRep.update(user);

			} catch (Exception e) {
				return SERVER_ERROR;
			}

			session.setAttribute("successMesage", "Successful joining Google account");
			session.setAttribute("user", user);
			session.setAttribute("refreshGoogleToken", refreshGoogleToken);

			return Response.temporaryRedirect(UriBuilder.fromUri(successURL).build()).build();

		}

		// CASE 2: Login to site. Session is not set. Find User by googleId
		// CASE 3: Registration. Session is not set. Create User with GoogleId
		// and SocialPhoto

		// Check if user exist by googleId
		User user = null;

		try {

			user = userRep.getByGoogleId(googleId);

		} catch (Exception e) {
			return SERVER_ERROR;
		}

		if (user != null) {
			// Case 2

			// creating Session for founded user. Setting params
			System.out.println("creating session");

			setUpSuccessSession(user, session, "success login with GoogleId");
			session.setAttribute("refreshGoogleToken", refreshGoogleToken);

			// Entering to site with Session
			return Response.temporaryRedirect(UriBuilder.fromUri(successURL).build()).build();

		}

		// else CASE 3

		// creating User to register
		User userToReg = new User();

		String userLogin;
		if (name != null && !name.isEmpty()) {
			userLogin = name;
		} else {
			userLogin = "unknown";
		}

		userToReg.setName(userLogin);
		userToReg.setSocialLogin(userLogin);

		userToReg.setSurname("N/A");
		userToReg.setEmail(email);
		userToReg.setActive(true);
		userToReg.setAddress("N/A");
		userToReg.setPhone("N/A");
		userToReg.setOrganizationInfo("N/A");
		userToReg.setOrganizationName("N/A");
		userToReg.setPassword(googleId);
		userToReg.setSocialPhoto(link);
		userToReg.setGoogleId(googleId);

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
		System.out.println(currentDate);
		userToReg.setRegistrationDate(currentDate);

		// inserting user to DB
		try {

			userRep.insert(userToReg);

		} catch (Exception e) {
			return SERVER_ERROR;
		}

		// creating session
		setUpSuccessSession(userToReg, session, "successful Registration with GoogleId");
		session.setAttribute("refreshGoogleToken", refreshGoogleToken);
		// session.setAttribute("user", userToReg);

		// Entering to site with Session
		return Response.temporaryRedirect(UriBuilder.fromUri(successURL).build()).build();

	}

	@GET
	@Path("login/google_login_direct") // http://localhost:8080/webapi/account/login/google_login_direct
	public Response directGoogleLoginWithOldAccessToken(@Context HttpServletRequest req,
													    @QueryParam("code") String refreshGoogleToken) {

		// Define URLs and callback
		String pathAll = req.getRequestURL().toString();
		String pathMain = pathAll.replace("webapi/account/login/google_login_direct", "");
		String successURL = pathMain + "#/ua/user/profile";
		String callbackUrlG = pathMain + callbackUrlPathG;

		System.out.println("Google refresh token - " + refreshGoogleToken);

		// getting new access token with old refreshToken
		OAuthRequest request = new OAuthRequest(Verb.POST, "https://www.googleapis.com/oauth2/v3/token");
		request.addBodyParameter("grant_type", "refresh_token");
		request.addBodyParameter("refresh_token", refreshGoogleToken); 
		request.addBodyParameter("client_id", apiKeyG);
		request.addBodyParameter("client_secret", apiSecretG);

		org.scribe.model.Response response = request.send();

		System.out.println(response.getCode()); // 200 - success
		System.out.println(response.getBody()); // JSON response

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
			e.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		// New request to protected resource with new accessToken and old
		// refreshToken

		OAuthService service2 = null;

		try {
			service2 = new ServiceBuilder()
					.provider(Google2Api.class)
					.apiKey(apiKeyG)
					.apiSecret(apiSecretG)
					.callback(callbackUrlG)
					.scope(SCOPE)
					.build();

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		Token accessToken = new Token(new_access_token, refreshGoogleToken);

		// Request protected resource
		OAuthRequest request2 = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
		service2.signRequest(accessToken, request2);
		org.scribe.model.Response response2 = request2.send();

		System.out.println(response2.getCode()); // 200 - success
		System.out.println(response2.getBody()); // JSON response

		// JSON string from Google response
		String json2 = response2.getBody();

		// parse string
		String googleId = null;

		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(json2);

			googleId = (String) jsonObject.get("id");
			System.out.println("id is: " + googleId);

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		// Login to site. Session is not set. Find User by googleId
		HttpSession sessionNew = req.getSession(true);

		// Check if user exist by googleId
		User user = null;

		try {

			user = userRep.getByGoogleId(googleId);
			System.out.println(user);

		} catch (Exception e) {
			return SERVER_ERROR;
		}

		if (user == null) {
			return NOT_FOUND;
		}

		// creating Session for founded user. Setting params
		setUpSuccessSession(user, sessionNew, "success direct login with GoogleId");
		sessionNew.setAttribute("refreshGoogleToken", refreshGoogleToken);

		return Response.status(Response.Status.OK).entity(successURL).build();

	}

	@GET
	@Path("login/facebook") // http:localhost:8080/webapi/account/login/facebook
	public Response facebookLogin(@Context HttpServletRequest req) {

		// Define URLs and callback
		String pathAll = req.getRequestURL().toString();
		String pathMain = pathAll.replace("webapi/account/login/facebook", "");
		System.out.println(pathMain);
		String callbackUrlF = pathMain + callbackUrlGPathFacebook;
		System.out.println(callbackUrlF);

		OAuthService service = null;

		try {
			service = new ServiceBuilder()
					.provider(FacebookApi.class)
					.apiKey(apiKeyF)
					.apiSecret(apiSecretF)
					.callback(callbackUrlF)
					.build();

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (service == null) {
			return Response.status(404).build();
		}

		String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);

		System.out.println("url - " + authorizationUrl);

		return Response.status(Response.Status.OK).entity(authorizationUrl).build();

	}

	@GET
	@Path("login/facebook_token") // http://localhost:8080/webapi/account/login/facebook_token
	public Response getFacebookAccessToken(@QueryParam("code") String token, @QueryParam("error") String error,
			@Context HttpServletRequest req) {

		// Define URLs and callback
		String pathAll = req.getRequestURL().toString();
		String pathMain = pathAll.replace("webapi/account/login/facebook_token", "");
		String successURL = pathMain + "#/ua/user/profile";
		String callbackUrlF = pathMain + callbackUrlGPathFacebook;

		if (error != null) {
			String entryUrl = pathMain + "/#/ua/user/login";
			return Response.temporaryRedirect(UriBuilder.fromUri(entryUrl).build()).build();
		}

		Verifier v = new Verifier(token);

		System.out.println("token - " + token);

		OAuthService service = null;

		try {
			service = new ServiceBuilder()
					.provider(FacebookApi.class)
					.apiKey(apiKeyF)
					.apiSecret(apiSecretF)
					.callback(callbackUrlF).build();

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		Token accessToken = service.getAccessToken(EMPTY_TOKEN, v);

		System.out.println("accesstoken - " + accessToken);

		OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL_FB);
		OAuthRequest request2 = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL_FB2);

		service.signRequest(accessToken, request);
		service.signRequest(accessToken, request2);

		request.addHeader("GData-Version", "3.0");
		request2.addHeader("GData-Version", "3.0");

		org.scribe.model.Response response = request.send();
		org.scribe.model.Response response2 = request2.send();

		System.out.println(response.getCode());
		System.out.println(response.getBody());
		System.out.println(response2.getBody());

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
			System.out.println("The first name is: " + name);

			facebookId = (String) jsonObject.get("id");
			System.out.println("id is: " + facebookId);
			
			JSONObject jsonObject2 = (JSONObject) jsonParser.parse(json2);

			JSONObject picture = (JSONObject) jsonObject2.get("picture");
			JSONObject data = (JSONObject) picture.get("data");

			link = (String) data.get("url");
			System.out.println("link to FB photo - " + link);
			System.out.println(link.length());

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		// getting userId from current session
		HttpSession session = req.getSession(true);

		// CASE 1: Editing user profile from MyCabinet. Check if session has
		// parameters
		if (session.getAttribute("userId") != null) {

			// Check if user exist by googleId. If exist - we can't join
			// accounts - will be error.
			// ERROR - when login - two accounts with the same GoogleID
			User existUserWithFBId = null;
			try {

				existUserWithFBId = userRep.getByFacebookId(facebookId);

			} catch (Exception e) {
				return SERVER_ERROR;
			}

			if (existUserWithFBId != null) {
				// add params to redirect URL to inform frontend that account is
				// already in use
				// by another user
				String errorUrl = successURL + "?join=error";
				return Response.temporaryRedirect(UriBuilder.fromUri(errorUrl).build()).build();
			}

			int userId = Integer.parseInt((String) session.getAttribute("userId"));
			System.out.println(userId);

			// insert in User value of googleId and picture by userId
			User user = null;
			try {

				user = userRep.getById(userId);
				user.setFacebookId(facebookId);
				// user.setSocialPhoto(link);
				user.setSocialPhoto("link");

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
		// and SocialPhoto

		// Check if user exist by googleId
		User user = null;

		try {

			user = userRep.getByFacebookId(facebookId);

		} catch (Exception e) {
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
		User userToReg = new User();

		String userLogin;
		if (name != null && !name.isEmpty()) {
			userLogin = name;
		} else {
			userLogin = "unknown";
		}

		userToReg.setName(userLogin);
		userToReg.setSocialLogin(userLogin);

		userToReg.setSurname("N/A");
		userToReg.setEmail("N/A");
		userToReg.setActive(true);
		userToReg.setAddress("N/A");
		userToReg.setPhone("N/A");
		userToReg.setOrganizationInfo("N/A");
		userToReg.setOrganizationName("N/A");
		userToReg.setPassword(facebookId);
		// userToReg.setSocialPhoto(link);
		userToReg.setSocialPhoto("link");
		userToReg.setFacebookId(facebookId);

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
		System.out.println(currentDate);
		userToReg.setRegistrationDate(currentDate);

		// inserting user to DB
		try {

			userRep.insert(userToReg);

		} catch (Exception e) {
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
		String pathAll = req.getRequestURL().toString();
		String pathMain = pathAll.replace("webapi/account/login/twitter", "");
		String callbackUrlTW = pathMain + callbackUrlGPathTW;

		OAuthService service = null;

		try {
			service = new ServiceBuilder().provider(TwitterApi.class)
					.apiKey(apiKeyTW)
					.apiSecret(apiSecretTW)
					.callback(callbackUrlTW)
					.build();

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (service == null) {
			return Response.status(404).build();
		}

		Token requestToken = service.getRequestToken();
		System.out.println("request Token - " + requestToken);

		String authorizationUrl = service.getAuthorizationUrl(requestToken);

		System.out.println("url - " + authorizationUrl);

		return Response.status(Response.Status.OK).entity(authorizationUrl).build();
		
	}

	@GET
	@Path("login/twitter_token") // http://localhost:8080/webapi/account/login/twitter_token
	public Response getTwitterAccessToken(@QueryParam("oauth_token") String oauth_token,
										  @QueryParam("oauth_verifier") String oauth_verifier, 
										  @QueryParam("denied") String error,
										  @Context HttpServletRequest req) {

		// Define URLs and callback
		String pathAll = req.getRequestURL().toString();
		String pathMain = pathAll.replace("webapi/account/login/twitter_token", "");
		String successURL = pathMain + "#/ua/user/profile";
		String callbackUrlTW = pathMain + callbackUrlGPathTW;

		if (error != null) {
			String entryUrl = pathMain + "/#/ua/user/login";
			return Response.temporaryRedirect(UriBuilder.fromUri(entryUrl).build()).build();
		}

		String token = oauth_token;
		String verifier = oauth_verifier;

		System.out.println(token);
		System.out.println(verifier);

		Verifier v = new Verifier(verifier);

		Token requestToken = new Token(token, verifier);

		OAuthService service = null;

		try {
			service = new ServiceBuilder()
					.provider(TwitterApi.class)
					.apiKey(apiKeyTW)
					.apiSecret(apiSecretTW)
					.callback(callbackUrlTW)
					.build();

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		Token accessToken = service.getAccessToken(requestToken, v);
		System.out.println("Twitter access Token - " + accessToken);
		String tokenTw = accessToken.getToken();
		String secretTw = accessToken.getSecret();
		System.out.println(tokenTw + " "+secretTw);

		OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL_TW);

		service.signRequest(accessToken, request);

		org.scribe.model.Response response = request.send();

		System.out.println(response.getCode());
		System.out.println(response.getBody());

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
			System.out.println("The first name is: " + name);

			twitterId = (String) jsonObject.get("id_str");
			System.out.println("id is: " + twitterId);

			String link2 = (String) jsonObject.get("profile_image_url");
			link = link2.replace("_normal", "");
			System.out.println("Link to photo: " + link);

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		// getting userId from current session
		HttpSession session = req.getSession(true);

		// CASE 1: Editing user profile from MyCabinet. Check if session has
		// parameters
		if (session.getAttribute("userId") != null) {

			// Check if user exist by googleId. If exist - we can't join
			// accounts - will be error.
			// ERROR - when login - two accounts with the same GoogleID
			User existUserWithTWId = null;
			try {

				existUserWithTWId = userRep.getByTwitterId(twitterId);

			} catch (Exception e) {
				return SERVER_ERROR;
			}

			if (existUserWithTWId != null) {
				// add params to redirect URL to inform frontend that account is
				// already in use by another user
				String errorUrl = successURL + "?join=error";
				return Response.temporaryRedirect(UriBuilder.fromUri(errorUrl).build()).build();
			}

			int userId = Integer.parseInt((String) session.getAttribute("userId"));
			System.out.println(userId);

			// insert in User value of googleId and picture by userId
			User user = null;
			try {

				user = userRep.getById(userId);
				user.setTwitterId(twitterId);
				user.setSocialPhoto(link);				

				userRep.update(user);

			} catch (Exception e) {
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
		// and SocialPhoto

		// Check if user exist by googleId
		User user = null;

		try {

			user = userRep.getByTwitterId(twitterId);

		} catch (Exception e) {
			return SERVER_ERROR;
		}

		if (user != null) {
			// Case 2

			// creating Session for founded user. Setting params
			System.out.println("creating session");

			setUpSuccessSession(user, session, "success login with Twitter");
			session.setAttribute("twitterToken", tokenTw);
			session.setAttribute("twitterSecret", secretTw);			

			// Entering to site with Session
			return Response.temporaryRedirect(UriBuilder.fromUri(successURL).build()).build();

		}

		// else CASE 3

		// creating User to register
		User userToReg = new User();

		String userLogin;
		if (name != null && !name.isEmpty()) {
			userLogin = name;
		} else {
			userLogin = "unknown";
		}

		userToReg.setName(userLogin);
		userToReg.setSocialLogin(userLogin);

		userToReg.setSurname("N/A");
		userToReg.setEmail("N/A");
		userToReg.setActive(true);
		userToReg.setAddress("N/A");
		userToReg.setPhone("N/A");
		userToReg.setOrganizationInfo("N/A");
		userToReg.setOrganizationName("N/A");
		userToReg.setPassword(twitterId);
		userToReg.setSocialPhoto(link);		
		userToReg.setTwitterId(twitterId);

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
		System.out.println(currentDate);
		userToReg.setRegistrationDate(currentDate);

		// inserting user to DB
		try {

			userRep.insert(userToReg);

		} catch (Exception e) {
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
		String pathAll = req.getRequestURL().toString();
		String pathMain = pathAll.replace("webapi/account/login/twitter_login_direct", "");
		String successURL = pathMain + "#/ua/user/profile";
		String callbackUrlTW = pathMain + callbackUrlGPathTW;

		System.out.println("Twitter token - " + twitterToken);
		System.out.println("Twitter secret - " + twitterSecret);
		
		Token twitterAccessToken = new Token(twitterToken,twitterSecret);
		
		OAuthService service = null;

		try {
			service = new ServiceBuilder()
					.provider(TwitterApi.class)
					.apiKey(apiKeyTW)
					.apiSecret(apiSecretTW)
					.callback(callbackUrlTW)
					.build();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		// Request protected resource
		OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL_TW);

		service.signRequest(twitterAccessToken, request);

		org.scribe.model.Response response = request.send();

		System.out.println(response.getCode());
		System.out.println(response.getBody());

		// JSON string from Google response
		String json = response.getBody();

		// parse string
		String twitterId = null;
		

		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
		
			twitterId = (String) jsonObject.get("id_str");
			System.out.println("id is: " + twitterId);			

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}		

		// Login to site. Session is not set. Find User by googleId
		HttpSession sessionNew = req.getSession(true);

		// Check if user exist by googleId
		User user = null;

		try {

			user = userRep.getByTwitterId(twitterId);
			System.out.println(user);

		} catch (Exception e) {
			return SERVER_ERROR;
		}

		if (user == null) {
			return NOT_FOUND;
		}

		// creating Session for founded user. Setting params
		setUpSuccessSession(user, sessionNew, "success direct login with Twitter");
		sessionNew.setAttribute("twitterToken", twitterToken);
		sessionNew.setAttribute("twitterSecret", twitterSecret);

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

}