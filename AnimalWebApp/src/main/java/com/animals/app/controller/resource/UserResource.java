package com.animals.app.controller.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.animals.app.domain.Animal;
import com.animals.app.domain.User;
import com.animals.app.repository.Impl.AnimalRepositoryImpl;
import com.animals.app.repository.Impl.UserRepositoryImpl;

@Path("users")
public class UserResource {
	
	private final Response BAD_REQUEST = Response.status(Response.Status.BAD_REQUEST).build();
	
	private final Response NOT_FOUND = Response.status(Response.Status.NOT_FOUND).build();
	
	private final Response SERVER_ERROR = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	
	private UserRepositoryImpl userRep = new UserRepositoryImpl();
	AnimalRepositoryImpl animalRepository = new AnimalRepositoryImpl();
	
	@GET //http:localhost:8080/webapi/users/user/{userId}
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
		
		try {
			User user = userRep.getById(parseId);
			
			if (user == null) return NOT_FOUND;
			
			return Response.ok().entity(user).build();
			
		} catch (Exception e) {
			return SERVER_ERROR;
		}	
		
	}
	
	@GET //http:localhost:8080/webapi/users/user/{userId}/animals
	@Produces(MediaType.APPLICATION_JSON)
	@Path("user/{userId}/animals") 
	public Response getAnimalsByUserId(@PathParam ("userId") String id) {
				
		int parseId = 0;
		
		try {
            if (id == null)
                return BAD_REQUEST;
            parseId = Integer.parseInt(id);
        } catch (NumberFormatException e){
            return BAD_REQUEST;
        }
		
		try {
			List<Animal> animals = animalRepository.getAnimalByUserId(parseId);
			
			GenericEntity<List<Animal>> genericAnimals =
			        new GenericEntity<List<Animal>>(animals) {};

			if(genericAnimals == null)
			    return Response.status(Response.Status.NOT_FOUND).build();

			return Response.status(Response.Status.OK).entity(genericAnimals).build();
			
		} catch (Exception e) {
			return SERVER_ERROR;
		}
		
	}
	
	@POST
	@Path("user")//http:localhost:8080/webapi/users/user/
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertUser (User user) {		//registration
		
		if (user==null) return BAD_REQUEST;
		
		try {
			userRep.insert(user);			
		} catch (Exception e) {
			return SERVER_ERROR;
		}
		
		return Response.ok().entity(user).build();
		
	}
	
	
	@PUT 
	@Path("user/{userId}") //http:localhost:8080/webapi/users/user/{userId}
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
		
       	try {
			userRep.update(user);
			
			User updatedUser = userRep.getById(id);
			
			return Response.ok().entity(updatedUser).build();
			
		} catch (Exception e) {
			return SERVER_ERROR;
		}		
	}

}
