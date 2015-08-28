package com.animals.app.controller.resource;

import com.animals.app.domain.AnimalMedicalHistory;
import com.animals.app.domain.AnimalsFilter;
import com.animals.app.domain.User;
import com.animals.app.repository.AnimalMedicalHistoryRepository;
import com.animals.app.repository.Impl.AnimalMedicalHistoryRepositoryImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Rostyslav.Viner on 25.08.2015.
 */

@Path("doctor")
public class DoctorResource {
    private static Logger LOG = LogManager.getLogger(DoctorResource.class);

    //return response with 400 code
    private final Response BAD_REQUEST = Response.status(Response.Status.BAD_REQUEST).build();

    //return response with 404 code
    private final Response NOT_FOUND = Response.status(Response.Status.NOT_FOUND).build();

    //return response with 500 code
    private final Response SERVER_ERROR = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

    /**
     * @param animalId instance used for lookup.
     * @return count of rows for pagination.
     */
    @GET //http:localhost:8080/webapi/animals/paginator
    @RolesAllowed("лікар")
    @Path("medical_history/paginator/{animalId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAnimalsPaginator(@PathParam("animalId") long animalId) {
        if(animalId == 0) {
            return BAD_REQUEST;
        }

        //get count of row according to filter
        AnimalMedicalHistoryRepository animalMedicalHistory = new AnimalMedicalHistoryRepositoryImpl();
        long pages = animalMedicalHistory.getByAnimalIdCount(animalId);

        String json = "{\"rowsCount\" : " + String.valueOf(pages) + "}";

        return ok(json);
    }

    /**
     * @param animalId instance used for lookup.
     * @return list of medical history.
     */
    @POST //http:localhost:8080/webapi/doctor/medical_history/{animalId}
    @RolesAllowed("лікар")
    @Path("medical_history/{animalId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getMedicalHistoryByAnimalId(@PathParam("animalId") long animalId, AnimalsFilter animalsFilter) {
        if((animalId == 0) || (animalsFilter == null)) {
            return BAD_REQUEST;
        }

        if ((animalsFilter.getPage() == 0) || (animalsFilter.getLimit() == 0)) {
            return BAD_REQUEST;
        }

        //get list of animals medical history from data base
        AnimalMedicalHistoryRepository animalMedicalHistory = new AnimalMedicalHistoryRepositoryImpl();
        List<AnimalMedicalHistory> medicalHistory = animalMedicalHistory.getByAnimalId(animalId, animalsFilter.getOffset(), animalsFilter.getLimit());

        //cast list of animals medical history to generic list
        GenericEntity<List<AnimalMedicalHistory>> genericAnimals =
                new GenericEntity<List<AnimalMedicalHistory>>(medicalHistory) {};

        if(genericAnimals == null) {
            return NOT_FOUND;
        }

        return ok(genericAnimals);
    }

    /**
     * @param itemId instance used for lookup.
     * @return list of medical history.
     */
    @DELETE //http:localhost:8080/webapi/doctor/medical_history/item/{itemId}
    @RolesAllowed("лікар")
    @Path("medical_history/item/{itemId}")
    public Response deleteMedicalHistoryItemById(@PathParam("itemId") long itemId) {
        if(itemId == 0) {
            return BAD_REQUEST;
        }

        AnimalMedicalHistoryRepository animalMedicalHistory = new AnimalMedicalHistoryRepositoryImpl();
        animalMedicalHistory.deleteById(itemId);

        return ok();
    }

    @POST //http:localhost:8080/webapi/doctor/medical_history/item
    @RolesAllowed("лікар")
    @Path("medical_history/item")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMedicalHistoryItemById(@Context HttpServletRequest req, AnimalMedicalHistory animalMedicalHistory) {
        if(animalMedicalHistory == null) {
            return BAD_REQUEST;
        }

        if((animalMedicalHistory.getAnimalId() == null) || (animalMedicalHistory.getDate() == null)
                || (animalMedicalHistory.getStatus() == null)) {
            return BAD_REQUEST;
        }

        if (animalMedicalHistory.getStatus().getId() == null) {
            return BAD_REQUEST;
        }

        HttpSession session = req.getSession(true);

        if (session.getAttribute("userId") == null) {
            return BAD_REQUEST;
        }

        User user = new User();
        try {
            user.setId(Integer.parseInt((String) session.getAttribute("userId")));
        } catch (ClassCastException e) {
            return BAD_REQUEST;
        }

        animalMedicalHistory.setUser(user);

        AnimalMedicalHistoryRepository animalMedicalHistoryRepository = new AnimalMedicalHistoryRepositoryImpl();
        animalMedicalHistoryRepository.insert(animalMedicalHistory);

        return ok();
    }

    /**
     * Return response with code 200(OK) and build returned entity
     * @return HTTP code K
     */
    private Response ok() {
        return Response.ok().build();
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
