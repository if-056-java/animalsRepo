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
		
		System.out.println("inside rest");
		
		HttpSession session = req.getSession(true);
		
		String header = req.getHeader("Authorization");
		
		System.out.println("header - " + header);
		
		String sub = header.replaceFirst("Basic" + " ", "");
		
		System.out.println(sub);
		
		String usernameAndPassword=null;
		
		try {
			byte[] decoded = Base64.decodeBase64(sub);
//            byte[] decodedBytes = Base64.getDecoder().decode(sub);
            usernameAndPassword = new String(decoded, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
        final String username = tokenizer.nextToken();
        final String password = tokenizer.nextToken();
        
        System.out.println("username - "+username);
        System.out.println("username - "+password);
		
		System.out.println(session.getId());
		
		session.setAttribute("userName",username);
		session.setAttribute("userId",password);      

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
		
		System.out.println("ping");
				
		HttpSession session = req.getSession(true);	
		
		System.out.println((String)session.getAttribute("userId"));
		
		if(session.getAttribute("userId") == null){
			
			String str = "{\"userId\" : \"0\"}";
					
			return Response.status(Response.Status.OK).entity(str).build();
		}
		
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
		
		System.out.println("destroying");
		
		HttpSession session = req.getSession(true);
		
		//session.setAttribute("userId","0");
		
		session.invalidate();	
		
		//System.out.println((String)session.getAttribute("userId"));
			
		String str = "{\"userId\" : \"0\"}";
					
		return Response.status(Response.Status.OK).entity(str).build();
		
		
	}

}
