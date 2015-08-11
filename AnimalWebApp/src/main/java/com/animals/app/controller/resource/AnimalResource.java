package com.animals.app.controller.resource;

import com.animals.app.domain.Animal;
import com.animals.app.domain.AnimalsFilter;
import com.animals.app.repository.Impl.AnimalRepositoryImpl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.animals.app.domain.AnimalBreed;
import com.animals.app.domain.AnimalType;
import com.animals.app.repository.Impl.AnimalBreedRepositoryImpl;
import com.animals.app.repository.Impl.AnimalTypeRepositoryImpl;

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

    AnimalRepositoryImpl animalRepository = new AnimalRepositoryImpl();

    @GET //http:localhost:8080/webapi/adoption/pagenator
    @Path("adoption/pagenator")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAmountListForAdopting() {

        long pages = animalRepository.getAmountListForAdopting();

        if(pages == 0)
            return Response.status(Response.Status.NOT_FOUND).build();

        String str = "{\"rowsCount\" : " + String.valueOf(pages) + "}";

        return Response.status(Response.Status.OK).entity(str).build();
    }

    @GET //http:localhost:8080/webapi/animals/adoption
    @Path("adoption/{page}/{limit}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllAnimalsForAdopting(@PathParam("page") int page, @PathParam("limit") int limit) {
        if (page == 0 || limit == 0) {
            return BAD_REQUEST;
        }

        //cast list of animals to generic list
        List<Animal> animals = animalRepository.getAllForAdopting(new AnimalsFilter(page, limit));
        GenericEntity<List<Animal>> genericAnimals =
                new GenericEntity<List<Animal>>(animals) {};

        if(genericAnimals == null)
            return NOT_FOUND;

        return ok(genericAnimals);
    }

    @GET //http:localhost:8080/webapi/animals/adoption/id
    @Path("adoption")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getShortInfo(@PathParam ("animalId") String id) {

        if (id == null)
            return BAD_REQUEST;

        int idAnimal = (int) Integer.parseInt(id);

        Animal animalShortInfo = animalRepository.getShortInfoById(idAnimal);

        if (animalShortInfo == null)
            return NOT_FOUND;

        return ok(animalShortInfo);
    }

    @GET //http:localhost:8080/webapi/animals/animal_types
    @Path("animal_types")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAnimalTypes() {
        List<AnimalType> animalTypes = new AnimalTypeRepositoryImpl().getAll();

        GenericEntity<List<AnimalType>> genericAnimalTypes =
                new GenericEntity<List<AnimalType>>(animalTypes) {};

        if(genericAnimalTypes == null) {
            return NOT_FOUND;
        }

        return ok(genericAnimalTypes);
    }

    @GET //http:localhost:8080/webapi/animals/animal_breeds
    @Path("animal_breeds/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAnimalBreadsByAnimalTypeId(@PathParam("id") int animalTypeId) {
        if (animalTypeId == 0) {
            return BAD_REQUEST;
        }

        List<AnimalBreed> animalBreeds = new AnimalBreedRepositoryImpl().getByTypeId(animalTypeId);

        GenericEntity<List<AnimalBreed>> genericAnimalBreeds =
                new GenericEntity<List<AnimalBreed>>(animalBreeds) {};

        if(genericAnimalBreeds == null) {
            return NOT_FOUND;
        }

        return ok(genericAnimalBreeds);
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
