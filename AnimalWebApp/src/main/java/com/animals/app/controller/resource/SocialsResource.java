package com.animals.app.controller.resource;

import com.animals.app.domain.Animal;
import com.animals.app.repository.AnimalRepository;
import com.animals.app.repository.Impl.AnimalRepositoryImpl;
import com.animals.app.service.Facebook;
import com.animals.app.service.FetchConfigProperties;
import com.animals.app.service.Twitt;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Properties;

@Path("socials")
public class SocialsResource {
    private static Logger LOG = LogManager.getLogger(SocialsResource.class);

    private final Properties socialsConfig = new Properties();

    private final Response BAD_REQUEST = Response.status(Response.Status.BAD_REQUEST).build();

    private final Response NOT_FOUND = Response.status(Response.Status.NOT_FOUND).build();

    private final Response OK = Response.status(Response.Status.OK).build();

    @Context
    private HttpServletRequest httpServlet;

    @POST //http:localhost:8080/webapi/socials/twitter/animalId
    @Path("/twitter/{animalId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendTwitt (@PathParam("animalId") long animalId) {

        Twitt twitt;
        String consumerKey, consumerSecret, accessToken, accessTokenSecret, messageBody ;

        if (animalId == 0) {
            LOG.warn("Wrong animal ID!");
            return BAD_REQUEST;
        }

        getProperties();

        consumerKey = socialsConfig.getProperty("twitter.consumerKey");
        consumerSecret = socialsConfig.getProperty("twitter.consumerSecret");
        accessToken = socialsConfig.getProperty("twitter.accessToken");
        accessTokenSecret = socialsConfig.getProperty("twitter.accessTokenSecret");
        messageBody = socialsConfig.getProperty("twitter.messageBody");

        //get animal by id from database
        AnimalRepository animalRepository = new AnimalRepositoryImpl();
        Animal animal = animalRepository.getById(animalId);

        if (animal == null) {
            LOG.warn("Wrong animal ID!");
            return NOT_FOUND;
        }
        if (animal.getDateOfTwitter() == null) {
            twitt = new Twitt();
            twitt.setMessage("Нова тварина! Деталі: - http://tym.dp.ua/#/ua/animal/adoption/" + animalId);

            //attach any media, if you want to
            if (!(animal.getImage() == null)) {

                String restPath = httpServlet.getServletContext().getRealPath("/"); //path to rest root folder
                twitt.setMedia(restPath + animal.getImage());
            }


            if (twitt.sendTwitt(consumerKey, consumerSecret, accessToken, accessTokenSecret)) {
                animal.setDateOfTwitter(getCurrentDate());
                animalRepository.twitterUpdate(animal);
                return OK;
            } else {
                LOG.error("Twitter send error");
                return BAD_REQUEST;
            }
        }
        else {
            LOG.error("Error in DB - see dateOfTwitter column!");
            return BAD_REQUEST;
        }
    }

    @POST //http:localhost:8080/webapi/socials/facebook/animalId
    @Path("/facebook/{animalId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendFacebook (@PathParam("animalId") long animalId) {
        Facebook facebook;
        String accessToken, wallId ;

        if (animalId == 0) {
            LOG.warn("Wrong animal ID!");
            return BAD_REQUEST;
        }

        //get animal by id from database
        AnimalRepository animalRepository = new AnimalRepositoryImpl();
        Animal animal = animalRepository.getById(animalId);

        if (animal == null) {
            LOG.warn("Wrong animal ID!");
            return NOT_FOUND;
        }

        if (animal.getDateOfFacebook() == null) {

            getProperties();

            accessToken = socialsConfig.getProperty("facebook.accessToken");
            wallId = socialsConfig.getProperty("facebook.wallId");

            facebook = new Facebook();

            if (facebook.sendFacebook(accessToken, wallId, "Нова тварина! Деталі: - http://tym.dp.ua/#/ua/animal/adoption/" + animalId)) {
                animal.setDateOfFacebook(getCurrentDate());
                animalRepository.facebookUpdate(animal);
                return OK;
            } else {
                LOG.warn("Facebook error!");
                return BAD_REQUEST;
            }
        }
        else {
            LOG.error("Error in DB - see dateOfFacebook column!");
            return BAD_REQUEST;
        }

    }

    private static java.sql.Date getCurrentDate() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());
    }


    /**
     * Return response with code 200(OK) and build returned entity
     * @param entity Returned json instance from client
     * @return HTTP code K
     */
    private Response ok(Object entity) {
        return Response.ok().entity(entity).build();
    }

    public void getProperties() {
        new FetchConfigProperties().fetchConfig(socialsConfig);
    }

}
