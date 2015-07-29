package com.animals.app.controller.resource;

import com.animals.app.domain.Animal;
import com.animals.app.repository.Impl.AnimalRepositoryImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("animals")
public class AnimalResource {

    //return response with 400 code
    private final Response BAD_REQUEST = Response.status(Response.Status.BAD_REQUEST).build();

    //return response with 404 code
    private final Response NOT_FOUND = Response.status(Response.Status.NOT_FOUND).build();

    private AnimalRepositoryImpl anRep = new AnimalRepositoryImpl();

    @POST
    @Path("animal")//http:localhost:8080/AnimalWebApp/webapi/animals/animal
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response insertAnimal (Animal animal) {
        if(animal == null)
            return BAD_REQUEST;

        anRep.insert(animal);

        return ok(animal);
    }

    @PUT
    @Path("animal") //http:localhost:8080/AnimalWebApp/webapi/animals/animal
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response update (Animal animal) {
        if(animal == null)
            return BAD_REQUEST;

        anRep.update(animal);

        return ok(animal);
    }

    @DELETE
    @Path("{animalId}") //http:localhost:8080/AnimalWebApp/webapi/animals/id
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response delete (@PathParam ("animalId") String id) {

        int parseId = 0;

        try {
            if (id == null)
                return BAD_REQUEST;
            parseId = Integer.parseInt(id);
        } catch (NumberFormatException e){
            return BAD_REQUEST;
        }

        anRep.delete(parseId);

        //cast list of animals to generic list
        List<Animal> animalsToReturn = anRep.getAll();
        GenericEntity<List<Animal>> genericAnimalsToReturn =
                new GenericEntity<List<Animal>>(animalsToReturn) {};

        if(genericAnimalsToReturn == null)
            return NOT_FOUND;

        return ok(genericAnimalsToReturn);
    }

    @GET //http:localhost:8080/webapi/animals/id
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("{animalId}")
    public Response getAnimal(@PathParam ("animalId") String id) {
        int parseId = 0;

        try {
            if (id == null)
                return BAD_REQUEST;
            parseId = Integer.parseInt(id);
        } catch (NumberFormatException e){
            return BAD_REQUEST;
        }

        Animal animal = anRep.getById(parseId);

        if(animal == null)
            return NOT_FOUND;

        return ok(animal);
    }

    @GET //http:localhost:8080/webapi/animals
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getAllAnimals() {

        //cast list of animals to generic list
        List<Animal> animals = anRep.getAll();
        GenericEntity<List<Animal>> genericAnimals =
                new GenericEntity<List<Animal>>(animals) {};

        if(genericAnimals == null)
            return NOT_FOUND;

        return ok(genericAnimals);
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
