package com.animals.app.controller.resource;

import com.animals.app.domain.Animal;
import com.animals.app.domain.AnimalsFilter;
import com.animals.app.repository.Impl.AnimalBreedRepositoryImpl;
import com.animals.app.repository.Impl.AnimalRepositoryImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("user")
public class UserResource {
    //return response with 400 code
    private final Response BAD_REQUEST = Response.status(Response.Status.BAD_REQUEST).build();

    //return response with 404 code
    private final Response NOT_FOUND = Response.status(Response.Status.NOT_FOUND).build();

    AnimalRepositoryImpl animalRepository = new AnimalRepositoryImpl();

    @GET //http:localhost:8080/AnimalWebApp/webapi/home/animals/{animalId}
    @Path("home/animals/{animalId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getById(@PathParam("animalId") int animalId) {
        if (animalId == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        //cast list of animals to generic list
        Animal animal = animalRepository.getById(animalId);

        return Response.status(Response.Status.OK).entity(animal).build();
    }

    @DELETE //http:localhost:8080/AnimalWebApp/webapi/home/animals/{animalId}
    @Path("home/animals/{animalId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response delete(@PathParam("animalId") long animalId) {
        if (animalId == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        //cast list of animals to generic list
        animalRepository.delete(animalId);

        return Response.status(Response.Status.OK).build();
    }

    @POST //http:localhost:8080/AnimalWebApp/webapi/home/animals/pagenator
    @Path("home/animals/pagenator")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAdminAnimalsListByPageCount(AnimalsFilter animalsFilter) {

        long pages = animalRepository.getAdminAnimalsListByPageCount(animalsFilter);

        if(pages == 0)
            return Response.status(Response.Status.NOT_FOUND).build();

        String str = "{\"rowsCount\" : " + String.valueOf(pages) + "}";

        return Response.status(Response.Status.OK).entity(str).build();
    }

    @POST //http:localhost:8080/AnimalWebApp/webapi/home/animals/editor
    @Path("home/animals/editor")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAdminAnimalsList(Animal animal) {
        if(animal == null)
            return Response.status(Response.Status.BAD_REQUEST).build();
        //Check breed, if it new insert it into database
        if ((animal.getBreed().getId()) == null && (animal.getBreed().getBreedUa() != null)) {
            animal.getBreed().setType(animal.getType());
            new AnimalBreedRepositoryImpl().insert_ua(animal.getBreed());
        }
        //Update animal
        animalRepository.update(animal);

        return Response.status(Response.Status.OK).build();
    }

    @POST //http:localhost:8080/AnimalWebApp/webapi/home/animals/editor
    @Path("home/animals")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAnimalsList(AnimalsFilter animalsFilter) {

        if(animalsFilter == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if ((animalsFilter.getPage() == 0) || (animalsFilter.getLimit() == 0)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        List<Animal> animals = animalRepository.getAllForAdminAnimalsListByPage(animalsFilter);

        //cast list of animals to generic list
        GenericEntity<List<Animal>> genericAnimals = new GenericEntity<List<Animal>>(animals) {};

        if(genericAnimals == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(genericAnimals).build();
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
