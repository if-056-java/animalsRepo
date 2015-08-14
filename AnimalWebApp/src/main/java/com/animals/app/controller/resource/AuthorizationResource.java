package com.animals.app.controller.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;
import org.apache.commons.codec.binary.Base64;
import java.util.StringTokenizer;

@Path("account")
public class AuthorizationResource {
	
	HttpSession session;
	
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("login")//http:localhost:8080/webapi/account/login
	public Response createSession (@Context HttpServletRequest req) {
		
		//creating session
		HttpSession session = req.getSession(true);
		
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
        final String username = tokenizer.nextToken();
        final String password = tokenizer.nextToken();
        
        //hashing values of username and password
        
        //checking if user exist. If not - return username or password is not correct
        
        // User exist. setting session params(username, userrole, userId) from User		
		
		
		session.setAttribute("userName",username);
		session.setAttribute("userId",password);  
		
		//returning json with session params

        String str = "{\"sessionId\" : \"" + (String)session.getId() + 
        			"\", \"userId\" : \"" + (String)session.getAttribute("userId") +
        			"\", \"userName\" : \"" + (String)session.getAttribute("userName") +
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

}
