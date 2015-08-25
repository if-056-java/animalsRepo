package com.animals.app.controller.resource;

import com.animals.app.domain.Animal;
import com.animals.app.repository.AnimalRepository;
import com.animals.app.repository.Impl.AnimalRepositoryImpl;
import com.animals.app.service.Twitt;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import twitter4j.TwitterException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Properties;

/**
 * Created by aquaneo on 8/19/2015.
 */
@Path("contacts")
public class TwitterResource {
    private static Logger LOG = LogManager.getLogger(TwitterResource.class);

    private final Response BAD_REQUEST = Response.status(Response.Status.BAD_REQUEST).build();

    private final Response NOT_FOUND = Response.status(Response.Status.NOT_FOUND).build();

    private final Response OK = Response.status(Response.Status.OK).build();

    @POST //http:localhost:8080/webapi/animals/twitt/animalId
    @Path("animals/twitt/{animalId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response sendTwitt (@PathParam("animalId") long animalId) {

        Twitt twitt;

        String consumerKey, consumerSecret, accessToken, accessTokenSecret, messageBody ;

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
        twitt.setMessage("Нова тварина! " + animal.getType().toString());
        try {
            twitt.sendTwitt(consumerKey, consumerSecret, accessToken, accessTokenSecret);
        } catch (TwitterException e) {
            return BAD_REQUEST;
        } catch (IOException e) {
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
