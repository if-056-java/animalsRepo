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
	private static final String callbackUrlG = "http://localhost:8080/webapi/account/login/google_token";
	
	// url to redirect after OAuth end
	private String url = "http://localhost:8080/#/ua/user/profile";  //No Bug, No Cry
	
	
	@GET
	@Path("login/google")		//http:localhost:8080/webapi/account/login/google
	public Response googleLogin() {

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

		return Response.temporaryRedirect(UriBuilder.fromUri(authorizationUrl).build()).build();
	}

	@GET
	@Path("login/google_token")			//http://localhost:8080/webapi/account/login/google_token
	public Response getGoogleAccessToken(@QueryParam("code") String token, @Context HttpServletRequest req) {

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
			

		//Request protected resource
		OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
		service2.signRequest(accessToken, request);
		org.scribe.model.Response response = request.send();
		
		System.out.println(response.getCode());		//200 - success
		System.out.println(response.getBody());		//JSON response
		
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
				//TEMPORARY Returning back without joining accounts. should provide some message - 
				// - user with this GoogleId can't be initialized. 
				//maybe we should provide some additionall atribute to our session - seesion.message
				//with some Error content to show on site.
				session.setAttribute("errorMesage", "this GoogleID is already in use by another User");
				
				return Response.temporaryRedirect(UriBuilder.fromUri(url).build()).build();
			}	
					
			
			int userId = Integer.parseInt((String)session.getAttribute("userId"));
			System.out.println(userId);
			
			//insert in User value of googleId and picture by userId
			try {
				
				User user = userRep.getById(userId);	
				
				user.setGoogleId(googleId);
				user.setSocialPhoto(link);
				
				userRep.update(user);
				
			} catch (Exception e) {
				return SERVER_ERROR;
			}		
			
			session.setAttribute("successMesage", "Successful joining Google account");
			session.setAttribute("refreshToken", token);
			
			return Response.temporaryRedirect(UriBuilder.fromUri(url).build()).build();
			
		}
		
		//CASE 2: Login to site. Session is not set. Find User by googleId
		//CASE 3: Registration. Session is not set. Create User with GoogleId and SocialPhoto
		
		//HttpSession sessionNew = req.getSession(true);   hide
		
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
			
			
			setUpSuccessSession(user, session, "success login with GoogleId", token);
	        			
			//Entering to site with Session			
			return Response.temporaryRedirect(UriBuilder.fromUri(url).build()).build();
			
		}	
		
		//else CASE 3
		
		
		//creating User to register		
		User userToReg = new User();
		
		userToReg.setName(name);
		userToReg.setSurname("N/A");
		userToReg.setSocialLogin(name);
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
		userRole.setId(3);										//id=3 for guest	
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
		setUpSuccessSession(userToReg, session, "successful Registration with GoogleId", token);		
		
		//Entering to site with Session		
		return Response.temporaryRedirect(UriBuilder.fromUri(url).build()).build();
		
	}
	
	
	
	private static void setUpSuccessSession(User user, HttpSession session, String success, String token){
		
		session.setAttribute("userName",user.getName());
		session.setAttribute("userId",user.getId().toString()); 
		session.setAttribute("userSurname",user.getSurname());
		session.setAttribute("socialLogin",user.getSocialLogin());
		session.setAttribute("userRoleId",user.getUserRole().get(0).getId().toString());
		session.setAttribute("userRole",user.getUserRole().get(0).getRole());
		session.setAttribute("refreshToken", token);
		session.setAttribute("successMesage", success);
		
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

		
		//creating JSON string with session params
        String str = "{\"sessionId\" : \"" + (String)session.getId() + 
        			"\", \"userId\" : \"" + (String)session.getAttribute("userId") +
        			"\", \"userName\" : \"" + (String)session.getAttribute("userName") +
        			"\", \"userSurname\" : \"" + (String)session.getAttribute("userSurname") +
        			"\", \"socialLogin\" : \"" + (String)session.getAttribute("socialLogin") +
        			"\", \"userRole\" : \"" + (String)session.getAttribute("userRole") +
        			"\", \"userRoleId\" : \"" + (String)session.getAttribute("userRoleId") +
        			"\", \"successMesage\" : \"" + (String)session.getAttribute("successMesage") +
        			"\", \"refreshToken\" : \"" + (String)session.getAttribute("refreshToken") +
        			"\", \"accessToken\" : \"" + (String)session.getAttribute("accessToken") +
        			"\"}";
        
        System.out.println(str);

	}
	
	@GET
	@Path("login/google_login_direct")			//http://localhost:8080/webapi/account/login/google_login_direct
	public Response directGoogleLoginWithOldAccessToken(@Context HttpServletRequest req, 														
														@QueryParam("code") String refreshToken) {

		
		OAuthService service2 =null;

		try {
			service2 = new ServiceBuilder()
					.provider(Google2Api.class)
					.apiKey(apiKeyG)
					.apiSecret(apiSecretG)
					//.callback(callbackUrlG)
					.scope(SCOPE)
					.offline(true)
					.build();
			
		} catch (Exception e1) {			
			e1.printStackTrace();
		}
		
		Verifier v = new Verifier(refreshToken);	
		System.out.println("old verifier"+v);

		//Token accessToken = new Token("ACCESS_TOKEN", "REFRESH_TOKEN");
		
		Token accessToken = service2.getAccessToken(EMPTY_TOKEN, v);
		System.out.println("old accessToken"+accessToken.toString());
		
		Token newAccessToken = service2.getAccessToken(accessToken, v);
		System.out.println("new accessToken"+newAccessToken.toString());
		
		accessToken = newAccessToken;					

		//Request protected resource
		OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
		service2.signRequest(accessToken, request);
		org.scribe.model.Response response = request.send();
		
		System.out.println(response.getCode());		//200 - success
		System.out.println(response.getBody());		//JSON response
		
		//JSON string from Google response
		String json = response.getBody();
		
		//parse string 
		String googleId=null;
		
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
								
			googleId = (String) jsonObject.get("id");
			System.out.println("id is: " + googleId);			
			
			
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}		
	
		
		//CASE 2: Login to site. Session is not set. Find User by googleId
		
		
		HttpSession sessionNew = req.getSession(true);
		
		//Check if user exist by googleId
		User user=null;
		
		try {
			
			user = userRep.getByGoogleId(googleId);
			
		} catch (Exception e) {
			return SERVER_ERROR;
		}
		
		if (user == null) {}
			
		//creating Session for founded user. Setting params
		System.out.println("creating session");	
			
			
		setUpSuccessSession(user, sessionNew, "success login with GoogleId", refreshToken);
	        			
		//Entering to site with Session			
		return Response.temporaryRedirect(UriBuilder.fromUri(url).build()).build();	
		
		
	}
	

}
