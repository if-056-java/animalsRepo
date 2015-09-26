/*
 * Create user with login - root and password - root and role admin or change credentials in
 * these values: LOGIN, PASSWORD for this test be passed
 */
package app.resource;

import app.JNDIConfigurationForTests;
import com.animals.app.domain.*;
import com.animals.app.repository.Impl.*;
import com.animals.app.repository.UserRepository;
import com.animals.app.service.DateSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
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
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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
    private static Animal animal;

    private static final String REST_SERVICE_URL = BASE_URL + "users";

    //size of fields in data base, table: animals
    private static final int LENGTH_NAME = 35;
    private static final int LENGTH_SURNAME = 45;
    private static final int LENGTH_PASSWORD = 40;
    private static final int LENGTH_EMAIL = 30;
    private static final int LENGTH_SOCIAL_LOGIN = 30;
    private static final int LENGTH_PHONE = 15;
    private static final int LENGTH_ADDRESS = 120;
    private static final int LENGTH_ORGANIZATION_NAME = 70;
    private static final int LENGTH_ORGANIZATION_INFO = 100;
    private static final int LENGTH_ANIMAL_COLOR = 20;
    private static final int LENGTH_ANIMAL_ADDRESS = 120;

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

        userRepository.insert(user);

        assertNotNull(user.getId());
        user.setPassword(password);

        animal = new Animal();
        animal.setSex(Animal.SexType.FEMALE);
        animal.setType(new AnimalTypeRepositoryImpl().getAll().get(0));
        animal.setSize(Animal.SizeType.LARGE);
        animal.setCites(Animal.CitesType.NONE);
        animal.setBreed(new AnimalBreedRepositoryImpl().getByTypeId(animal.getType().getId()).get(0));
        animal.setDateOfRegister(new Date(System.currentTimeMillis()));
        animal.setColor(RandomStringUtils.random(LENGTH_ANIMAL_COLOR, true, true));
        animal.setAddress(RandomStringUtils.random(LENGTH_ANIMAL_ADDRESS, true, true));
        animal.setService(new AnimalServiceRepositoryImpl().getAll().get(0));
        animal.setUser(user);

        new AnimalRepositoryImpl().insert(animal);

        assertNotNull(animal.getId());
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
        assertNotNull(user.getName());
        assertNotNull(user.getSurname());
        assertNotNull(user.getRegistrationDate());
        assertNotNull(user.getPassword());
        assertNotNull(user.getSocialLogin());
        assertNotNull(user.getEmail());
        assertNotNull(user.getUserRole());
        assertNotNull(user.getUserType());

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
        actual.setName(RandomStringUtils.random(LENGTH_NAME, true, true));

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
        assertNotNull(user.getName());
        assertNotNull(user.getSurname());
        assertNotNull(user.getRegistrationDate());
        assertNotNull(user.getPassword());
        assertNotNull(user.getSocialLogin());
        assertNotNull(user.getEmail());
        assertNotNull(user.getUserRole());
        assertNotNull(user.getUserType());

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
        assertNotNull(user.getName());
        assertNotNull(user.getSurname());
        assertNotNull(user.getRegistrationDate());
        assertNotNull(user.getPassword());
        assertNotNull(user.getSocialLogin());
        assertNotNull(user.getEmail());
        assertNotNull(user.getUserRole());
        assertNotNull(user.getUserType());

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
        assertNotNull(user.getName());
        assertNotNull(user.getSurname());
        assertNotNull(user.getRegistrationDate());
        assertNotNull(user.getPassword());
        assertNotNull(user.getSocialLogin());
        assertNotNull(user.getEmail());
        assertNotNull(user.getUserRole());
        assertNotNull(user.getUserType());

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
        assertNotNull(user.getName());
        assertNotNull(user.getSurname());
        assertNotNull(user.getRegistrationDate());
        assertNotNull(user.getPassword());
        assertNotNull(user.getSocialLogin());
        assertNotNull(user.getEmail());
        assertNotNull(user.getUserRole());
        assertNotNull(user.getUserType());

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
        assertNotNull(user.getName());
        assertNotNull(user.getSurname());
        assertNotNull(user.getRegistrationDate());
        assertNotNull(user.getPassword());
        assertNotNull(user.getSocialLogin());
        assertNotNull(user.getEmail());
        assertNotNull(user.getUserRole());
        assertNotNull(user.getUserType());

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
        assertNotNull(user.getName());
        assertNotNull(user.getSurname());
        assertNotNull(user.getRegistrationDate());
        assertNotNull(user.getPassword());
        assertNotNull(user.getSocialLogin());
        assertNotNull(user.getEmail());
        assertNotNull(user.getUserRole());
        assertNotNull(user.getUserType());

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
        assertNotNull(user.getName());
        assertNotNull(user.getSurname());
        assertNotNull(user.getRegistrationDate());
        assertNotNull(user.getPassword());
        assertNotNull(user.getSocialLogin());
        assertNotNull(user.getEmail());
        assertNotNull(user.getUserRole());
        assertNotNull(user.getUserType());

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

    /*
     * Testing of updating user
     * Path: /users/user/userId
     * Method: put
     * Send: not valid user (length of address = LENGTH_ADDRESS + 1, max = LENGTH_ADDRESS)
     * Expect: response with status 400
     */
    @Test(expected = BadRequestException.class)
    public void test13UpdateUser() {
        assertNotNull(accessToken);
        assertNotNull(user);
        assertNotNull(user.getId());
        assertNotNull(user.getName());
        assertNotNull(user.getSurname());
        assertNotNull(user.getRegistrationDate());
        assertNotNull(user.getPassword());
        assertNotNull(user.getSocialLogin());
        assertNotNull(user.getEmail());
        assertNotNull(user.getUserRole());
        assertNotNull(user.getUserType());

        User actual = SerializationUtils.clone(user);
        actual.setAddress(RandomStringUtils.random(LENGTH_ADDRESS + 1, true, true));

        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test13UpdateUser - " + json);

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
     * Send: not valid user (length of organizationName = LENGTH_ORGANIZATION_NAME + 1, max = LENGTH_ORGANIZATION_NAME)
     * Expect: response with status 400
     */
    @Test(expected = BadRequestException.class)
    public void test14UpdateUser() {
        assertNotNull(accessToken);
        assertNotNull(user);
        assertNotNull(user.getId());
        assertNotNull(user.getName());
        assertNotNull(user.getSurname());
        assertNotNull(user.getRegistrationDate());
        assertNotNull(user.getPassword());
        assertNotNull(user.getSocialLogin());
        assertNotNull(user.getEmail());
        assertNotNull(user.getUserRole());
        assertNotNull(user.getUserType());

        User actual = SerializationUtils.clone(user);
        actual.setOrganizationName(RandomStringUtils.random(LENGTH_ORGANIZATION_NAME + 1, true, true));

        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test14UpdateUser - " + json);

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
     * Send: not valid user (length of organizationInfo = LENGTH_ORGANIZATION_INFO + 1, max = LENGTH_ORGANIZATION_INFO)
     * Expect: response with status 400
     */
    @Test(expected = BadRequestException.class)
    public void test15UpdateUser() {
        assertNotNull(accessToken);
        assertNotNull(user);
        assertNotNull(user.getId());
        assertNotNull(user.getName());
        assertNotNull(user.getSurname());
        assertNotNull(user.getRegistrationDate());
        assertNotNull(user.getPassword());
        assertNotNull(user.getSocialLogin());
        assertNotNull(user.getEmail());
        assertNotNull(user.getUserRole());
        assertNotNull(user.getUserType());

        User actual = SerializationUtils.clone(user);
        actual.setOrganizationInfo(RandomStringUtils.random(LENGTH_ORGANIZATION_INFO + 1, true, true));

        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test15UpdateUser - " + json);

        client.target(REST_SERVICE_URL)
                .path("user/" + user.getId())
                .request()
                .header("AccessToken", accessToken)
                .put(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), User.class);
    }

    /*
     * Testing of updating user
     * Try to change userRole
     * Path: /users/user/userId
     * Method: put
     * Send: valid user
     * Expect: userRole must be unchanged
     */
    @Test
    public void test16UpdateUser() {
        assertNotNull(accessToken);
        assertNotNull(user);
        assertNotNull(user.getId());
        assertNotNull(user.getName());
        assertNotNull(user.getSurname());
        assertNotNull(user.getRegistrationDate());
        assertNotNull(user.getPassword());
        assertNotNull(user.getSocialLogin());
        assertNotNull(user.getEmail());
        assertNotNull(user.getUserRole());
        assertNotNull(user.getUserType());

        User actual = SerializationUtils.clone(user);

        List<UserRole> userRoles = new ArrayList<>();
        userRoles.add(new UserRoleRepositoryImpl().getAll().get(1));
        actual.setUserRole(userRoles);

        assertNotEquals(user.getUserRole(), actual.getUserRole());

        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test16UpdateUser - " + json);

        User expected = client
                .target(REST_SERVICE_URL)
                .path("user/" + user.getId())
                .request()
                .header("AccessToken", accessToken)
                .put(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), User.class);

        new UserRepositoryImpl().update(user);

        assertNotEquals(expected.getUserRole(), actual.getUserRole());
    }

    /*
     * Testing of updating user
     * Try to change userType
     * Path: /users/user/userId
     * Method: put
     * Send: valid user
     * Expect: userType must be unchanged
     */
    @Test(expected = BadRequestException.class)
    public void test17UpdateUser() {
        assertNotNull(accessToken);
        assertNotNull(user);
        assertNotNull(user.getId());
        assertNotNull(user.getName());
        assertNotNull(user.getSurname());
        assertNotNull(user.getRegistrationDate());
        assertNotNull(user.getPassword());
        assertNotNull(user.getSocialLogin());
        assertNotNull(user.getEmail());
        assertNotNull(user.getUserRole());
        assertNotNull(user.getUserType());

        User actual = SerializationUtils.clone(user);
        actual.setUserType(new UserTypeRepositoryImpl().getAll().get(1));

        assertNotEquals(user.getUserType(), actual.getUserType());

        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test17UpdateUser - " + json);

        User expected = client
                .target(REST_SERVICE_URL)
                .path("user/" + user.getId())
                .request()
                .header("AccessToken", accessToken)
                .put(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), User.class);

        new UserRepositoryImpl().update(user);

        assertNotEquals(expected.getUserType(), actual.getUserType());
    }

    /*
     * Testing of adding animal
     * Path: /users/user/{userId}/animals/animal
     * Method: post
     * Send: valid animal
     * Expect: response with status 200
     */
    @Test
    public void test18UpdateUserAnimal() {
        assertNotNull(accessToken);
        assertNotNull(user);
        assertNotNull(user.getId());
        assertNotNull(animal);
        assertNotNull(animal.getId());
        assertNotNull(animal.getType());
        assertNotNull(animal.getType().getId());
        assertNotNull(animal.getSize());
        assertNotNull(animal.getDateOfRegister());
        assertNotNull(animal.getColor());
        assertNotNull(animal.getAddress());
        assertNotNull(animal.getService());
        assertNotNull(animal.getService().getId());

        Animal actual = SerializationUtils.clone(animal);
        actual.setColor(RandomStringUtils.random(LENGTH_ANIMAL_COLOR, true, true));

        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test18UpdateUserAnimal - " + json);

        Response response = client
                .target(REST_SERVICE_URL)
                .path("user/" + user.getId() + "/animals/animal")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), Response.class);

        assertNotNull(response);
        assertEquals(response.getStatus(), 200);

        Animal expected = new AnimalRepositoryImpl().getAnimalId(animal.getId());

        assertNotEquals(expected.getColor(), animal.getColor());
    }

    /*
     * Testing of getting animals
     * Path: /users/user/{userId}/animals
     * Method: post
     * Send: valid AnimalFilter
     * Expect: list of animals
     */
    @Test
    public void test19GetUserAnimals() {
        assertNotNull(accessToken);
        assertNotNull(user);
        assertNotNull(user.getId());

        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(new AnimalsFilter(1, 5));

        LOG.debug("TestName: test19GetUserAnimals - " + json);

        List<Animal> animals = client
                .target(REST_SERVICE_URL)
                .path("user/" + user.getId() + "/animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), new GenericType<List<Animal>>() {});

        assertNotNull(animals);
        assertEquals(animals.size(), 1);
        assertNotNull(animal);

        animal.setId(animals.get(0).getId());
    }

    /*
     * Testing of getting animals count
     * Path: /users/user/{userId}/animals
     * Method: get
     * Send: valid userId
     * Expect: user animals count
     */
    @Test
    public void test20GetUserAnimalsCount() {
        assertNotNull(accessToken);
        assertNotNull(user);
        assertNotNull(user.getId());

        String count = client
                .target(REST_SERVICE_URL)
                .path("user/" + user.getId() + "/animals/paginator/")
                .request()
                .header("AccessToken", accessToken)
                .get(String.class);

        LOG.debug("TestName: test20GetUserAnimalsCount - " + count);

        Long rowCount = new Gson().fromJson(count, JsonObject.class).get("rowsCount").getAsLong();

        assertNotNull(rowCount);
        assertEquals(rowCount, new Long(1));
    }

    /*
     * Testing of getting animals count
     * Path: /users/user/{userId}/animals
     * Method: get
     * Send: not valid userId (userId = -1)
     * Expect: response with status 400
    */
    @Test(expected = BadRequestException.class)
    public void test21GetUserAnimalsCount() {
        assertNotNull(accessToken);

        client.target(REST_SERVICE_URL)
                .path("user/-1/animals/paginator/")
                .request()
                .header("AccessToken", accessToken)
                .get(String.class);
    }

    /*
     * Testing of getting animals count
     * Path: /users/user/{userId}/animals
     * Method: get
     * Send: not valid userId (userId = 0)
     * Expect: response with status 400
    */
    @Test(expected = BadRequestException.class)
    public void test22GetUserAnimalsCount() {
        assertNotNull(accessToken);

        client.target(REST_SERVICE_URL)
                .path("user/0/animals/paginator/")
                .request()
                .header("AccessToken", accessToken)
                .get(String.class);
    }

    /*
     * Testing of deleting animal
     * Path: /users/user/{userId}/animals/animalId
     * Method: delete
     * Send: valid AnimalFilter
     * Expect: list of animals
     */
    @Test
    public void test23DeleteUserAnimal() {
        assertNotNull(accessToken);
        assertNotNull(user);
        assertNotNull(user.getId());
        assertNotNull(animal);
        assertNotNull(animal.getId());

        Response response = client
                .target(REST_SERVICE_URL)
                .path("user/" + user.getId() + "/animals/" + animal.getId())
                .request()
                .header("AccessToken", accessToken)
                .delete(Response.class);

        assertNotNull(response);
        assertEquals(response.getStatus(), 200);
    }

    /*
     * Testing of getting user types
     * Path: /users/user_types
     * Method: get
     * Send: -
     * Expect: list of user types
     */
    @Test
    public void test24GetUserTypes() {
        assertNotNull(accessToken);

        List<UserType> userTypes = client
                .target(REST_SERVICE_URL)
                .path("user_types")
                .request()
                .header("AccessToken", accessToken)
                .get(new GenericType<List<UserType>>() {
                });

        assertNotNull(userTypes);
        assertNotEquals(userTypes.size(), 0);
    }

    /*
     * Testing of getting user roles
     * Path: /users/user_roles
     * Method: get
     * Send: -
     * Expect: list of user roles
     */
    @Test
    public void test25GetUserTypes() {
        assertNotNull(accessToken);

        List<UserRole> userRoles = client
                .target(REST_SERVICE_URL)
                .path("user_roles")
                .request()
                .header("AccessToken", accessToken)
                .get(new GenericType<List<UserRole>>() {});

        assertNotNull(userRoles);
        assertNotEquals(userRoles.size(), 0);
    }
}
