package com.animals.app.controller.client;

import com.animals.app.domain.Animal;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by oleg on 28.07.2015.
 */
public class AnimalClient {

    //instance of Jersey Client
    private Client client;

    //URL to REST service
    private final String URL = "http://localhost:8080/webapi/";

    public AnimalClient() {

        //create new client
        client = ClientBuilder.newClient();
    }

    /**
     * This method imitate POST query on server to insert new record
     * @param animal Instance of Animal class
     * @return Instance of Animal class
     */
    public Animal insert(Animal animal) {
        WebTarget webTarget = client.target(URL);

        Response response = webTarget.path("animals/animal")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(animal, MediaType.APPLICATION_JSON));

        if(response.getStatus() != Response.Status.OK.getStatusCode())
            throw new RuntimeException(response.getStatus() + " there was an error on server.");

        return response.readEntity(Animal.class);
    }

    /**
     * This method imitate PUT query on server to update record
     * @param animal Instance of Animal class
     * @return Instance of Animal class
     */
    public Animal update(Animal animal) {
        WebTarget webTarget = client.target(URL);

        Response response = webTarget.path("animals/animal")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(animal, MediaType.APPLICATION_JSON));

        if(response.getStatus() != Response.Status.OK.getStatusCode())
            throw new RuntimeException(response.getStatus() + " there was an error on server.");

        return response.readEntity(Animal.class);
    }

    /**
     * This method imitate DELETE query on server to delete record
     * @param id Id instance of Animal class
     * @return List instances of Animal class
     */
    public List<Animal> delete(String id) {
        WebTarget target = client.target(URL);

        Response response = target.path("animals/" + id)
                .request(MediaType.APPLICATION_JSON)
                .delete();

        if(response.getStatus() != Response.Status.OK.getStatusCode())
            throw new RuntimeException(response.getStatus() + " there was an error on server.");

        return response.readEntity(new GenericType<List<Animal>>() {});
    }

    /**
     * This method imitate GET query on server to get record by id
     * @param id Id instance of Animal class
     * @return Instance of Animal class
     */
    public Animal get(String id) {

        //get target to testing
        WebTarget webTarget = client.target(URL);

        //generate response from server
        Response response = webTarget.path("animals/" + id)
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);

        //check if response code wasn't return error code
        if(response.getStatus() != Response.Status.OK.getStatusCode())
            throw new RuntimeException(response.getStatus() + " there was an error on server.");

        //return generated instance
        return response.readEntity(Animal.class);
    }

    /**
     * This method imitate GET query on server to get all records
     * @return List instances of Animal class
     */
    public List<Animal> getAll() {
        WebTarget target = client.target(URL);

        Response response = target.path("animals")
                .request(MediaType.APPLICATION_JSON)
                .get();

        if(response.getStatus() != Response.Status.OK.getStatusCode())
            throw new RuntimeException(response.getStatus() + " there was an error on server.");

        return response.readEntity(new GenericType<List<Animal>>() {});
    }
}
