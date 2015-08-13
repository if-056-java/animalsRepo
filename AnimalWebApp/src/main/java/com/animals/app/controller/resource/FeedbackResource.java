package com.animals.app.controller.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.animals.app.domain.Animal;
import com.animals.app.domain.User;
import com.animals.app.repository.Impl.AnimalRepositoryImpl;
import com.animals.app.repository.Impl.UserRepositoryImpl;

/**
 * Created by aquaneo on 8/13/2015.
 */
@Path("contacts")
public class FeedbackResource {

        private final Response BAD_REQUEST = Response.status(Response.Status.BAD_REQUEST).build();

        private final Response NOT_FOUND = Response.status(Response.Status.NOT_FOUND).build();

        @POST
        @Path("mail")//http:localhost:8080/webapi/contacts/mail
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public Response sendFeedback (Feedback feedback) {

            if (feedback==null) return BAD_REQUEST;

            System.out.println("POST" + feedback);

            return ok("1");

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
