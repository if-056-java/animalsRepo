package com.animals.app.controller.resource;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
import com.animals.app.repository.Impl.UserRepositoryImpl;

/**
 * Created by 41X on 8/16/2015.
 */

@Path("account")
public class AuthorizationResource {
	
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
	//private String url = "http://localhost:8080/#/ua/user/profile";  //temporary Bug with session
	private String url = "http://localhost:8080/#/ua";
	
	
	
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("login")//http:localhost:8080/webapi/account/login
	public Response createSession (@Context HttpServletRequest req) {
				
		//reading header from request
		String header = req.getHeader("Authorization");
		
		//formating header
		String sub = header.replaceFirst("Basic" + " ", "");
				
		String usernameAndPassword=null;
		
		try {
			byte[] decoded = Base64.decodeBase64(sub);
            usernameAndPassword = new String(decoded, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
		
        final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");

        String username = tokenizer.nextToken();
        String password = tokenizer.nextToken();      
       
        
        System.out.println("password - " + password);
        System.out.println("socialLogin - " + username);    
                     
                        
        //checking if user exist. If not - return username or password is not correct        
        User user;
		try {
			user = userRep.checkIfUserExistInDB(username, password);
			System.out.println(user.getName());
			System.out.println(user.getSocialLogin());			
		} catch (Exception e) {
			return SERVER_ERROR;
		}
                        
        if (user == null) return NOT_FOUND;
        
        System.out.println(user.getId());
        System.out.println(user.getName());
        System.out.println(user.getSurname());
        System.out.println(user.getSocialLogin());
        System.out.println(user.getUserRole().get(0));
     	
        // User exist. setting session params(username, userrole, userId) from User		
		
        //creating session
        HttpSession session = req.getSession(true);
        
       
		
        System.out.println("ping2");
        
		session.setAttribute("userName",user.getName());
		session.setAttribute("userId",user.getId().toString()); 
		session.setAttribute("userSurname",user.getSurname());
		session.setAttribute("socialLogin",user.getSocialLogin());
		session.setAttribute("userRole",user.getUserRole().get(0).getId().toString());
		
		//returning json with session params

        String str = "{\"sessionId\" : \"" + (String)session.getId() + 
        			"\", \"userId\" : \"" + (String)session.getAttribute("userId") +
        			"\", \"userName\" : \"" + (String)session.getAttribute("userName") +
        			"\", \"userSurname\" : \"" + (String)session.getAttribute("userSurname") +
        			"\", \"socialLogin\" : \"" + (String)session.getAttribute("socialLogin") +
        			"\", \"userRole\" : \"" + (String)session.getAttribute("userRole") +
        			"\"}";
        
        System.out.println(str);

	    return Response.status(Response.Status.OK).entity(str).build();
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("refresh")//http:localhost:8080/webapi/account/refresh
	public Response refreshSession(@Context HttpServletRequest req) {
		
		//creating session				
		HttpSession session = req.getSession(true);	
		
		//checking if session is stil going on by geting params from it. if not - returning json with empty user	
		if(session.getAttribute("userId") == null){
			
			String str = "{\"userId\" : \"0\"}";
					
			return Response.status(Response.Status.OK).entity(str).build();
		}
		
		//if session has params - returning json with session same params. REFRESH
		
		String str = "{\"sessionId\" : \"" + (String)session.getId() + 
    			"\", \"userId\" : \"" + (String)session.getAttribute("userId") +
    			"\", \"userName\" : \"" + (String)session.getAttribute("userName") +
    			"\", \"userSurname\" : \"" + (String)session.getAttribute("userSurname") +
    			"\", \"socialLogin\" : \"" + (String)session.getAttribute("socialLogin") +
    			"\", \"userRole\" : \"" + (String)session.getAttribute("userRole") +
    			"\"}";
    
		System.out.println(str);

		return Response.status(Response.Status.OK).entity(str).build();
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("logout")//http:localhost:8080/webapi/account/refresh
	public Response destroySession(@Context HttpServletRequest req) {
				
		
		HttpSession session = req.getSession(true);	
		
		//destroying session		
		session.invalidate();	
		
		//returning json with empty user		
		String str = "{\"userId\" : \"0\"}";
					
		return Response.status(Response.Status.OK).entity(str).build();		
		
	}
	
	
	@POST
	@Path("registration")//http:localhost:8080/webapi/account/registration
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public Response registerUser (@Context HttpServletRequest req, User user) {	
		
		if (user==null) return BAD_REQUEST;
		
		String socialLogin = user.getSocialLogin();
		
		System.out.println(socialLogin);
		
		//check if user socialLogin unique
		String socialLogin2="logintoChange";
		try {
			 socialLogin2 = userRep.checkIfUsernameUnique(socialLogin);
			 System.out.println("socialLogin2"+socialLogin2);
		} catch (Exception e) {
			return SERVER_ERROR;
		}
		
		if(socialLogin2 != null && !socialLogin2.isEmpty()){
			System.out.println("zrada");
			
			String str = "{\"userId\" : \"0\"}";
			
			return Response.status(Response.Status.OK).entity(str).build();
		}				
		
		
		try {
			userRep.insert(user);			
		} catch (Exception e) {
			return SERVER_ERROR;
		}
		
		//creating session
        HttpSession session = req.getSession(true);
		
        System.out.println("ping2");
        
		session.setAttribute("userName",user.getName());
		session.setAttribute("userId",user.getId().toString()); 
		session.setAttribute("userSurname",user.getSurname());
		session.setAttribute("socialLogin",user.getSocialLogin());
		session.setAttribute("userRole",user.getUserRole().get(0).getId().toString());
		
		//returning json with session params

        String str = "{\"sessionId\" : \"" + (String)session.getId() + 
        			"\", \"userId\" : \"" + (String)session.getAttribute("userId") +
        			"\", \"userName\" : \"" + (String)session.getAttribute("userName") +
        			"\", \"userSurname\" : \"" + (String)session.getAttribute("userSurname") +
        			"\", \"socialLogin\" : \"" + (String)session.getAttribute("socialLogin") +
        			"\", \"userRole\" : \"" + (String)session.getAttribute("userRole") +
        			"\"}";
        
        System.out.println(str);

	    return Response.status(Response.Status.OK).entity(str).build();		
		
	}
	
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

		boolean refresh = true;
		boolean startOver = true;

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
			

		System.out.println("Now we're going to access a protected resource...");
		OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
		service2.signRequest(accessToken, request);
		org.scribe.model.Response response = request.send();
		System.out.println("Got it! Lets see what we found...");
		System.out.println();
		System.out.println(response.getCode());
		System.out.println(response.getBody());
		
		//json string from Google response
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
		
			String userId = (String)session.getAttribute("userId");
			
			System.out.println(userId);
			
			int userId2 = Integer.parseInt(userId);
			
			//insert in User value of googleId and picture by userId
			try {
				
				User user = userRep.getById(userId2);	
				
				user.setGoogleId(googleId);
				user.setSocialPhoto(link);
				
				userRep.update(user);
				
			} catch (Exception e) {
				return SERVER_ERROR;
			}
			
			System.out.println("Thats it man! Go and build something awesome with Scribe! :)");	
			
			
			return Response.temporaryRedirect(UriBuilder.fromUri(url).build()).build();
			
		}
		
		//CASE 2: Login to site. Session is not set. Find User by googleId
		//CASE 3: Registration. Session is not set. Create User with GoogleId and SocialPhoto
		
		//Check if user exist by googleId
		User user=null;
		
		//try {
			user = userRep.getByGoogleId(googleId);
//		} catch (Exception e) {
//			return SERVER_ERROR;
//		}
		
		if (user != null) {
			//Case 2
			
			//creating Session for founded user. Setting params
			System.out.println("creating session");
	        
			session.setAttribute("userName",user.getName());
			session.setAttribute("userId",user.getId().toString()); 
			session.setAttribute("userSurname",user.getSurname());
			session.setAttribute("socialLogin",user.getSocialLogin());
			session.setAttribute("userRole",user.getUserRole().get(0).getId().toString());
			
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
		userToReg.setPassword("root");		
		userToReg.setSocialPhoto(link);
//		userToReg.setUserRole(null);
//		userToReg.setUserType(null);
		//reg date
		//userRole
		
		//inserting user to DB
		userRep.insert(userToReg);
		
		//creating session
		System.out.println("creating session for registered");
        
		session.setAttribute("userName",userToReg.getName());
		session.setAttribute("userId",userToReg.getId().toString()); 
		session.setAttribute("userSurname",userToReg.getSurname());
		session.setAttribute("socialLogin",userToReg.getSocialLogin());
		session.setAttribute("userRole",user.getUserRole().get(0).getId().toString());
		
		//Entering to site with Session
		
		return Response.temporaryRedirect(UriBuilder.fromUri(url).build()).build();
		
	}
	
	

}
