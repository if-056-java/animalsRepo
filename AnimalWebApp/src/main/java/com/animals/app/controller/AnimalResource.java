package com.animals.app.controller;

import com.animals.app.domain.Animal;
import com.animals.app.repository.AnimalRepositoryImpl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("animals") 
public class AnimalResource {
	
	private AnimalRepositoryImpl anRep = new AnimalRepositoryImpl();
	
	@GET //http:localhost:8080/AnimalWebApp/webapi/animals
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Animal> getAllAnimals() {
		
		return anRep.getAll();
		
	}

	@GET //http:localhost:8080/AnimalWebApp/webapi/animals/id
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("{animalId}")
	public Animal getAnimal(@PathParam ("animalId") String id) {
		
		int idi = (int) Integer.parseInt(id);
		
		return anRep.getById(idi);
		
	}
	
}
