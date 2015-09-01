package com.animals.app.controller.resource;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.annotation.security.PermitAll;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;
import org.scribe.model.Token;

import com.animals.app.domain.User;
import com.animals.app.repository.Impl.UserRepositoryImpl;
import com.animals.app.service.MailSender;

/**
 * Created by 41X on 8/16/2015.
 */

@Path("account")
@PermitAll 
public class AuthorizationResource {
	
	private final Response BAD_REQUEST = Response.status(Response.Status.BAD_REQUEST).build();	
	private final Response NOT_FOUND = Response.status(Response.Status.NOT_FOUND).build();	
	private final Response SERVER_ERROR = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	
	private UserRepositoryImpl userRep = new UserRepositoryImpl();	
	
	
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("login/{rememberMe}")//http:localhost:8080/webapi/account/login
	public Response loginToSite (@Context HttpServletRequest req, 
									@PathParam ("rememberMe") String rememberMe) {
				
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
               
                        
        //checking if user exist. If not - return username or password is not correct        
        User user;
		try {
			user = userRep.checkIfUserExistInDB(username, password);					
		} catch (Exception e) {
			return SERVER_ERROR;
		}
                        
        if (user == null) return NOT_FOUND;
                     	
        // User exist. setting session params(username, userrole, userId etc.) from User		
		
        //creating session
        HttpSession session = req.getSession(true);
        
        System.out.println(rememberMe);
        if(rememberMe.equals("ON")){
        	System.out.println("remember me ON");
        	session.setMaxInactiveInterval(60*60*24*30);			//session duration - 30 days    	   	
        } else {
        	System.out.println("remember me OFF");
        	session.setMaxInactiveInterval(60*30*3);					//session duration - 90 min
        }
        
        //setSuccessAtribute(session);        
        String sessionSuccess = setUpSuccessSession(user, session, "Successful login"); 
        session.setAttribute("user", user);
        
        System.out.println(sessionSuccess);

	    return Response.status(Response.Status.OK).entity(sessionSuccess).build();
		
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("logout")//http:localhost:8080/webapi/account/refresh
	public Response destroySession(@Context HttpServletRequest req) {
				
		HttpSession session = req.getSession(true);	
	
		//destroying session		
		session.invalidate();	
		
		//returning json with empty user		
		String destroyedSession = setUpDestroyedSession("Session Destroyed"); 
					
		return Response.status(Response.Status.OK).entity(destroyedSession).build();		
		
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
			 System.out.println("socialLogin2 - "+socialLogin2);
		} catch (Exception e) {
			return SERVER_ERROR;
		}
		
		if(socialLogin2 != null && !socialLogin2.isEmpty()){
						
			String socialLoginIsAlreadyInUse = setUpDestroyedSession("SocialLogin is already in use by another User"); 
			
			return Response.status(Response.Status.OK).entity(socialLoginIsAlreadyInUse).build();
		}				
				
		try {
			userRep.insert(user);			
		} catch (Exception e) {
			return SERVER_ERROR;
		}
		
		//sending mail
		String recipientEmail = user.getEmail();
		System.out.println("email - " + recipientEmail);
		
		String username = user.getSocialLogin();
		String code = user.getPassword();
		
		String title = "Confirmation Registration on AnimalWebApp";
		String message = "Folow link http://localhost:8080/#/ua/user/confirmRegistration?username="+username+"&code="+ code;
		System.out.println("message - " + message);
		
		try {
			System.out.println("sending mail");			
			MailSender ms = new MailSender();
			ms.newsSend(recipientEmail, message);
			System.out.println("mail send. Check!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
        
		String regWithoutConfirm = setUpDestroyedSession("Registration Success. Waiting for confirmation"); 
        //response with UserRole = null, UserType = null, UserSocialLogin=null
	    return Response.status(Response.Status.OK).entity(regWithoutConfirm).build();	 
		
	}
	
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("confirmRegistration")//http:localhost:8080/webapi/account/confirmRegistration
	public Response loginToSite (@Context HttpServletRequest req) {
		
		//checking if user exist in DB
		
		//updating user with userRole="гість" 
		
		//creating session
        HttpSession session = req.getSession(true);
//		
//        String sessionSuccessReg = setUpSuccessSession(user, session, "Successful Registration"); 
//        session.setAttribute("user", user);
//        
//        System.out.println(sessionSuccessReg);     
//       
		
		//response with UserRole = null, UserType = null, UserSocialLogin=null
	    return Response.status(Response.Status.OK).entity("sessionSuccessReg").build();	 
		
	
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("refresh")//http:localhost:8080/webapi/account/refresh
	public Response refreshSession(@Context HttpServletRequest req) {
		
		//creating session				
		HttpSession session = req.getSession(true);	
		
		//checking if session is stil going on by geting params from it. if not - returning json with empty user	
		if(session.getAttribute("userId") == null){
			
			String destroyedSession = setUpDestroyedSession("Session Destroyed");  
							
			return Response.status(Response.Status.OK).entity(destroyedSession).build();
			
		}
		
		//if session has params - returning json with session same params. REFRESH
		
		String str = buildResponse(session);		
    
		System.out.println(str);

		return Response.status(Response.Status.OK).entity(str).build();
		
	}
		
	
private static String setUpSuccessSession(User user, HttpSession session, String success){
		
		session.setAttribute("userName",user.getName());
		session.setAttribute("userId",user.getId().toString()); 
		session.setAttribute("userSurname",user.getSurname());
		session.setAttribute("socialLogin",user.getSocialLogin());
		session.setAttribute("userRoleId",user.getUserRole().get(0).getId().toString());
		session.setAttribute("userRole",user.getUserRole().get(0).getRole());
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
        			"\", \"accessToken\" : \"" + (String)session.getAttribute("accessToken") +
        			"\"}";
		return str;
	};
	
	private static String buildResponse(HttpSession session){
		
		String str = "{\"sessionId\" : \"" + (String)session.getId() + 
    			"\", \"userId\" : \"" + (String)session.getAttribute("userId") +
    			"\", \"userName\" : \"" + (String)session.getAttribute("userName") +
    			"\", \"userSurname\" : \"" + (String)session.getAttribute("userSurname") +
    			"\", \"socialLogin\" : \"" + (String)session.getAttribute("socialLogin") +
    			"\", \"userRole\" : \"" + (String)session.getAttribute("userRole") +
    			"\", \"userRoleId\" : \"" + (String)session.getAttribute("userRoleId") +
    			"\", \"successMesage\" : \"" + (String)session.getAttribute("successMesage") +
    			"\", \"accessToken\" : \"" + (String)session.getAttribute("accessToken") +
    			"\", \"refreshGoogleToken\" : \"" + (String)session.getAttribute("refreshGoogleToken") +
    			"\", \"accessGoogleToken\" : \"" + (String)session.getAttribute("accessGoogleToken") +
    			"\"}";		
		
		return str;
	}
	
	
	
	private static String setUpDestroyedSession(String errorMesage){
		
		String destroyedSession = "{\"userId\" : \"0\", \"errorMesage\" : \"" + errorMesage + "\"}";   	
		
		return destroyedSession;
		
	}	

}