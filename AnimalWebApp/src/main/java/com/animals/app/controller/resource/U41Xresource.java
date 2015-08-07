package com.animals.app.controller.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
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
	@Path("{userId}") 
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
	
	@GET //http:localhost:8080/webapi/users
	@Produces(MediaType.APPLICATION_JSON)	
	public Response getAllUsers() {
		
		List<User> users=userRep.getAll();
		
		GenericEntity<List<User>> genericUsers =new GenericEntity<List<User>>(users) {};

		//if(genericUsers == null) return NOT_FOUND;  //We really need it???

		return Response.ok().entity(genericUsers).build(); 
		
	}
	
	

}
