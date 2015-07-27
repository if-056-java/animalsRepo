package com.animals.app.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.animals.app.domain.User;
import com.animals.app.repository.UserRepositoryImpl;

@Path("users") //http:localhost:8080/AnimalsWebApp/webapi/users
public class UserResource {
	
	UserRepositoryImpl userRep = new UserRepositoryImpl();
	
	@GET //http:localhost:8080/AnimalWebApp/webapi/users
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})	
	public List<User> getAllUsers() {
		
		return userRep.getAll();
		
	}
	
	@GET //http:localhost:8080/AnimalWebApp/webapi/users/id
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("{userId}") //http:localhost:8080/AnimalsWebApp/webapi/users/id
	public User getUserById(@PathParam ("userId") String id) {
		
		int idi = (int) Integer.parseInt(id);
		
		return userRep.getById(idi);
		
	}
	
	@DELETE
	@Path("{userId}") //http:localhost:8080/AnimalWebApp/webapi/users/id
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<User> delete (@PathParam ("userId") String userId) {
		
		int id = (int) Integer.parseInt(userId);
		
		userRep.delete(id);
		
		List<User> usersToReturn = userRep.getAll();
		
		return usersToReturn;
		
	}
	
	
	@POST
	@Path("user")//http:localhost:8080/AnimalWebApp/webapi/users/user
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public User insertUser (User user) {
		
		userRep.insert(user);
		
		int i = userRep.getAll().size();
		
		return userRep.getAll().get(i-1);
	}
		
	
	@PUT //not ready
	@Path("user") //http:localhost:8080/AnimalWebApp/webapi/users/user
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public User updateUser (User user) {
		
		int id = user.getId();
        user.setId(id);
        System.out.println(id +"  -  " + user.getId());
		
        userRep.update(user);
		
		User updatedUser = userRep.getById(id);
		
		return updatedUser;
		
	}
	
	

}
