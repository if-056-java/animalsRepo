package app.resource;

import com.animals.app.domain.User;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class UserClient {

    //instance of Jersey Client
    private Client client;

    //URL to REST service
    private final String URL = "http://localhost:8080/webapi/";

    public UserClient() {

        //create new client
        client = ClientBuilder.newClient();
    }

    /**
     * This method imitate POST query on server to insert new record
     * @param user Instance of User class
     * @return Instance of User class
     */
    public User insert(User user) {
        WebTarget webTarget = client.target(URL);

        Response response = webTarget.path("users/user")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(user, MediaType.APPLICATION_JSON));

        if(response.getStatus() != Response.Status.OK.getStatusCode())
            throw new RuntimeException(response.getStatus() + " there was an error on server.");

        return response.readEntity(User.class);
    }

    /**
     * This method imitate PUT query on server to update record
     * @param user Instance of User class
     * @return Instance of User class
     */
    public User update(User user) {
        WebTarget webTarget = client.target(URL);

        Response response = webTarget.path("users/user")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(user, MediaType.APPLICATION_JSON));

        if(response.getStatus() != Response.Status.OK.getStatusCode())
            throw new RuntimeException(response.getStatus() + " there was an error on server.");

        return response.readEntity(User.class);
    }

    /**
     * This method imitate DELETE query on server to delete record
     * @param id Id instance of User class
     * @return List instances of User class
     */
    public String delete(String id) {
        WebTarget target = client.target(URL);

        Response response = target.path("users/" + id)
                .request(MediaType.APPLICATION_JSON)
                .delete();

        if(response.getStatus() != Response.Status.OK.getStatusCode())
            throw new RuntimeException(response.getStatus() + " there was an error on server.");

        return response.readEntity(String.class);
    }

    /**
     * This method imitate GET query on server to get record by id
     * @param id Id instance of User class
     * @return Instance of String class
     */
    public User get(String id) {

        //get target to testing
        WebTarget webTarget = client.target(URL);

        //generate response from server
        Response response = webTarget.path("users/" + id)
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);

        //check if response code wasn't return error code
        if(response.getStatus() != Response.Status.OK.getStatusCode())
            throw new RuntimeException(response.getStatus() + " there was an error on server.");

        //return generated instance
        return response.readEntity(User.class);
    }

    /**
     * This method imitate GET query on server to get all records
     * @return List instances of User class
     */
    public List<User> getAll() {
        WebTarget target = client.target(URL);

        Response response = target.path("users")
                .request(MediaType.APPLICATION_JSON)
                .get();

        if(response.getStatus() != Response.Status.OK.getStatusCode())
            throw new RuntimeException(response.getStatus() + " there was an error on server.");

        return response.readEntity(new GenericType<List<User>>() {});
    }
}
