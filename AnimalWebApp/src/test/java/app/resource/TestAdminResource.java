package app.resource;

import app.JNDIConfigurationForTests;
import com.animals.app.domain.Animal;
import com.animals.app.domain.AnimalService;
import com.animals.app.domain.User;
import com.animals.app.repository.AnimalRepository;
import com.animals.app.repository.Impl.AnimalRepositoryImpl;
import com.animals.app.repository.Impl.AnimalServiceRepositoryImpl;
import com.animals.app.repository.Impl.AnimalTypeRepositoryImpl;
import com.animals.app.service.DateSerializer;
import com.animals.app.service.ValidationFilterDomainFields;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.json.JSONObject;
import org.junit.*;
import org.junit.experimental.categories.Category;
import org.junit.runners.MethodSorters;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Rostyslav.Viner on 03.09.2015.
 */
@Category(IntegrationTest.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAdminResource extends JerseyTest {
    private static Logger LOG = LogManager.getLogger(TestAdminResource.class);

    private static Client client;

    private static final String LOGIN = "root";
    private static final String PASSWORD = "root";
    private static String accessToken;
    private static Animal animal;

    //size of fields in data base, table: animals
    private final int LENGTH_TRANSPNUMBER = 15;
    private final int LENGTH_TOKENNUMBER = 12;
    private final int LENGTH_COLOR = 20;
    private final int LENGTH_DESCRIPTION = 100;
    private final int LENGTH_ADDRESS = 120;
    private final int LENGTH_IMAGE = 50;

    private static final String REST_SERVICE_URL = "http://localhost:9998/admin";
    private static final String REST_LOGIN_URL = "http://localhost:9998/account";

    @Override
    protected TestContainerFactory getTestContainerFactory() {
        return new GrizzlyWebTestContainerFactory();
    }

    @Override
    protected DeploymentContext configureDeployment() {
        ResourceConfig config = new ValidationFilterDomainFields();
        return ServletDeploymentContext.forServlet(
                new ServletContainer(config)).build();
    }

    @BeforeClass
    public static void runBeforeClass() {
        JNDIConfigurationForTests.configureJNDIForJUnit();

        client = ClientBuilder.newClient();
    }

    @AfterClass
    public static void runAfterClass() {
        client = null;
    }

    @Test
    public void test00Login() {
        String passwordMd5 = getMd5(PASSWORD);
        String credentials = "Basic " + Base64.encodeBase64String((LOGIN + ':' + passwordMd5).getBytes());

        String result = client
                .target(REST_LOGIN_URL)
                .path("/login/OFF")
                .request()
                .header("Authorization", credentials)
                .post(null, String.class);

        JSONObject json = new JSONObject(result);
        accessToken = json.getString("accessToken");

        AnimalRepository animalRepository = new AnimalRepositoryImpl();

        animal = new Animal();
        animal.setSex(Animal.SexType.FEMALE);
        animal.setType(new AnimalTypeRepositoryImpl().getAll().get(0));
        animal.setSize(Animal.SizeType.LARGE);
        animal.setDateOfRegister(new Date(System.currentTimeMillis()));
        animal.setColor(RandomStringUtils.random(10, true, true));
        animal.setAddress(RandomStringUtils.random(10, true, true));
        animal.setService(new AnimalServiceRepositoryImpl().getAll().get(0));

        animalRepository.insert(animal);

        assertNotNull(animal.getId());
    }

    @Test
    public void test01GetAnimal() {
        assertNotNull(accessToken);
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

        Animal expected = client
                .target(REST_SERVICE_URL)
                .path("animals/" + animal.getId())
                .request()
                .header("AccessToken", accessToken)
                .get(Animal.class);

        assertNotNull(expected);
    }

    /*
     * animalId = -1
     */
    @Test(expected = BadRequestException.class)
    public void test02GetAnimal() {
        assertNotNull(accessToken);

        client.target(REST_SERVICE_URL)
                .path("animals/-1")
                .request()
                .header("AccessToken", accessToken)
                .get(Animal.class);
    }

    /*
     * animalId = 0
     */
    @Test(expected = BadRequestException.class)
    public void test03GetAnimal() {
        assertNotNull(accessToken);

        client.target(REST_SERVICE_URL)
                .path("animals/0")
                .request()
                .header("AccessToken", accessToken)
                .get(Animal.class);
    }

    /*
     * Animal = null
     */
    @Test(expected = BadRequestException.class)
    public void test04UpdateAnimal() {
        assertNotNull(accessToken);

        client.target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(null, MediaType.APPLICATION_JSON), String.class);
    }

    /*
     * Animal.id = null
     */
    @Test(expected = BadRequestException.class)
    public void test05UpdateAnimal() {
        assertNotNull(accessToken);
        assertNotNull(animal);
        assertNotNull(animal.getId());
        assertNotNull(animal.getSex());
        assertNotNull(animal.getType());
        assertNotNull(animal.getType().getId());
        assertNotNull(animal.getSize());
        assertNotNull(animal.getDateOfRegister());
        assertNotNull(animal.getColor());
        assertNotNull(animal.getAddress());
        assertNotNull(animal.getService());
        assertNotNull(animal.getService().getId());

        Animal actual = SerializationUtils.clone(animal);
        actual.setId(null);
        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test05UpdateAnimal - " + json);

        client.target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON), String.class);
    }

    /*
     * Animal.id = -1
     */
    @Test(expected = BadRequestException.class)
    public void test06UpdateAnimal() {
        assertNotNull(accessToken);
        assertNotNull(animal);
        assertNotNull(animal.getId());
        assertNotNull(animal.getSex());
        assertNotNull(animal.getType());
        assertNotNull(animal.getType().getId());
        assertNotNull(animal.getSize());
        assertNotNull(animal.getDateOfRegister());
        assertNotNull(animal.getColor());
        assertNotNull(animal.getAddress());
        assertNotNull(animal.getService());
        assertNotNull(animal.getService().getId());

        Animal actual = SerializationUtils.clone(animal);
        actual.setId(new Long(-1));
        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test06UpdateAnimal - " + json);

        client.target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON), String.class);
    }

    /*
     * Animal.id = 0
     */
    @Test(expected = BadRequestException.class)
    public void test07UpdateAnimal() {
        assertNotNull(accessToken);
        assertNotNull(animal);
        assertNotNull(animal.getId());
        assertNotNull(animal.getSex());
        assertNotNull(animal.getType());
        assertNotNull(animal.getType().getId());
        assertNotNull(animal.getSize());
        assertNotNull(animal.getDateOfRegister());
        assertNotNull(animal.getColor());
        assertNotNull(animal.getAddress());
        assertNotNull(animal.getService());
        assertNotNull(animal.getService().getId());

        Animal actual = SerializationUtils.clone(animal);
        actual.setId(new Long(0));
        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test07UpdateAnimal - " + json);

        client.target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON), String.class);
    }

    /*
     * Animal.type.id = null
     */
    @Test(expected = BadRequestException.class)
    public void test08UpdateAnimal() {
        assertNotNull(accessToken);
        assertNotNull(animal);
        assertNotNull(animal.getId());
        assertNotNull(animal.getSex());
        assertNotNull(animal.getType());
        assertNotNull(animal.getType().getId());
        assertNotNull(animal.getSize());
        assertNotNull(animal.getDateOfRegister());
        assertNotNull(animal.getColor());
        assertNotNull(animal.getAddress());
        assertNotNull(animal.getService());
        assertNotNull(animal.getService().getId());

        Animal actual = SerializationUtils.clone(animal);
        actual.getType().setId(null);
        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test08UpdateAnimal - " + json);

        client.target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON), String.class);
    }

    /*
     * Animal.type.id = -1
     */
    @Test(expected = BadRequestException.class)
    public void test09UpdateAnimal() {
        assertNotNull(accessToken);
        assertNotNull(animal);
        assertNotNull(animal.getId());
        assertNotNull(animal.getSex());
        assertNotNull(animal.getType());
        assertNotNull(animal.getType().getId());
        assertNotNull(animal.getSize());
        assertNotNull(animal.getDateOfRegister());
        assertNotNull(animal.getColor());
        assertNotNull(animal.getAddress());
        assertNotNull(animal.getService());
        assertNotNull(animal.getService().getId());

        Animal actual = SerializationUtils.clone(animal);
        actual.getType().setId(new Long(-1));
        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test09UpdateAnimal - " + json);

        client.target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON), String.class);
    }

    /*
     * Animal.type.id = 0
     */
    @Test(expected = BadRequestException.class)
    public void test10UpdateAnimal() {
        assertNotNull(accessToken);
        assertNotNull(animal);
        assertNotNull(animal.getId());
        assertNotNull(animal.getSex());
        assertNotNull(animal.getType());
        assertNotNull(animal.getType().getId());
        assertNotNull(animal.getSize());
        assertNotNull(animal.getDateOfRegister());
        assertNotNull(animal.getColor());
        assertNotNull(animal.getAddress());
        assertNotNull(animal.getService());
        assertNotNull(animal.getService().getId());

        Animal actual = SerializationUtils.clone(animal);
        actual.getType().setId(new Long(0));
        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test10UpdateAnimal - " + json);

        client.target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON), String.class);
    }

    /*
     * Animal.user = null
     */
    @Test
    public void test11UpdateAnimal() {
        assertNotNull(accessToken);
        assertNotNull(animal);
        assertNotNull(animal.getId());
        assertNotNull(animal.getSex());
        assertNotNull(animal.getType());
        assertNotNull(animal.getType().getId());
        assertNotNull(animal.getSize());
        assertNotNull(animal.getDateOfRegister());
        assertNotNull(animal.getColor());
        assertNotNull(animal.getAddress());
        assertNotNull(animal.getService());
        assertNotNull(animal.getService().getId());

        Animal actual = SerializationUtils.clone(animal);
        actual.setUser(null);
        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test11UpdateAnimal - " + json);

        String result = client
                .target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON), String.class);

        LOG.debug("TestName: test11UpdateAnimal - " + result);

        Map<String, String> jsonMap = new Gson().fromJson(result, HashMap.class);

        assertNotNull(jsonMap);
        assertNotNull(jsonMap.get("filePath"));
    }

    /*
     * Animal.user.id = null
     */
    @Test
    public void test12UpdateAnimal() {
        assertNotNull(accessToken);
        assertNotNull(animal);
        assertNotNull(animal.getId());
        assertNotNull(animal.getSex());
        assertNotNull(animal.getType());
        assertNotNull(animal.getType().getId());
        assertNotNull(animal.getSize());
        assertNotNull(animal.getDateOfRegister());
        assertNotNull(animal.getColor());
        assertNotNull(animal.getAddress());
        assertNotNull(animal.getService());
        assertNotNull(animal.getService().getId());

        Animal actual = SerializationUtils.clone(animal);
        actual.setUser(new User());
        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test12UpdateAnimal - " + json);

        String result = client
                .target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON), String.class);

        LOG.debug("TestName: test12UpdateAnimal - " + result);

        Map<String, String> jsonMap = new Gson().fromJson(result, HashMap.class);

        assertNotNull(jsonMap);
        assertNotNull(jsonMap.get("filePath"));
    }

    /*
     * Animal.user.id = -1
     */
    @Test(expected = BadRequestException.class)
    public void test13UpdateAnimal() {
        assertNotNull(accessToken);
        assertNotNull(animal);
        assertNotNull(animal.getId());
        assertNotNull(animal.getSex());
        assertNotNull(animal.getType());
        assertNotNull(animal.getType().getId());
        assertNotNull(animal.getSize());
        assertNotNull(animal.getDateOfRegister());
        assertNotNull(animal.getColor());
        assertNotNull(animal.getAddress());
        assertNotNull(animal.getService());
        assertNotNull(animal.getService().getId());

        Animal actual = SerializationUtils.clone(animal);
        User user = new User();
        user.setId(new Integer(-1));
        actual.setUser(user);
        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test13UpdateAnimal - " + json);

        client.target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON), String.class);
    }

    /*
     * Animal.user.id = 0
     */
    @Test(expected = BadRequestException.class)
    public void test14UpdateAnimal() {
        assertNotNull(accessToken);
        assertNotNull(animal);
        assertNotNull(animal.getId());
        assertNotNull(animal.getSex());
        assertNotNull(animal.getType());
        assertNotNull(animal.getType().getId());
        assertNotNull(animal.getSize());
        assertNotNull(animal.getDateOfRegister());
        assertNotNull(animal.getColor());
        assertNotNull(animal.getAddress());
        assertNotNull(animal.getService());
        assertNotNull(animal.getService().getId());

        Animal actual = SerializationUtils.clone(animal);
        User user = new User();
        user.setId(new Integer(0));
        actual.setUser(user);
        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test14UpdateAnimal - " + json);

        client.target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON), String.class);
    }

    /*
     * Animal.user.id = max int value
     */
    @Test(expected = BadRequestException.class)
    public void test15UpdateAnimal() {
        assertNotNull(accessToken);
        assertNotNull(animal);
        assertNotNull(animal.getId());
        assertNotNull(animal.getSex());
        assertNotNull(animal.getType());
        assertNotNull(animal.getType().getId());
        assertNotNull(animal.getSize());
        assertNotNull(animal.getDateOfRegister());
        assertNotNull(animal.getColor());
        assertNotNull(animal.getAddress());
        assertNotNull(animal.getService());
        assertNotNull(animal.getService().getId());

        Animal actual = SerializationUtils.clone(animal);
        User user = new User();
        user.setId(Integer.MAX_VALUE);
        actual.setUser(user);
        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test15UpdateAnimal - " + json);

        client.target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON), String.class);
    }


    /*
     * Animal.service = null
     */
    @Test(expected = BadRequestException.class)
    public void test16UpdateAnimal() {
        assertNotNull(accessToken);
        assertNotNull(animal);
        assertNotNull(animal.getId());
        assertNotNull(animal.getSex());
        assertNotNull(animal.getType());
        assertNotNull(animal.getType().getId());
        assertNotNull(animal.getSize());
        assertNotNull(animal.getDateOfRegister());
        assertNotNull(animal.getColor());
        assertNotNull(animal.getAddress());
        assertNotNull(animal.getService());
        assertNotNull(animal.getService().getId());

        Animal actual = SerializationUtils.clone(animal);
        actual.setService(null);
        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test16UpdateAnimal - " + json);

        client.target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON), String.class);
    }

    /*
     * Animal.service.id = null
     */
    @Test(expected = BadRequestException.class)
    public void test17UpdateAnimal() {
        assertNotNull(accessToken);
        assertNotNull(animal);
        assertNotNull(animal.getId());
        assertNotNull(animal.getSex());
        assertNotNull(animal.getType());
        assertNotNull(animal.getType().getId());
        assertNotNull(animal.getSize());
        assertNotNull(animal.getDateOfRegister());
        assertNotNull(animal.getColor());
        assertNotNull(animal.getAddress());
        assertNotNull(animal.getService());
        assertNotNull(animal.getService().getId());

        Animal actual = SerializationUtils.clone(animal);
        actual.setService(new AnimalService());
        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test17UpdateAnimal - " + json);

        client.target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON), String.class);
    }

    /*
     * Animal.service.id = -1
     */
    @Test(expected = BadRequestException.class)
    public void test18UpdateAnimal() {
        assertNotNull(accessToken);
        assertNotNull(animal);
        assertNotNull(animal.getId());
        assertNotNull(animal.getSex());
        assertNotNull(animal.getType());
        assertNotNull(animal.getType().getId());
        assertNotNull(animal.getSize());
        assertNotNull(animal.getDateOfRegister());
        assertNotNull(animal.getColor());
        assertNotNull(animal.getAddress());
        assertNotNull(animal.getService());
        assertNotNull(animal.getService().getId());

        Animal actual = SerializationUtils.clone(animal);
        AnimalService animalService = new AnimalService();
        animalService.setId(new Long(-1));
        actual.setService(animalService);
        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test18UpdateAnimal - " + json);

        client.target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON), String.class);
    }

    /*
     * Animal.service.id = 0
     */
    @Test(expected = BadRequestException.class)
    public void test19UpdateAnimal() {
        assertNotNull(accessToken);
        assertNotNull(animal);
        assertNotNull(animal.getId());
        assertNotNull(animal.getSex());
        assertNotNull(animal.getType());
        assertNotNull(animal.getType().getId());
        assertNotNull(animal.getSize());
        assertNotNull(animal.getDateOfRegister());
        assertNotNull(animal.getColor());
        assertNotNull(animal.getAddress());
        assertNotNull(animal.getService());
        assertNotNull(animal.getService().getId());

        Animal actual = SerializationUtils.clone(animal);
        AnimalService animalService = new AnimalService();
        animalService.setId(new Long(0));
        actual.setService(animalService);
        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test19UpdateAnimal - " + json);

        client.target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON), String.class);
    }

    /*
     * Animal.service.id = max int value
     */
    @Test(expected = BadRequestException.class)
    public void test20UpdateAnimal() {
        assertNotNull(accessToken);
        assertNotNull(animal);
        assertNotNull(animal.getId());
        assertNotNull(animal.getSex());
        assertNotNull(animal.getType());
        assertNotNull(animal.getType().getId());
        assertNotNull(animal.getSize());
        assertNotNull(animal.getDateOfRegister());
        assertNotNull(animal.getColor());
        assertNotNull(animal.getAddress());
        assertNotNull(animal.getService());
        assertNotNull(animal.getService().getId());

        Animal actual = SerializationUtils.clone(animal);
        AnimalService animalService = new AnimalService();
        animalService.setId(new Long(Integer.MAX_VALUE));
        actual.setService(animalService);
        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test20UpdateAnimal - " + json);

        client.target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON), String.class);
    }

    /*
     * Animal.sex = null
     */
    @Test(expected = BadRequestException.class)
    public void test21UpdateAnimal() {
        assertNotNull(accessToken);
        assertNotNull(animal);
        assertNotNull(animal.getId());
        assertNotNull(animal.getSex());
        assertNotNull(animal.getType());
        assertNotNull(animal.getType().getId());
        assertNotNull(animal.getSize());
        assertNotNull(animal.getDateOfRegister());
        assertNotNull(animal.getColor());
        assertNotNull(animal.getAddress());
        assertNotNull(animal.getService());
        assertNotNull(animal.getService().getId());

        Animal actual = SerializationUtils.clone(animal);
        actual.setSex(null);
        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test21UpdateAnimal - " + json);

        client.target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON), String.class);
    }

    /*
     * Animal.size = null
     */
    @Test(expected = BadRequestException.class)
    public void test22UpdateAnimal() {
        assertNotNull(accessToken);
        assertNotNull(animal);
        assertNotNull(animal.getId());
        assertNotNull(animal.getSex());
        assertNotNull(animal.getType());
        assertNotNull(animal.getType().getId());
        assertNotNull(animal.getSize());
        assertNotNull(animal.getDateOfRegister());
        assertNotNull(animal.getColor());
        assertNotNull(animal.getAddress());
        assertNotNull(animal.getService());
        assertNotNull(animal.getService().getId());

        Animal actual = SerializationUtils.clone(animal);
        actual.setSize(null);
        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test22UpdateAnimal - " + json);

        client.target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON), String.class);
    }

    /*
     * Animal.transpNumber.length > LENGTH_TRANSPNUMBER
     */
    @Test(expected = BadRequestException.class)
    public void test23UpdateAnimal() {
        assertNotNull(accessToken);
        assertNotNull(animal);
        assertNotNull(animal.getId());
        assertNotNull(animal.getSex());
        assertNotNull(animal.getType());
        assertNotNull(animal.getType().getId());
        assertNotNull(animal.getSize());
        assertNotNull(animal.getDateOfRegister());
        assertNotNull(animal.getColor());
        assertNotNull(animal.getAddress());
        assertNotNull(animal.getService());
        assertNotNull(animal.getService().getId());

        Animal actual = SerializationUtils.clone(animal);
        actual.setTranspNumber(RandomStringUtils.random(LENGTH_TRANSPNUMBER + 1, true, true));
        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test23UpdateAnimal - " + json);

        client.target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON), String.class);
    }

    /*
     * Animal.tokenNumber.length > LENGTH_TOKENNUMBER
     */
    @Test(expected = BadRequestException.class)
    public void test24UpdateAnimal() {
        assertNotNull(accessToken);
        assertNotNull(animal);
        assertNotNull(animal.getId());
        assertNotNull(animal.getSex());
        assertNotNull(animal.getType());
        assertNotNull(animal.getType().getId());
        assertNotNull(animal.getSize());
        assertNotNull(animal.getDateOfRegister());
        assertNotNull(animal.getColor());
        assertNotNull(animal.getAddress());
        assertNotNull(animal.getService());
        assertNotNull(animal.getService().getId());

        Animal actual = SerializationUtils.clone(animal);
        actual.setTokenNumber(RandomStringUtils.random(LENGTH_TOKENNUMBER + 1, true, true));
        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test24UpdateAnimal - " + json);

        client.target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON), String.class);
    }

    /*
     * Animal.dateOfRegister = null
     */
    @Test(expected = BadRequestException.class)
    public void test25UpdateAnimal() {
        assertNotNull(accessToken);
        assertNotNull(animal);
        assertNotNull(animal.getId());
        assertNotNull(animal.getSex());
        assertNotNull(animal.getType());
        assertNotNull(animal.getType().getId());
        assertNotNull(animal.getSize());
        assertNotNull(animal.getDateOfRegister());
        assertNotNull(animal.getColor());
        assertNotNull(animal.getAddress());
        assertNotNull(animal.getService());
        assertNotNull(animal.getService().getId());

        Animal actual = SerializationUtils.clone(animal);
        actual.setDateOfRegister(null);
        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test25UpdateAnimal - " + json);

        client.target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON), String.class);
    }

    /*
     * Animal.color.length > LENGTH_COLOR
     */
    @Test(expected = BadRequestException.class)
    public void test26UpdateAnimal() {
        assertNotNull(accessToken);
        assertNotNull(animal);
        assertNotNull(animal.getId());
        assertNotNull(animal.getSex());
        assertNotNull(animal.getType());
        assertNotNull(animal.getType().getId());
        assertNotNull(animal.getSize());
        assertNotNull(animal.getDateOfRegister());
        assertNotNull(animal.getColor());
        assertNotNull(animal.getAddress());
        assertNotNull(animal.getService());
        assertNotNull(animal.getService().getId());

        Animal actual = SerializationUtils.clone(animal);
        actual.setColor(RandomStringUtils.random(LENGTH_COLOR + 1, true, true));
        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test26UpdateAnimal - " + json);

        client.target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON), String.class);
    }

    /*
     * Animal.description.length > LENGTH_DESCRIPTION
     */
    @Test(expected = BadRequestException.class)
    public void test27UpdateAnimal() {
        assertNotNull(accessToken);
        assertNotNull(animal);
        assertNotNull(animal.getId());
        assertNotNull(animal.getSex());
        assertNotNull(animal.getType());
        assertNotNull(animal.getType().getId());
        assertNotNull(animal.getSize());
        assertNotNull(animal.getDateOfRegister());
        assertNotNull(animal.getColor());
        assertNotNull(animal.getAddress());
        assertNotNull(animal.getService());
        assertNotNull(animal.getService().getId());

        Animal actual = SerializationUtils.clone(animal);
        actual.setDescription(RandomStringUtils.random(LENGTH_DESCRIPTION + 1, true, true));
        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test27UpdateAnimal - " + json);

        client.target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON), String.class);
    }

    /*
     * Animal.address.length > LENGTH_ADDRESS
     */
    @Test(expected = BadRequestException.class)
    public void test28UpdateAnimal() {
        assertNotNull(accessToken);
        assertNotNull(animal);
        assertNotNull(animal.getId());
        assertNotNull(animal.getSex());
        assertNotNull(animal.getType());
        assertNotNull(animal.getType().getId());
        assertNotNull(animal.getSize());
        assertNotNull(animal.getDateOfRegister());
        assertNotNull(animal.getColor());
        assertNotNull(animal.getAddress());
        assertNotNull(animal.getService());
        assertNotNull(animal.getService().getId());

        Animal actual = SerializationUtils.clone(animal);
        actual.setAddress(RandomStringUtils.random(LENGTH_ADDRESS + 1, true, true));
        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test28UpdateAnimal - " + json);

        client.target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON), String.class);
    }

    /*
     * Animal.address = null
     */
    @Test(expected = BadRequestException.class)
    public void test29UpdateAnimal() {
        assertNotNull(accessToken);
        assertNotNull(animal);
        assertNotNull(animal.getId());
        assertNotNull(animal.getSex());
        assertNotNull(animal.getType());
        assertNotNull(animal.getType().getId());
        assertNotNull(animal.getSize());
        assertNotNull(animal.getDateOfRegister());
        assertNotNull(animal.getColor());
        assertNotNull(animal.getAddress());
        assertNotNull(animal.getService());
        assertNotNull(animal.getService().getId());

        Animal actual = SerializationUtils.clone(animal);
        actual.setAddress(null);
        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test29UpdateAnimal - " + json);

        client.target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON), String.class);
    }

    /*
     * Animal.image.length > LENGTH_IMAGE
     */
    @Test(expected = BadRequestException.class)
    public void test30UpdateAnimal() {
        assertNotNull(accessToken);
        assertNotNull(animal);
        assertNotNull(animal.getId());
        assertNotNull(animal.getSex());
        assertNotNull(animal.getType());
        assertNotNull(animal.getType().getId());
        assertNotNull(animal.getSize());
        assertNotNull(animal.getDateOfRegister());
        assertNotNull(animal.getColor());
        assertNotNull(animal.getAddress());
        assertNotNull(animal.getService());
        assertNotNull(animal.getService().getId());

        Animal actual = SerializationUtils.clone(animal);
        actual.setImage(RandomStringUtils.random(LENGTH_IMAGE + 1, true, true));
        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test30UpdateAnimal - " + json);

        client.target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON), String.class);
    }

    @Test
    public void test31DeleteAnimal() {
        assertNotNull(accessToken);

        Response response = client
                .target(REST_SERVICE_URL)
                .path("animals/-1")
                .request()
                .header("AccessToken", accessToken)
                .delete();

        assertNotNull(response);
        assertEquals(response.getStatus(), 400);
    }

    @Test
    public void test32DeleteAnimal() {
        assertNotNull(accessToken);

        Response response = client
                .target(REST_SERVICE_URL)
                .path("animals/0")
                .request()
                .header("AccessToken", accessToken)
                .delete();

        assertNotNull(response);
        assertEquals(response.getStatus(), 400);
    }

    @Test
    public void test33DeleteAnimal() {
        assertNotNull(accessToken);
        assertNotNull(animal);
        assertNotNull(animal.getId());

        Response response = client
                .target(REST_SERVICE_URL)
                .path("animals/" + animal.getId())
                .request()
                .header("AccessToken", accessToken)
                .delete();

        assertNotNull(response);
        assertEquals(response.getStatus(), 200);
    }

    private static String getMd5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
}