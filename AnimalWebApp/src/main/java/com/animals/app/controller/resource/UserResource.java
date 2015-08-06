package com.animals.app.controller.resource;

import com.animals.app.domain.Animal;
import com.animals.app.domain.Pagenator;
import com.animals.app.repository.Impl.AnimalRepositoryImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Rostyslav.Viner on 04.08.2015.
 */
@Path("user")
public class UserResource {
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

    @GET //http:localhost:8080/AnimalWebApp/webapi/home/animals/pagenator
    @Path("home/animals/pagenator")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAdminAnimalsListByPageCount() {

        Pagenator pages = animalRepository.getAdminAnimalsListByPageCount();

        if(pages == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        String str = "{\"rowsCount\" : " + String.valueOf(pages.getRowsCount()) + "}";

        return Response.status(Response.Status.OK).entity(str).build();
    }

    @GET //http:localhost:8080/AnimalWebApp/webapi/home/animals/{page}/{limit}
    @Path("home/animals/{page}/{limit}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAdminAnimalsListByPage(@PathParam("page") int page, @PathParam("limit") int limit) {
        if (page == 0 || limit == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        //cast list of animals to generic list
        List<Animal> animals = animalRepository.getAllForAdminAnimalsListByPage(new Pagenator(page, limit));
        GenericEntity<List<Animal>> genericAnimals =
                new GenericEntity<List<Animal>>(animals) {};

        if(genericAnimals == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(genericAnimals).build();
    }
}
