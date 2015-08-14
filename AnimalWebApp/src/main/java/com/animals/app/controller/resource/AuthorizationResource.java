package com.animals.app.controller.resource;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;

import com.animals.app.domain.User;
import com.animals.app.repository.Impl.UserRepositoryImpl;

@Path("account")
public class AuthorizationResource {
	
	private final Response BAD_REQUEST = Response.status(Response.Status.BAD_REQUEST).build();	
	private final Response NOT_FOUND = Response.status(Response.Status.NOT_FOUND).build();	
	private final Response SERVER_ERROR = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	
	private UserRepositoryImpl userRep = new UserRepositoryImpl();
	
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
//        final String username = tokenizer.nextToken();
//        final String password = tokenizer.nextToken();
        String username = tokenizer.nextToken();
        String password = tokenizer.nextToken();
        
                
        //hashing values of password. Checking if User exist. username and password
        //MessageDigest messageDigest;
		try {
			System.out.println("before - " + password);
			byte[] bytesOfMessage = password.getBytes("UTF-8");
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
	        byte[] thedigest = messageDigest.digest(bytesOfMessage);
	        password = new String(thedigest, "UTF-8");
	        System.out.println("after - " + password);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}               
        
                
        //checking if user exist. If not - return username or password is not correct
        User user=userRep.checkIfUserExistInDB(username, password);
        
        if (user == null) return NOT_FOUND;
     	
        // User exist. setting session params(username, userrole, userId) from User		
		
        //creating session
        HttpSession session = req.getSession(true);
		
		session.setAttribute("userName",user.getName());
		session.setAttribute("userId",user.getId()); 
		session.setAttribute("userSurname",user.getSurname());
		session.setAttribute("socialLogin",user.getSocialLogin());
		session.setAttribute("userRole",user.getUserRole().get(0));
		
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
	
//	@GET
//	@Path("login/google")		//http:localhost:8080/webapi/account/login/google
//	public Response googleLogin() {
//
//		OAuthService service = null;
//
//		try {
//			service = new ServiceBuilder()
//					.provider(Google2Api.class)
//					.apiKey(apiKeyG)
//					.apiSecret(apiSecretG)
//					.callback(callbackUrlG)
//					.scope(SCOPE)
//					.offline(true)
//					.build();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		if (service == null) {
//			return Response.status(404).build();
//		}
//
//		String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
//
//		System.out.println("url - " + authorizationUrl);
//
//		return Response.temporaryRedirect(UriBuilder.fromUri(authorizationUrl).build()).build();
//	}
//
//	@GET
//	@Path("login/google_token")			//http:localhost:8080/webapi/account/login/google_token
//	public Response getGoogleAccessToken(@QueryParam("code") String token) {
//
//		boolean refresh = true;
//		boolean startOver = true;
//
//		Verifier v = new Verifier(token);
//
//		System.out.println("token - " + token);
//		
//		OAuthService service2 =null;
//
//		try {
//			service2 = new ServiceBuilder()
//					.provider(Google2Api.class)
//					.apiKey(apiKeyG)
//					.apiSecret(apiSecretG)
//					.callback(callbackUrlG)
//					.scope(SCOPE)
//					.offline(true)
//					.build();
//			
//		} catch (Exception e1) {			
//			e1.printStackTrace();
//		}
//
//		Token accessToken = new Token("ACCESS_TOKEN", "REFRESH_TOKEN");
//
//		if (startOver) {
//
//			// Trade the Request Token and Verfier for the Access Token
//			System.out.println("Trading the Request Token for an Access Token...");
//			accessToken = service2.getAccessToken(EMPTY_TOKEN, v);
//			System.out.println("Got the Access Token!");
//			System.out.println("(if your curious it looks like this: " + accessToken + " )");
//			System.out.println();
//		}
//
//		if (refresh) {
//			try {
//				// Trade the Refresh Token for a new Access Token
//				System.out.println("Trading the Refresh Token for a new Access Token...");
//				Token newAccessToken = service2.getAccessToken(accessToken, v);
//				System.out.println("Got the Access Token!");
//				System.out.println("(if your curious it looks like this: " + newAccessToken + " )");
//				System.out.println();
//				accessToken = newAccessToken;
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//		System.out.println("Now we're going to access a protected resource...");
//		OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
//		service2.signRequest(accessToken, request);
//		org.scribe.model.Response response = request.send();
//		System.out.println("Got it! Lets see what we found...");
//		System.out.println();
//		System.out.println(response.getCode());
//		System.out.println(response.getBody());
//		
//		//get GoogleID and SocsalLogin from response
//		
//		// check if user exist in DB
//		//if exist - start session
//		//if nor - create User (with a lot empty fields)
//		//start session for created user
//
//		System.out.println();
//		System.out.println("Thats it man! Go and build something awesome with Scribe! :)");
//		
//		
//		
//		return Response.temporaryRedirect(UriBuilder.fromUri(url).build()).build();
//		
//	}
	
	

}
