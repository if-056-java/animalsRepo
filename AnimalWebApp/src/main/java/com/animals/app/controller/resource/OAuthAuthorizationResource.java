package com.animals.app.controller.resource;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Google2Api;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import com.animals.app.domain.User;
import com.animals.app.domain.UserRole;
import com.animals.app.domain.UserType;
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
	
	private UserRepositoryImpl userRep = new UserRepositoryImpl();
	
	// Google OAuth preferences
	private static final String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json";
	private static final String SCOPE = "https://mail.google.com/ https://www.googleapis.com/auth/userinfo.email";
	private static final Token EMPTY_TOKEN = null;
	private static final String apiKeyG = "1061082540782-02vuauouhb8v5caiavepvgkuuiv4t178.apps.googleusercontent.com";
	private static final String apiSecretG = "rYsnWUSHf4S2z-LHM1oMocJT";	
	private static final String callbackUrlGPath = "webapi/account/login/google_token";	
	
	
	@GET
	@Path("login/google")		//http:localhost:8080/webapi/account/login/google
	public Response googleLogin(@Context HttpServletRequest req) {
		
		String pathAll = req.getRequestURL().toString(); 
		String pathMain =pathAll.replace("webapi/account/login/google", "");
		System.out.println("pathMain - " + pathMain);
		String callbackUrlG = pathMain + callbackUrlGPath;
		System.out.println(callbackUrlG);
		

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
	@Path("login/google_token")			//http://localhost:8080/webapi/account/login/google_token
	public Response getGoogleAccessToken(@QueryParam("code") String token,
										@QueryParam("error") String error,			
										@Context HttpServletRequest req) {
		
		
		String pathAll = req.getRequestURL().toString(); 
		String pathMain =pathAll.replace("webapi/account/login/google_token", "");
		System.out.println("pathMain - " + pathMain);
		String successURL = pathMain + "#/ua/user/profile";
		System.out.println(successURL);
		String callbackUrlG = pathMain + callbackUrlGPath;
		System.out.println(callbackUrlG);
		
		if(error!=null){
			String entryUrl= pathMain + "/#/ua/user/login";				
			return Response.temporaryRedirect(UriBuilder.fromUri(entryUrl).build()).build();
		}

		Verifier v = new Verifier(token);

		System.out.println("token - " + token);
		
		OAuthService service2 =null;

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
		
		String refreshGoogleToken = accessToken.getSecret();
		String accessGoogleToken = accessToken.getToken();

		//Request protected resource
		OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
		service2.signRequest(accessToken, request);
		org.scribe.model.Response response = request.send();
		
		System.out.println(response.getCode());		
		System.out.println(response.getBody());		
		
		//JSON string from Google response
		String json = response.getBody();
		
		//parse string 
		String googleId=null;
		String name=null;
		String link=null;
		String email=null;
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
		
		//getting userId from current session		
		HttpSession session = req.getSession(true);
		
		
		//CASE 1: Editing user profile from MyCabinet. Check if session has parameters
		if(session.getAttribute("userId") != null){			
						
			//Check if user exist by googleId. If exist - we can't join accounts - will be error.
			//ERROR - when login - two accounts with the same GoogleID
			User existUserWithGoogleId=null;
			try {				
				
				existUserWithGoogleId = userRep.getByGoogleId(googleId);	
				
			} catch (Exception e) {				
				return SERVER_ERROR;
			}
			
			
			if (existUserWithGoogleId != null) {
				//add params to redirect URL to inform frontend that account is already in use
				//by another user				
				String errorUrl= successURL  + "?join=error";				
				return Response.temporaryRedirect(UriBuilder.fromUri(errorUrl).build()).build();
			}	
					
			
			int userId = Integer.parseInt((String)session.getAttribute("userId"));
			System.out.println(userId);
			
			//insert in User value of googleId and picture by userId
			User user=null;
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
		
		//CASE 2: Login to site. Session is not set. Find User by googleId
		//CASE 3: Registration. Session is not set. Create User with GoogleId and SocialPhoto
		
				
		//Check if user exist by googleId
		User user=null;
		
		try {
			
			user = userRep.getByGoogleId(googleId);
			
		} catch (Exception e) {
			return SERVER_ERROR;
		}
		
		if (user != null) {
			//Case 2
			
			//creating Session for founded user. Setting params
			System.out.println("creating session");	
			
			
			setUpSuccessSession(user, session, "success login with GoogleId");			
			session.setAttribute("refreshGoogleToken", refreshGoogleToken);
	        			
			//Entering to site with Session			
			return Response.temporaryRedirect(UriBuilder.fromUri(successURL).build()).build();
			
		}	
		
		//else CASE 3
		
		
		//creating User to register		
		User userToReg = new User();
		
		String userLogin;
		if (name!=null && !name.isEmpty()) {
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
		
		
		//inserting user to DB
		try {
			
			userRep.insert(userToReg);
						
		} catch (Exception e) {
			return SERVER_ERROR;
		}
		
		//creating session		
		setUpSuccessSession(userToReg, session, "successful Registration with GoogleId");
		session.setAttribute("refreshGoogleToken", refreshGoogleToken);
		//session.setAttribute("user", userToReg);
			
		
		//Entering to site with Session		
		return Response.temporaryRedirect(UriBuilder.fromUri(successURL).build()).build();
		
	}	
	
	
	@GET
	@Path("login/google_login_direct")			//http://localhost:8080/webapi/account/login/google_login_direct
	public Response directGoogleLoginWithOldAccessToken(@Context HttpServletRequest req, 														
														@QueryParam("code") String refreshGoogleToken) {
		
		
		String pathAll = req.getRequestURL().toString(); 
		String pathMain =pathAll.replace("webapi/account/login/google_login_direct", "");
		System.out.println("pathMain - " + pathMain);
		String successURL = pathMain + "#/ua/user/profile";
		System.out.println(successURL);
		String callbackUrlG = pathMain + callbackUrlGPath;
		System.out.println(callbackUrlG);
		

		System.out.println("Google refresh token - "+ refreshGoogleToken);		
		
		//getting new access token with old refreshToken
		OAuthRequest request = new OAuthRequest(Verb.POST, "https://www.googleapis.com/oauth2/v3/token");
		request.addBodyParameter("grant_type", "refresh_token");
		request.addBodyParameter("refresh_token", refreshGoogleToken); // were accessToken is the Token object you want to refresh.
		request.addBodyParameter("client_id", apiKeyG);
	    request.addBodyParameter("client_secret", apiSecretG);
	    	
		org.scribe.model.Response response = request.send();
		
		System.out.println(response.getCode());		//200 - success
		System.out.println(response.getBody());		//JSON response
						
		//JSON string from Google response
		String json = response.getBody();
		
		//parse string 
		String new_access_token=null;
		
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
		
		//New request to protected resource with new accessToken and old refreshToken
	
		OAuthService service2 =null;
		
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
		
		Token accessToken = new Token(new_access_token, refreshGoogleToken);	
		
		//Request protected resource
		OAuthRequest request2 = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
		service2.signRequest(accessToken, request2);
		org.scribe.model.Response response2 = request2.send();
				
		System.out.println(response2.getCode());		//200 - success
		System.out.println(response2.getBody());		//JSON response
				
		//JSON string from Google response
		String json2 = response2.getBody();
						
		//parse string 
		String googleId=null;
				
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
		
		//Login to site. Session is not set. Find User by googleId		
		HttpSession sessionNew = req.getSession(true);
		
		//Check if user exist by googleId
		User user=null;
		
		try {
			
			user = userRep.getByGoogleId(googleId);
			System.out.println(user);
			
		} catch (Exception e) {
			return SERVER_ERROR;
		}
		
		if (user == null) {
			return NOT_FOUND;
		}
			
		//creating Session for founded user. Setting params
		setUpSuccessSession(user, sessionNew, "success direct login with GoogleId");	
		sessionNew.setAttribute("refreshGoogleToken", refreshGoogleToken);
			
		return Response.status(Response.Status.OK).entity(successURL).build();
		
	}	

	private static void setUpSuccessSession(User user, HttpSession session, String success){
		
		session.setAttribute("userName",user.getName());
		session.setAttribute("userId",user.getId().toString()); 
		session.setAttribute("userSurname",user.getSurname());
		session.setAttribute("socialLogin",user.getSocialLogin());
		session.setAttribute("userRoleId",user.getUserRole().get(0).getId().toString());
		session.setAttribute("userRole",user.getUserRole().get(0).getRole());		
		session.setAttribute("successMesage", success);
		session.setAttribute("user", user);
		
		//creating string for accessToken
		String accessToken = (String)session.getId() + ":" + (String)session.getAttribute("userId");
		
		System.out.println("decoded accesToken - " + accessToken);
		
		String accessTokenEncoded=null;
		
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