package com.animals.app;

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
import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;

import com.animals.app.domain.Owner;
import com.animals.app.domain.Pet;
import com.animals.app.repository.PetRepository;
import com.animals.app.repository.PetRepositoryStub;


@Path("pets") //http:localhost:8080/rest1/webapi/pets
public class PetResouce {
	
	static Logger logger = Logger.getLogger(PetResouce.class);
	static Logger loggerR = Logger.getLogger("R");

	private PetRepository petRepository = new PetRepositoryStub();
	
	@POST
	@Path("pet")//http:localhost:8080/rest1/webapi/pets/pet
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Pet createPet(Pet pet) {
				
		logger.info("Creating pet via JSON: "+pet.getType() + ", " + 
					pet.getSize() + ", Owned by: " + pet.getOwner().getName());
		loggerR.info("Creating pet via JSON: "+pet.getType() + ", " + 
				pet.getSize() + ", Owned by: " + pet.getOwner().getName());
		
		petRepository.create(pet);
		
		return pet;
	}
	
	@POST
	@Path("pet") //http:localhost:8080/rest1/webapi/pets/pet
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Pet createPetParams(MultivaluedMap<String, String> formParams) {
				
		Pet pet = new Pet();
		pet.setId(formParams.getFirst("id"));
		pet.setType(formParams.getFirst("type"));
		pet.setSize(Integer.parseInt(formParams.getFirst("size")));
		
		Owner owner = new Owner();
		owner.setName(formParams.getFirst("ownerName"));
		owner.setId(formParams.getFirst("ownerID"));
		owner.setAdress(formParams.getFirst("ownerAdress"));
		pet.setOwner(owner);
		
		logger.info("From REST method: Adding Pet via form: "+pet.getType() + ", " 
					+ pet.getSize() + ", Owned by: " + pet.getOwner().getName());
		loggerR.info("From REST method: Adding Pet via form: "+pet.getType() + ", " 
				+ pet.getSize() + ", Owned by: " + pet.getOwner().getName());	
		
		petRepository.create(pet);
		
		return pet;
	}
	
	@GET //http:localhost:8080/rest1/webapi/pets
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Pet> getAllPets() {
		logger.info("From REST method: Getting all pets");
		loggerR.info("From REST method: Getting all pets");
		return petRepository.findAllPets();
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("{petId}") //http:localhost:8080/rest1/webapi/pets/101
	public Pet getPet(@PathParam ("petId") String petId) {
		logger.info("From REST method: Getting pet by ID");
		loggerR.info("From REST method: Getting pet by ID");
		return petRepository.findPet(petId);
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("{petId}/owner") //http:localhost:8080/rest1/webapi/pets/101/owner
	public Owner getPetOwner(@PathParam ("petId") String petId) {
		
		logger.info("From REST method: Getting Owner by Pet ID");
		loggerR.info("From REST method: Getting Owner by Pet ID");
		
		Pet pet = petRepository.findPet(petId);
		Owner owner = pet.getOwner();
		return owner;
		
	}
	
	@PUT
	@Path("pet") //http:localhost:8080/rest1/webapi/pets/pet
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Pet update(MultivaluedMap<String, String> formParams) {
						
		String petId = formParams.getFirst("petId");
		String petType = formParams.getFirst("petType");
		
		logger.info("From REST method: Getting all pets. Pet Id = " + petId + 
				", new PetType - " + petType);
		loggerR.info("From REST method: Getting all pets. Pet Id = " + petId + 
				", new PetType - " + petType);

		Pet pet = petRepository.update(petId, petType);
		
		return pet; 
		
	}
	
	@DELETE
	@Path("{petId}") //http:localhost:8080/rest1/webapi/pets/101
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String delete (@PathParam ("petId") String petId) {
		
		logger.info("From REST method: Deleting Pet by ID");
		loggerR.info("From REST method: Deleting Pet by ID");
		
		int k = petRepository.delete(petId);
		
		if (k==1) {			
			return  "Pet deleted";
		}		
		return  "Pet not found";
	}	
	
}
