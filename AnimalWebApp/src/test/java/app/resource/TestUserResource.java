/*
 * Create user with login - root and password - root and role admin or change credentials in
 * these values: LOGIN, PASSWORD for this test be passed
 */
package app.resource;

import app.JNDIConfigurationForTests;
import com.animals.app.domain.User;
import com.animals.app.domain.UserRole;
import com.animals.app.repository.Impl.UserRepositoryImpl;
import com.animals.app.repository.Impl.UserRoleRepositoryImpl;
import com.animals.app.repository.Impl.UserTypeRepositoryImpl;
import com.animals.app.repository.UserRepository;
import com.animals.app.service.DateSerializer;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runners.MethodSorters;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by root on 25.09.2015.
 */
@Category(IntegrationTest.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUserResource extends ResourceTestTemplate {
    private static Logger LOG = LogManager.getLogger(TestAdminResource.class);

    private static Client client;

    private static String accessToken;
    private static User user;

    private static final String REST_SERVICE_URL = BASE_URL + "users";

    //size of fields in data base, table: animals
    private static final int LENGTH_NAME = 35;
    private static final int LENGTH_SURNAME = 45;
    private static final int LENGTH_PASSWORD = 40;
    private static final int LENGTH_EMAIL = 30;
    private static final int LENGTH_SOCIAL_LOGIN = 30;
    private static final int LENGTH_PHONE = 15;

    @BeforeClass
    public static void runBeforeClass() {
        JNDIConfigurationForTests.configureJNDIForJUnit();

        client = ClientBuilder.newClient();

        UserRepository userRepository = new UserRepositoryImpl();
        user = new User();
        String password = RandomStringUtils.random(LENGTH_PASSWORD, true, true);

        user.setName(RandomStringUtils.random(LENGTH_NAME, true, true));
        user.setSurname(RandomStringUtils.random(LENGTH_SURNAME, true, true));
        user.setRegistrationDate(new Date(System.currentTimeMillis()));
        user.setPassword(getMd5(password));
        user.setSocialLogin(RandomStringUtils.random(LENGTH_SOCIAL_LOGIN, true, true));
        user.setEmail(RandomStringUtils.random(10, true, true) + '@' +
                RandomStringUtils.random(10, true, true) + '.' +
                RandomStringUtils.random(3, true, false));
        List<UserRole> userRoles = new ArrayList<>();
        userRoles.add(new UserRoleRepositoryImpl().getAll().get(0));
        user.setUserRole(userRoles);
        user.setUserType(new UserTypeRepositoryImpl().getAll().get(0));

        //userRepository.insert(user);

        assertNotNull(user.getId());

        user.setPassword(password);
    }

    @AfterClass
    public static void runAfterClass() {
        assertNotNull(user);
        assertNotNull(user.getId());

        UserRepository userRepository = new UserRepositoryImpl();

        userRepository.delete(user.getId());

        client = null;
    }

    @Test
    public void test00Initialization() {
        assertNotNull(user);
        assertNotNull(user.getSocialLogin());
        assertNotNull(user.getPassword());

        accessToken = login(client, user.getSocialLogin(), user.getPassword());

        assertNotNull(accessToken);
    }

    /*
     * Testing of getting user
     * Path: /users/user/userId
     * Method: get
     * Send: valid userId
     * Expect: instance of user
     */
    @Test
    public void test01GetUserById() {
        assertNotNull(accessToken);
        assertNotNull(user);
        assertNotNull(user.getId());

        User expected = client
                .target(REST_SERVICE_URL)
                .path("user/" + user.getId())
                .request()
                .header("AccessToken", accessToken)
                .get(User.class);

        assertNotNull(expected);
    }

    /*
     * Testing of getting user
     * Path: /users/user/userId
     * Method: get
     * Send: not valid userId (userId = -1)
     * Expect: response with status 400
     */
    @Test(expected = BadRequestException.class)
    public void test02GetUserById() {
        assertNotNull(accessToken);

        client.target(REST_SERVICE_URL)
                .path("user/-1")
                .request()
                .header("AccessToken", accessToken)
                .get(User.class);
    }

    /*
     * Testing of getting user
     * Path: /users/user/userId
     * Method: get
     * Send: not valid userId (userId = 0)
     * Expect: response with status 400
     */
    @Test(expected = BadRequestException.class)
    public void test03GetUserById() {
        assertNotNull(accessToken);

        client.target(REST_SERVICE_URL)
                .path("user/0")
                .request()
                .header("AccessToken", accessToken)
                .get(User.class);
    }

    /*
     * Testing of updating user
     * Path: /users/user/userId
     * Method: put
     * Send: valid user
     * Expect: instance of user
     */
    @Test
    public void test04UpdateUser() {
        assertNotNull(accessToken);
        assertNotNull(user);
        assertNotNull(user.getId());

        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(user);

        User expected = client
                .target(REST_SERVICE_URL)
                .path("user/" + user.getId())
                .request()
                .header("AccessToken", accessToken)
                .put(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), User.class);

        assertNotNull(expected);
    }

    /*
     * Testing of updating user
     * Try update another user
     * Path: /users/user/userId
     * Method: put
     * Send: valid user
     * Expect: response with status 400
     */
    @Test(expected = NotAuthorizedException.class)
    public void test05UpdateUser() {
        assertNotNull(accessToken);
        assertNotNull(user);
        assertNotNull(user.getId());

        UserRepository userRepository = new UserRepositoryImpl();
        User userDb = userRepository.getAll().get(0);//change

        User actual = SerializationUtils.clone(userDb);
        actual.setName("test_update");

        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test05UpdateUser - " + json);

        client.target(REST_SERVICE_URL)
                .path("user/" + user.getId())
                .request()
                .header("AccessToken", accessToken)
                .put(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), User.class);

        userRepository.update(userDb);
    }

    /*
     * Testing of updating user
     * Path: /users/user/userId
     * Method: put
     * Send: not valid user (length of name = LENGTH_NAME + 1, max = LENGTH_NAME)
     * Expect: response with status 400
     */
    @Test(expected = BadRequestException.class)
    public void test06UpdateUser() {
        assertNotNull(accessToken);
        assertNotNull(user);
        assertNotNull(user.getId());

        User actual = SerializationUtils.clone(user);
        actual.setName(RandomStringUtils.random(LENGTH_NAME + 1, true, true));

        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test06UpdateUser - " + json);

        client.target(REST_SERVICE_URL)
                .path("user/" + user.getId())
                .request()
                .header("AccessToken", accessToken)
                .put(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), User.class);
    }

    /*
     * Testing of updating user
     * Path: /users/user/userId
     * Method: put
     * Send: not valid user (length of surname = LENGTH_SURNAME + 1, max = LENGTH_SURNAME)
     * Expect: response with status 400
     */
    @Test(expected = BadRequestException.class)
    public void test07UpdateUser() {
        assertNotNull(accessToken);
        assertNotNull(user);
        assertNotNull(user.getId());

        User actual = SerializationUtils.clone(user);
        actual.setSurname(RandomStringUtils.random(LENGTH_SURNAME + 1, true, true));

        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test07UpdateUser - " + json);

        client.target(REST_SERVICE_URL)
                .path("user/" + user.getId())
                .request()
                .header("AccessToken", accessToken)
                .put(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), User.class);
    }

    /*
     * Testing of updating user
     * Path: /users/user/userId
     * Method: put
     * Send: not valid user (email - not valid)
     * Expect: response with status 400
     */
    @Test(expected = BadRequestException.class)
    public void test08UpdateUser() {
        assertNotNull(accessToken);
        assertNotNull(user);
        assertNotNull(user.getId());

        User actual = SerializationUtils.clone(user);
        actual.setEmail(RandomStringUtils.random(LENGTH_EMAIL, true, true));

        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test08UpdateUser - " + json);

        client.target(REST_SERVICE_URL)
                .path("user/" + user.getId())
                .request()
                .header("AccessToken", accessToken)
                .put(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), User.class);
    }

    /*
     * Testing of updating user
     * Path: /users/user/userId
     * Method: put
     * Send: not valid user (length of email = LENGTH_EMAIL + 10, max = LENGTH_EMAIL)
     * Expect: response with status 400
     */
    @Test(expected = BadRequestException.class)
    public void test09UpdateUser() {
        assertNotNull(accessToken);
        assertNotNull(user);
        assertNotNull(user.getId());

        User actual = SerializationUtils.clone(user);
        actual.setEmail(RandomStringUtils.random(LENGTH_EMAIL, true, true) + '@' +
                RandomStringUtils.random(5, true, true) + '.' +
                RandomStringUtils.random(3, true, false));

        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test09UpdateUser - " + json);

        client.target(REST_SERVICE_URL)
                .path("user/" + user.getId())
                .request()
                .header("AccessToken", accessToken)
                .put(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), User.class);
    }

    /*
     * Testing of updating user
     * Path: /users/user/userId
     * Method: put
     * Send: not valid user (length of password = LENGTH_PASSWORD + 1, max = LENGTH_PASSWORD)
     * Expect: response with status 400
     */
    @Test(expected = BadRequestException.class)
    public void test10UpdateUser() {
        assertNotNull(accessToken);
        assertNotNull(user);
        assertNotNull(user.getId());

        User actual = SerializationUtils.clone(user);
        actual.setPassword(RandomStringUtils.random(LENGTH_PASSWORD + 1, true, true));

        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test10UpdateUser - " + json);

        client.target(REST_SERVICE_URL)
                .path("user/" + user.getId())
                .request()
                .header("AccessToken", accessToken)
                .put(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), User.class);
    }

    /*
     * Testing of updating user
     * Path: /users/user/userId
     * Method: put
     * Send: not valid user (password = null)
     * Expect: response with status 400
     */
    @Test(expected = BadRequestException.class)
    public void test11UpdateUser() {
        assertNotNull(accessToken);
        assertNotNull(user);
        assertNotNull(user.getId());

        User actual = SerializationUtils.clone(user);
        actual.setPassword(null);

        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test10UpdateUser - " + json);

        client.target(REST_SERVICE_URL)
                .path("user/" + user.getId())
                .request()
                .header("AccessToken", accessToken)
                .put(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), User.class);
    }

    /*
     * Testing of updating user
     * Path: /users/user/userId
     * Method: put
     * Send: not valid user (phone - not valid)
     * Expect: response with status 400
     */
    @Test(expected = BadRequestException.class)
    public void test12UpdateUser() {
        assertNotNull(accessToken);
        assertNotNull(user);
        assertNotNull(user.getId());

        User actual = SerializationUtils.clone(user);
        actual.setPhone(RandomStringUtils.random(LENGTH_PHONE, false, true));

        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test12UpdateUser - " + json);

        client.target(REST_SERVICE_URL)
                .path("user/" + user.getId())
                .request()
                .header("AccessToken", accessToken)
                .put(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), User.class);
    }
}
