package home.softrest;

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

import home.softrest.model.Owner;
import home.softrest.model.Pet;
import home.softrest.repository.PetRepository;
import home.softrest.repository.PetRepositoryStub;

@Path("pets") //http:localhost:8080/rest1/webapi/pets
public class PetResouce {

	private PetRepository petRepository = new PetRepositoryStub();
	
	@POST
	@Path("pet")//http:localhost:8080/rest1/webapi/pets/pet
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Pet createPet(Pet pet) {
		
		System.out.println("Creating pet: "+pet.getType() + ", " + pet.getSize() + ", Owned by: " + pet.getOwner().getName());
				
		petRepository.create(pet);
		
		return pet;
	}
	
	@POST
	@Path("pet") //http:localhost:8080/rest1/webapi/pets/pet
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Pet createPetParams(MultivaluedMap<String, String> formParams) {
		
		System.out.println(formParams.getFirst("type"));
		System.out.println(formParams.getFirst("size"));
		System.out.println(formParams.getFirst("ownerName"));
		
		Pet pet = new Pet();
		pet.setId(formParams.getFirst("id"));
		pet.setType(formParams.getFirst("type"));
		pet.setSize(Integer.parseInt(formParams.getFirst("size")));
		
		Owner owner = new Owner();
		owner.setName(formParams.getFirst("ownerName"));
		owner.setId(formParams.getFirst("ownerID"));
		owner.setAdress(formParams.getFirst("ownerAdress"));
		pet.setOwner(owner);
		
		System.out.println(pet.getOwner().getName()+ "pet to add");
		
		petRepository.create(pet);
		
		return pet;
	}
	
	@GET //http:localhost:8080/rest1/webapi/pets
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Pet> getAllPets() {
		return petRepository.findAllPets();
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("{petId}") //http:localhost:8080/rest1/webapi/pets/101
	public Pet getPet(@PathParam ("petId") String petId) {
		return petRepository.findPet(petId);
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("{petId}/owner") //http:localhost:8080/rest1/webapi/pets/101/owner
	public Owner getPetOwner(@PathParam ("petId") String petId) {
		
		Pet pet = petRepository.findPet(petId);
		Owner owner = pet.getOwner();
		return owner;
		
	}
	
	@PUT
	@Path("pet") //http:localhost:8080/rest1/webapi/pets/pet
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Pet update(MultivaluedMap<String, String> formParams) {
		
		System.out.println("ping");
		System.out.println(formParams.getFirst("petId"));
		System.out.println(formParams.getFirst("petType"));
		
		String petId = formParams.getFirst("petId");
		String petType = formParams.getFirst("petType");
		
		Pet pet = petRepository.update(petId, petType);
		
		return pet; 
		
	}
	
	@DELETE
	@Path("{petId}") //http:localhost:8080/rest1/webapi/pets/101
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String delete (@PathParam ("petId") String petId) {
		
		System.out.println("ping");
		System.out.println(petId);
		
		int k = petRepository.delete(petId);
		
		if (k==1) { 
			return  "Pet deleted";
		}			
		return  "Pet not found";
	}
	
	
	
}
