package com.animals.app.controller.resource;

import com.animals.app.domain.User;
import com.animals.app.repository.Impl.UserRepositoryImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;


@Path("users") //http:localhost:8080/AnimalWebApp/webapi/users
public class UserResource {
	
	//return response with 400 code
    private final Response BAD_REQUEST = Response.status(Response.Status.BAD_REQUEST).build();

    //return response with 404 code
    private final Response NOT_FOUND = Response.status(Response.Status.NOT_FOUND).build();
	
	private UserRepositoryImpl userRep = new UserRepositoryImpl();
	
	@GET //http:localhost:8080/AnimalWebApp/webapi/users
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})	
	public Response getAllUsers() {
		
		List<User> users=userRep.getAll();
		
		GenericEntity<List<User>> genericUsers =new GenericEntity<List<User>>(users) {};

		//if(genericUsers == null) return NOT_FOUND;  //We really need it???

		return ok(genericUsers);
		
	}
	
	
	
	@GET //http:localhost:8080/AnimalWebApp/webapi/users/id
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
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
		
		return ok(user);
		
	}
	
	@DELETE
	@Path("{userId}") //http:localhost:8080/AnimalWebApp/webapi/users/id
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response delete (@PathParam ("userId") String userId) {
		
		int parseId = 0;

        try {
            if (userId == null)
                return BAD_REQUEST;
            parseId = Integer.parseInt(userId);
        } catch (NumberFormatException e){
            return BAD_REQUEST;
        }
        
        if (userRep.getById(parseId) == null) return NOT_FOUND;
		
		userRep.delete(parseId);		    

        return ok('{' +"\"Response\":\"User deleted!\"" + '}');
		
	}
	
	
	@POST
	@Path("user")//http:localhost:8080/AnimalWebApp/webapi/users/user
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response insertUser (User user) {
		
		if (user==null) return BAD_REQUEST;
		
		userRep.insert(user);
		
		return ok(user);
		
	}
		
	
	@PUT 
	@Path("user") //http:localhost:8080/AnimalWebApp/webapi/users/user
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response updateUser (User user) {
		
		if (user==null) return BAD_REQUEST;
		
		int id = user.getId();		
				
		if (userRep.getById(id) == null) return NOT_FOUND;     
		
        userRep.update(user);
		
		User updatedUser = userRep.getById(id);
		
		return ok(updatedUser);
		
	}
	
	/**
     * Return response with code 200(OK) and build returned entity
     * @param entity Returned json instance from client
     * @return HTTP code K
     */
    private Response ok(Object entity) {
        return Response.ok().entity(entity).build();
    }
    
}
