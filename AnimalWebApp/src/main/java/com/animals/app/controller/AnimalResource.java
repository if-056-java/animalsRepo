package com.animals.app.controller;

import com.animals.app.domain.Animal;
import com.animals.app.repository.AnimalRepositoryImpl;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
	
	@DELETE
	@Path("{animalId}") //http:localhost:8080/AnimalWebApp/webapi/animals/id
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Animal> delete (@PathParam ("animalId") String animalId) {
		
		int idi = (int) Integer.parseInt(animalId);
		
		anRep.delete(idi);
		
		List<Animal> animalsToReturn = anRep.getAll();
		
		return animalsToReturn;
		
	}
	
	@POST
	@Path("animal")//http:localhost:8080/AnimalWebApp/webapi/animals/animal
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Animal insertAnimal (Animal animal) {
		
		anRep.insert(animal);
		
		int i = anRep.getAll().size();
		
		return anRep.getAll().get(i-1);
	}
	
	// what exactly we need from update? how we specify pet to update? if with ID than need PUT if other way - we need POST. wrong repository implementation!
	
	@PUT //not ready
	@Path("animal") //http:localhost:8080/AnimalWebApp/webapi/animals/animal
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Animal update (Animal animal) {
		
		long id = animal.getId();
        animal.setId(id);
        System.out.println(id +"  -  " + animal.getId());
		
		anRep.update(animal);
		
		Animal updatedAnimal = anRep.getById(id);
		
		return updatedAnimal;
		
		
	}
	
}
