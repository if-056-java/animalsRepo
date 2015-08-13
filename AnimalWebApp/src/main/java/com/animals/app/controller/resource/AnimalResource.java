package com.animals.app.controller.resource;

import com.animals.app.domain.Animal;
import com.animals.app.domain.AnimalsFilter;
import com.animals.app.repository.Impl.AnimalRepositoryImpl;

import javax.ws.rs.*;

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

    @POST //http:localhost:8080/webapi/adoption/pagenator
    @Path("adoption/pagenator")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAmountListForAdopting(AnimalsFilter animalsFilter) {

        AnimalRepositoryImpl animalRepository = new AnimalRepositoryImpl();
        long pages = animalRepository.getAmountListForAdopting(animalsFilter);

        if(pages == 0)
            return Response.status(Response.Status.NOT_FOUND).build();

        String str = "{\"rowsCount\" : " + String.valueOf(pages) + "}";

        return Response.status(Response.Status.OK).entity(str).build();
    }

    @POST //http:localhost:8080/webapi/animals/adoption
    @Path("adoption"/*/{page}/{limit}*/)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllAnimalsForAdopting(/*@PathParam("page") int page, @PathParam("limit") int limit*/ AnimalsFilter animalsFilter) {
/*
        if (page == 0 || limit == 0) {
            return BAD_REQUEST;
        }
*/

        if(animalsFilter == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if ((animalsFilter.getPage() == 0) || (animalsFilter.getLimit() == 0)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        AnimalRepositoryImpl animalRepository = new AnimalRepositoryImpl();
        //cast list of animals to generic list
        List<Animal> animals = animalRepository.getAllForAdopting(animalsFilter);
        GenericEntity<List<Animal>> genericAnimals =
                new GenericEntity<List<Animal>>(animals) {};

        if(genericAnimals == null)
            return NOT_FOUND;

        return ok(genericAnimals);
    }


    @GET //http:localhost:8080/webapi/animals/{adoption|found|lost}/id
    @Path("{parameter: adoption|found|lost}")
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
