package com.animals.app.controller.resource;

import com.animals.app.domain.Animal;
import com.animals.app.domain.Pagenator;
import com.animals.app.repository.Impl.AnimalRepositoryImpl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Rostyslav.Viner on 04.08.2015.
 */
@Path("adm_animals_list")
public class AnimalListAdminResource {
    AnimalRepositoryImpl animalRepository = new AnimalRepositoryImpl();

    @GET //http:localhost:8080/AnimalWebApp/webapi/adm_animals_list/pagenator
    @Path("pagenator")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAdminAnimalsListByPageCount() {

        //cast list of animals to generic list
        Pagenator pages = animalRepository.getAdminAnimalsListByPageCount();

        String str = "{\"rowsCount\" : " + String.valueOf(pages.getRowsCount()) + "}";

        if(pages == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(str).build();
    }

    @GET //http:localhost:8080/AnimalWebApp/webapi/adm_animals_list/page/limit
    @Path("{page}/{limit}")
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
