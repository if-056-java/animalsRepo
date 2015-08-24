package com.animals.app.service;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.commons.codec.binary.Base64;

import com.animals.app.domain.User;
import com.animals.app.repository.Impl.UserRepositoryImpl;
import com.animals.app.service.UserSecurityContext;

/**
 * Filtering incoming requests with accessToken.
 * This filter works with REST @RolesAllowed annotation
 */
@Provider
@PreMatching
public class AuthorizationFilter implements ContainerRequestFilter {
	
	private static final String ACCESS_TOKEN_HEADER = "AccessToken";
	private @Context HttpServletRequest req;
	private UserRepositoryImpl userRep = new UserRepositoryImpl();

	@Override
	public void filter(ContainerRequestContext requestContext) throws WebApplicationException {
		
		System.out.println("filter Start");
		
		HttpSession session = req.getSession(true);		
		

		//AUTHENTICATION Read request header and define USER from accessToken
		//get session ID from accessToken		
		if(requestContext.getHeaderString(ACCESS_TOKEN_HEADER) != null){
			
			System.out.println(requestContext.getUriInfo().getPath()); 
			
			System.out.println("filter ping");			
			
			String accessTokenEncoded = requestContext.getHeaderString(ACCESS_TOKEN_HEADER);
			
			String accessTokenDecoded=null;
			
			try {
				byte[] decoded = Base64.decodeBase64(accessTokenEncoded);
				accessTokenDecoded = new String(decoded, "UTF-8");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			
			final StringTokenizer tokenizer = new StringTokenizer(accessTokenDecoded, ":");

	        String sessionId = tokenizer.nextToken();
	        String userId = tokenizer.nextToken();
	        
			
			int userId2 = Integer.parseInt(userId);
			
			//check if userId is valid
	        User user = userRep.getById(userId2);
			
	        if (user == null) {	        	
	            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
	        } 
			
			//AUTHORIZATION Check if User role matches with @RolesAllowed annotation, 
	        //(if not - 406 - not acceptable)	        
	        requestContext.setSecurityContext(new UserSecurityContext(user));
        
		}
		
	}

}