package com.animals.app.controller.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.animals.app.domain.User;
import com.animals.app.domain.UserRole;
import com.animals.app.domain.UserType;
import com.animals.app.repository.Impl.UserRepositoryImpl;
import com.animals.app.service.MailSender;

/**
 * Created by 41X on 8/16/2015.
 */

@Path("account")
@PermitAll 
public class AuthorizationResource {
	
	private static Logger LOG = LogManager.getLogger(AuthorizationResource.class);
	
	private final Response BAD_REQUEST = Response.status(Response.Status.BAD_REQUEST).build();	
	private final Response NOT_FOUND = Response.status(Response.Status.NOT_FOUND).build();	
	private final Response SERVER_ERROR = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	
	private final int LONG_SESSION=2592000;
	private final int SHORT_SESSION= 10800;
	
	private static final String LOGIN_CONFIRM_REG = "Now confirmation should be done";
	private static final String SUCCESSFULL_LOGIN = "Successful login";
	private static final String SESSION_DESTROYED = "Session destroyed";
	private static final String LOGIN_NOT_UNIQUE = "SocialLogin is already in use by another User";
	private static final String CONFIRMATION = "Now confirmation should be done";
	private static final String SUCCESSFUL_REGISTRATION = "Successful Registration";
	private static final String DESTROYED_SESSION = "Session Destroyed";
	
	private UserRepositoryImpl userRep = new UserRepositoryImpl();	
	
	
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("login/{rememberMe}")//http:localhost:8080/webapi/account/login
	public Response loginToSite (@Context HttpServletRequest req, 
								 @PathParam ("rememberMe") @NotNull String rememberMe) {
				
		//reading header from request
		String header = req.getHeader("Authorization");
		
		//formating header
		String sub = header.replaceFirst("Basic" + " ", "");
				
		String usernameAndPassword=null;
		
		try {
			byte[] decoded = Base64.decodeBase64(sub);
            usernameAndPassword = new String(decoded, "UTF-8");
        } catch (IOException e) {
        	LOG.error(e);
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
			LOG.error(e);
			return SERVER_ERROR;
		}
                        
        if (user == null) return NOT_FOUND;

        // User exist. setting session params(username, userrole, userId etc.) from User
        if (!user.isActive()){
        	
        	String regWithoutConfirm = buildResponseEntity(0, LOGIN_CONFIRM_REG);
        	
        	return Response.status(Response.Status.BAD_REQUEST).entity(regWithoutConfirm).build();
        }
		
        //creating session
        HttpSession session = req.getSession(true);      
       
        if(rememberMe.equals("ON")){        	
        	session.setMaxInactiveInterval(LONG_SESSION);					//session duration - 30 days    	   	
        } else {        	
        	session.setMaxInactiveInterval(SHORT_SESSION);					//session duration - 90 min
        }
        
        //setSuccessAtribute(session);        
        String sessionSuccess = setUpSuccessSession(user, session, SUCCESSFULL_LOGIN); 
        session.setAttribute("user", user);

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
		String destroyedSession = buildResponseEntity(0, SESSION_DESTROYED);
							
		return Response.status(Response.Status.OK).entity(destroyedSession).build();		
		
	}
	
	
	@POST
	@Path("registration")//http:localhost:8080/webapi/account/registration
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public Response registerUser (@Context HttpServletRequest req, @Valid User user) {	
				
		String socialLogin = user.getSocialLogin();		
				
		//check if user socialLogin unique
		String socialLogin2;
		try {
			 socialLogin2 = userRep.checkIfUsernameUnique(socialLogin);			
		} catch (Exception e) {
			LOG.error(e);
			return SERVER_ERROR;
		}
		
		if(socialLogin2 != null && !socialLogin2.isEmpty()){
						
			String socialLoginIsAlreadyInUse = buildResponseEntity(0, LOGIN_NOT_UNIQUE);
			
			return Response.status(Response.Status.BAD_REQUEST).entity(socialLoginIsAlreadyInUse).build();
		}
		
		String emailVerificator = UUID.randomUUID().toString();		
		
		user.setEmailVerificator(emailVerificator);
				
		try {
			userRep.insert(user);			
		} catch (Exception e) {
			LOG.error(e);
			return SERVER_ERROR;
		}
		
		//sending mail
		String recipientEmail = user.getEmail();
				
		String username = user.getSocialLogin();
		
		//Define URLs and callback
		String pathAll = req.getRequestURL().toString(); 
		String pathMain =pathAll.replace("webapi/account/registration", "");	
			
		String message = buildResponseMessage(pathMain, username, emailVerificator);
						
		try {
			MailSender ms = new MailSender();
			ms.newsSend(recipientEmail, message);			
		} catch (Exception e) {			
			LOG.error(e);
		}        
		
		String regWithoutConfirm = buildResponseEntity(1, CONFIRMATION);
        
	    return Response.status(Response.Status.OK).entity(regWithoutConfirm).build();	 
		
	}


	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("confirmRegistration/{socialLogin}/{code}")//http:localhost:8080/webapi/account/confirmRegistration/socialLogin/code
	public Response loginToSite (@Context HttpServletRequest req,
								 @PathParam ("socialLogin") @NotNull String socialLogin,
								 @PathParam ("code") @NotNull String code) {
		
		
		//user Verification. checking if user with verification code exist.    
        User user;
		try {			
			user = userRep.userVerification(socialLogin, code);				
		} catch (Exception e) {
			LOG.error(e);
			return SERVER_ERROR;
		}
		                        
        if (user == null) return NOT_FOUND;
        
        //update user active
        user.setActive(true);      
        
        try {        	
	        userRep.update(user);        	
        } catch (Exception e) {
        	LOG.error(e);
			return SERVER_ERROR;
		}
                		
		//creating session
        HttpSession session = req.getSession(true);
		
        String sessionSuccessReg = setUpSuccessSession(user, session, SUCCESSFUL_REGISTRATION); 
        session.setAttribute("user", user);        
		
	    return Response.status(Response.Status.OK).entity(sessionSuccessReg).build();	
	
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("refresh")//http:localhost:8080/webapi/account/refresh
	public Response refreshSession(@Context HttpServletRequest req) {
		
		//creating session				
		HttpSession session = req.getSession(true);	
		
		//checking if session is stil going on by geting params from it. if not - returning json with empty user	
		if(session.getAttribute("userId") == null){
			
			String destroyedSession = buildResponseEntity(0, DESTROYED_SESSION);  
							
			return Response.status(Response.Status.OK).entity(destroyedSession).build();
			
		}
		
		//if session has params - returning json with session same params. REFRESH		
		String str = buildResponse(session);		
    
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

		String accessTokenEncoded=null;
		
		try {
			byte[] encoded = Base64.encodeBase64(accessToken.getBytes());
			accessTokenEncoded = new String(encoded, "UTF-8");
        } catch (IOException e) {
        	LOG.error(e);
            e.printStackTrace();
        }	
		
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
    			"\", \"twitterToken\" : \"" + (String)session.getAttribute("twitterToken") +
    			"\", \"twitterSecret\" : \"" + (String)session.getAttribute("twitterSecret") +
    			"\", \"facebookToken\" : \"" + (String)session.getAttribute("facebookToken") +
    			"\"}";		
		
		return str;
	}	
	
	
	private String buildResponseEntity(int i, String message) {
		
		String entity = "{\"userId\" : " + i + ", \"message\" : \"" + message + "\"}"; 
    	
		return entity;
	}
	
	private String buildResponseMessage(String pathMain, String username, String emailVerificator) {
		
		String message = "Для підтвердження реєстрації на сайті - "+ pathMain + " пройдіть за вказаною ссилкою - "
				+ pathMain + "#/ua/user/confirmRegistration?username="+username+"&code="+ emailVerificator;
		
		return message;
	}

}