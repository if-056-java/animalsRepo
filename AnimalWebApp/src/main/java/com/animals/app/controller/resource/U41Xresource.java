package com.animals.app.controller.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.animals.app.domain.User;
import com.animals.app.repository.Impl.UserRepositoryImpl;

@Path("users")
public class U41Xresource {
	
	private final Response BAD_REQUEST = Response.status(Response.Status.BAD_REQUEST).build();
	
	private final Response NOT_FOUND = Response.status(Response.Status.NOT_FOUND).build();
	
	private UserRepositoryImpl userRep = new UserRepositoryImpl();
	
	@GET //http:localhost:8080/webapi/users/id
	@Produces(MediaType.APPLICATION_JSON)
	@Path("user/{userId}") 
	public Response getUserById(@PathParam ("userId") String id) {
				
		int parseId = 0;
		
		try {
            if (id == null)
                return BAD_REQUEST;
            parseId = Integer.parseInt(id);
        } catch (NumberFormatException e){
            return BAD_REQUEST;
        }
		
		User user = userRep.getById(parseId);
		
		if (user == null) return NOT_FOUND;
		
		return Response.ok().entity(user).build();	
		
	}
	
	@POST
	@Path("user")//http:localhost:8080/webapi/users/user
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertUser (User user) {
		
		if (user==null) return BAD_REQUEST;
		
		userRep.insert(user);
		
		return Response.ok().entity(user).build();
		
	}
	
	@PUT 
	@Path("user") //http:localhost:8080/webapi/users/user
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser (User user) {
		
		if (user==null) return BAD_REQUEST;
		
		int id;		
		try {
			id = user.getId();
		} catch (Exception e) {
			return BAD_REQUEST;
		}		
				
		if (userRep.getById(id) == null) return NOT_FOUND;     
		
        userRep.update(user);
		
		User updatedUser = userRep.getById(id);
		
		return Response.ok().entity(updatedUser).build();
		
	}

}
