package com.animals.app.controller.resource;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("account")
public class AuthorizationResource {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("login")//http:localhost:8080/webapi/account/login
	public Response createSession (@Context HttpServletRequest req) {
		
		System.out.println("inside rest");
		
		HttpSession session = req.getSession(true);
		
		System.out.println(session.getId());
		
		session.setAttribute("name","Ivan");
		session.setAttribute("userId","25");

//        HashMap<String, String> json = new HashMap<String, String>();
//        json.put("SessionId", session.getId());

        return Response.ok(session).build();
		
	}

}
