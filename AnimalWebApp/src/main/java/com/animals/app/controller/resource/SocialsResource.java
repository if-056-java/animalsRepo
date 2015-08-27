package com.animals.app.controller.resource;

import com.animals.app.domain.Animal;
import com.animals.app.repository.AnimalRepository;
import com.animals.app.repository.Impl.AnimalRepositoryImpl;
import com.animals.app.service.Twitt;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import twitter4j.TwitterException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by aquaneo on 8/19/2015.
 */
@Path("socials")
public class SocialsResource {
    private static Logger LOG = LogManager.getLogger(SocialsResource.class);

    private final Response BAD_REQUEST = Response.status(Response.Status.BAD_REQUEST).build();

    private final Response NOT_FOUND = Response.status(Response.Status.NOT_FOUND).build();

    private final Response OK = Response.status(Response.Status.OK).build();

    @POST //http:localhost:8080/webapi/socials/twitter/animalId
    @Path("twitter/{animalId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response sendTwitt (@PathParam("animalId") long animalId) {

        Twitt twitt;
        String consumerKey, consumerSecret, accessToken, accessTokenSecret, messageBody ;

        System.out.println("animalId -" + animalId);
        if (animalId == 0) {
            return BAD_REQUEST;
        }
        consumerKey = twitterConfig.getProperty("twitter.consumerKey");
        consumerSecret = twitterConfig.getProperty("twitter.consumerSecret");
        accessToken = twitterConfig.getProperty("twitter.accessToken");
        accessTokenSecret = twitterConfig.getProperty("twitter.accessTokenSecret");
        messageBody = twitterConfig.getProperty("twitter.messageBody");

        //get animal by id from database
        AnimalRepository animalRepository = new AnimalRepositoryImpl();
        Animal animal = animalRepository.getById(animalId);

        if (animal == null) {
            return NOT_FOUND;
        }

        twitt = new Twitt();
        twitt.setMessage("Нова тварина! Деталі: - http://tym.dp.ua/#/ua/animal/adoption/" + animalId);

        //attach any media, if you want to
        if (!animal.getImage().equals(null)) twitt.setMedia(animal.getImage().toString());

        try {
            twitt.sendTwitt(consumerKey, consumerSecret, accessToken, accessTokenSecret);
        } catch (TwitterException e) {
            System.out.println(e.toString());
            return BAD_REQUEST;
        } catch (IOException e) {
            System.out.println(e.toString());
            return BAD_REQUEST;
        }

        return OK;

    }




    /**
     * Return response with code 200(OK) and build returned entity
     * @param entity Returned json instance from client
     * @return HTTP code K
     */
    private Response ok(Object entity) {
        return Response.ok().entity(entity).build();
    }

    private static Properties twitterConfig = new Properties();
    {
        fetchConfig();
    }

    /**
     * Open a specific text file containing reCAPTCHA secret key
     * and verification URL.
     */
    private void fetchConfig() {
        //This file contains the reCAPTCHA config properties mentioned above.
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("project.properties").getFile());

        try (InputStream input = new FileInputStream(file)) {
            twitterConfig.load(input);
        }
        catch (IOException ex){
            System.err.println("Cannot open and load mail server properties file. Put it on...");
        }
    }
}
