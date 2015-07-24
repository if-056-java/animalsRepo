package com.animals.app.controller;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.animals.app.domain.Animal;
import com.animals.app.repository.AnimalRepositoryImpl;

@Path("animals") //http:localhost:8080/AnimalsWebApp/webapi/animals
public class AnimalResource {
	
	private AnimalRepositoryImpl anRep = new AnimalRepositoryImpl();
	
	@GET //http:localhost:8080/AnimalsWebApp/webapi/animals
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Animal> getAllAnimals() {
		
		return anRep.getAll();
		
	}
	
}
