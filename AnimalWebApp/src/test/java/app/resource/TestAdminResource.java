package app.resource;

import app.JNDIConfigurationForTests;
import com.animals.app.controller.resource.AdminResource;
import com.animals.app.domain.Animal;
import com.animals.app.domain.AnimalService;
import com.animals.app.domain.User;
import com.animals.app.repository.AnimalRepository;
import com.animals.app.repository.Impl.AnimalRepositoryImpl;
import com.animals.app.repository.Impl.AnimalServiceRepositoryImpl;
import com.animals.app.repository.Impl.AnimalTypeRepositoryImpl;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.json.JSONObject;
import org.junit.*;
import org.junit.experimental.categories.Category;
import org.junit.runners.MethodSorters;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;

import static org.junit.Assert.*;

/**
 * Created by Rostyslav.Viner on 03.09.2015.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAdminResource extends JerseyTest {
    private static Animal animal;

    //size of fields in data base, table: animals
    private final int LENGTH_TRANSPNUMBER = 15;
    private final int LENGTH_TOKENNUMBER = 12;
    private final int LENGTH_COLOR = 20;
    private final int LENGTH_DESCRIPTION = 100;
    private final int LENGTH_ADDRESS = 120;
    private final int LENGTH_IMAGE = 50;

    private static final String REST_SERVICE_URL = "admin";

    @Override
    protected Application configure() {
        return new ResourceConfig(AdminResource.class);
    }

    @BeforeClass
    public static void runBeforeClass() {
        JNDIConfigurationForTests.configureJNDIForJUnit();

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

    @AfterClass
    public static void runAfterClass() {
        animal = null;
    }

    @Test
    public void test01GetAnimal() {
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

        Animal expected = target(REST_SERVICE_URL)
                .path("animals/" + animal.getId())
                .request()
                .get(Animal.class);

        assertNotNull(expected);
    }

    @Test(expected = BadRequestException.class)
    public void test02GetAnimal() {
        target(REST_SERVICE_URL)
                .path("animals/-1")
                .request()
                .get(Animal.class);
    }

    @Test(expected = BadRequestException.class)
    public void test03GetAnimal() {
        target(REST_SERVICE_URL)
                .path("animals/0")
                .request()
                .get(Animal.class);
    }

    /*
     * Animal = null
     */
    @Test(expected = BadRequestException.class)
    public void test04UpdateAnimal() {
        target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .post(null, Animal.class);
    }

    /*
     * Animal.id = null
     */
    @Test(expected = BadRequestException.class)
    public void test05UpdateAnimal() {
        target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .post(Entity.entity(new Animal(), MediaType.APPLICATION_JSON), Animal.class);
    }

    /*
     * Animal.id = -1
     */
    @Test(expected = BadRequestException.class)
    public void test06UpdateAnimal() {
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
        actual.setId(new Long(-1));

        target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .post(Entity.entity(actual, MediaType.APPLICATION_JSON), Animal.class);
    }

    /*
     * Animal.id = 0
     */
    @Test(expected = BadRequestException.class)
    public void test07UpdateAnimal() {
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
        actual.setId(new Long(0));

        target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .post(Entity.entity(actual, MediaType.APPLICATION_JSON), Animal.class);
    }

    /*
     * Animal.type.id = null
     */
    @Test(expected = BadRequestException.class)
    public void test08UpdateAnimal() {
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
        actual.getType().setId(null);

        target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .post(Entity.entity(actual, MediaType.APPLICATION_JSON), String.class);
    }

    /*
     * Animal.type.id = -1
     */
    @Test(expected = BadRequestException.class)
    public void test09UpdateAnimal() {
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
        actual.getType().setId(new Long(-1));

        target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .post(Entity.entity(actual, MediaType.APPLICATION_JSON), String.class);
    }

    /*
     * Animal.type.id = 0
     */
    @Test(expected = BadRequestException.class)
    public void test10UpdateAnimal() {
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
        actual.getType().setId(new Long(0));

        target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .post(Entity.entity(actual, MediaType.APPLICATION_JSON), String.class);
    }

    /*
     * Animal.user = null
     */
    @Test
    public void test11UpdateAnimal() {
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
        actual.setUser(null);

        String result = target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .post(Entity.entity(actual, MediaType.APPLICATION_JSON), String.class);

        JSONObject json = new JSONObject(result);
        String filePath = json.getString("filePath");

        assertNotNull(filePath);
    }

    /*
     * Animal.user.id = null
     */
    @Test(expected = BadRequestException.class)
    public void test12UpdateAnimal() {
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
        actual.setUser(new User());

        String result = target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .post(Entity.entity(actual, MediaType.APPLICATION_JSON), String.class);

        JSONObject json = new JSONObject(result);
        String filePath = json.getString("filePath");

        assertNotNull(filePath);
    }

    /*
     * Animal.user.id = -1
     */
    @Test(expected = BadRequestException.class)
    public void test13UpdateAnimal() {
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
        User user = new User();
        user.setId(-1);
        actual.setUser(user);

        String result = target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .post(Entity.entity(actual, MediaType.APPLICATION_JSON), String.class);

        JSONObject json = new JSONObject(result);
        String filePath = json.getString("filePath");

        assertNotNull(filePath);
    }

    /*
     * Animal.user.id = 0
     */
    @Test(expected = BadRequestException.class)
    public void test14UpdateAnimal() {
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
        User user = new User();
        user.setId(0);
        actual.setUser(user);

        String result = target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .post(Entity.entity(actual, MediaType.APPLICATION_JSON), String.class);

        JSONObject json = new JSONObject(result);
        String filePath = json.getString("filePath");

        assertNotNull(filePath);
    }

    /*
     * Animal.user.id = max int value
     */
    @Test(expected = BadRequestException.class)
    public void test15UpdateAnimal() {
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
        User user = new User();
        user.setId(Integer.MAX_VALUE);
        actual.setUser(user);

        String result = target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .post(Entity.entity(actual, MediaType.APPLICATION_JSON), String.class);

        JSONObject json = new JSONObject(result);
        String filePath = json.getString("filePath");

        assertNotNull(filePath);
    }


    /*
     * Animal.service = null
     */
    @Test(expected = BadRequestException.class)
    public void test16UpdateAnimal() {
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
        actual.setService(null);

        String result = target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .post(Entity.entity(actual, MediaType.APPLICATION_JSON), String.class);

        JSONObject json = new JSONObject(result);
        String filePath = json.getString("filePath");

        assertNotNull(filePath);
    }

    /*
     * Animal.service.id = null
     */
    @Test(expected = BadRequestException.class)
    public void test17UpdateAnimal() {
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
        actual.setService(new AnimalService());

        String result = target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .post(Entity.entity(actual, MediaType.APPLICATION_JSON), String.class);

        JSONObject json = new JSONObject(result);
        String filePath = json.getString("filePath");

        assertNotNull(filePath);
    }

    /*
     * Animal.service.id = -1
     */
    @Test(expected = BadRequestException.class)
    public void test18UpdateAnimal() {
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
        AnimalService animalService = new AnimalService();
        animalService.setId(new Long(-1));
        actual.setService(animalService);

        String result = target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .post(Entity.entity(actual, MediaType.APPLICATION_JSON), String.class);

        JSONObject json = new JSONObject(result);
        String filePath = json.getString("filePath");

        assertNotNull(filePath);
    }

    /*
     * Animal.service.id = 0
     */
    @Test(expected = BadRequestException.class)
    public void test19UpdateAnimal() {
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
        AnimalService animalService = new AnimalService();
        animalService.setId(new Long(0));
        actual.setService(animalService);

        String result = target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .post(Entity.entity(actual, MediaType.APPLICATION_JSON), String.class);

        JSONObject json = new JSONObject(result);
        String filePath = json.getString("filePath");

        assertNotNull(filePath);
    }

    /*
     * Animal.service.id = max int value
     */
    @Test(expected = BadRequestException.class)
    public void test20UpdateAnimal() {
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
        AnimalService animalService = new AnimalService();
        animalService.setId(new Long(Integer.MAX_VALUE));
        actual.setService(animalService);

        String result = target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .post(Entity.entity(actual, MediaType.APPLICATION_JSON), String.class);

        JSONObject json = new JSONObject(result);
        String filePath = json.getString("filePath");

        assertNotNull(filePath);
    }

    /*
     * Animal.sex = null
     */
    @Test(expected = BadRequestException.class)
    public void test21UpdateAnimal() {
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
        actual.setSex(null);

        String result = target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .post(Entity.entity(actual, MediaType.APPLICATION_JSON), String.class);

        JSONObject json = new JSONObject(result);
        String filePath = json.getString("filePath");

        assertNotNull(filePath);
    }

    /*
     * Animal.size = null
     */
    @Test(expected = BadRequestException.class)
    public void test22UpdateAnimal() {
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
        actual.setSize(null);

        String result = target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .post(Entity.entity(actual, MediaType.APPLICATION_JSON), String.class);

        JSONObject json = new JSONObject(result);
        String filePath = json.getString("filePath");

        assertNotNull(filePath);
    }

    /*
     * Animal.transpNumber.length > LENGTH_TRANSPNUMBER
     */
    @Test(expected = BadRequestException.class)
    public void test23UpdateAnimal() {
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
        actual.setTranspNumber(RandomStringUtils.random(LENGTH_TRANSPNUMBER + 1, true, true));

        String result = target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .post(Entity.entity(actual, MediaType.APPLICATION_JSON), String.class);

        JSONObject json = new JSONObject(result);
        String filePath = json.getString("filePath");

        assertNotNull(filePath);
    }

    /*
     * Animal.tokenNumber.length > LENGTH_TOKENNUMBER
     */
    @Test(expected = BadRequestException.class)
    public void test24UpdateAnimal() {
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
        actual.setTokenNumber(RandomStringUtils.random(LENGTH_TOKENNUMBER + 1, true, true));

        String result = target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .post(Entity.entity(actual, MediaType.APPLICATION_JSON), String.class);

        JSONObject json = new JSONObject(result);
        String filePath = json.getString("filePath");

        assertNotNull(filePath);
    }

    /*
     * Animal.dateOfRegister = null
     */
    @Test(expected = BadRequestException.class)
    public void test25UpdateAnimal() {
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
        actual.setDateOfRegister(null);

        String result = target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .post(Entity.entity(actual, MediaType.APPLICATION_JSON), String.class);

        JSONObject json = new JSONObject(result);
        String filePath = json.getString("filePath");

        assertNotNull(filePath);
    }

    /*
     * Animal.color.length > LENGTH_COLOR
     */
    @Test(expected = BadRequestException.class)
    public void test26UpdateAnimal() {
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
        actual.setColor(RandomStringUtils.random(LENGTH_COLOR + 1, true, true));

        String result = target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .post(Entity.entity(actual, MediaType.APPLICATION_JSON), String.class);

        JSONObject json = new JSONObject(result);
        String filePath = json.getString("filePath");

        assertNotNull(filePath);
    }

    /*
     * Animal.description.length > LENGTH_DESCRIPTION
     */
    @Test(expected = BadRequestException.class)
    public void test27UpdateAnimal() {
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
        actual.setDescription(RandomStringUtils.random(LENGTH_DESCRIPTION + 1, true, true));

        String result = target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .post(Entity.entity(actual, MediaType.APPLICATION_JSON), String.class);

        JSONObject json = new JSONObject(result);
        String filePath = json.getString("filePath");

        assertNotNull(filePath);
    }

    /*
     * Animal.address.length > LENGTH_ADDRESS
     */
    @Test(expected = BadRequestException.class)
    public void test28UpdateAnimal() {
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
        actual.setAddress(RandomStringUtils.random(LENGTH_ADDRESS + 1, true, true));

        String result = target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .post(Entity.entity(actual, MediaType.APPLICATION_JSON), String.class);

        JSONObject json = new JSONObject(result);
        String filePath = json.getString("filePath");

        assertNotNull(filePath);
    }

    /*
     * Animal.address = null
     */
    @Test(expected = BadRequestException.class)
    public void test29UpdateAnimal() {
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
        actual.setAddress(null);

        String result = target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .post(Entity.entity(actual, MediaType.APPLICATION_JSON), String.class);

        JSONObject json = new JSONObject(result);
        String filePath = json.getString("filePath");

        assertNotNull(filePath);
    }

    /*
     * Animal.image.length > LENGTH_IMAGE
     */
    @Test(expected = BadRequestException.class)
    public void test30UpdateAnimal() {
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
        actual.setImage(RandomStringUtils.random(LENGTH_IMAGE + 1, true, true));

        String result = target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .post(Entity.entity(actual, MediaType.APPLICATION_JSON), String.class);

        JSONObject json = new JSONObject(result);
        String filePath = json.getString("filePath");

        assertNotNull(filePath);
    }

    @Test
    public void test31DeleteAnimal() {
        Response response = target(REST_SERVICE_URL)
                .path("animals/-1")
                .request()
                .delete();

        assertNotNull(response);
        assertEquals(response.getStatus(), 400);
    }

    @Test
    public void test32DeleteAnimal() {
        Response response = target(REST_SERVICE_URL)
                .path("animals/0")
                .request()
                .delete();

        assertNotNull(response);
        assertEquals(response.getStatus(), 400);
    }

    @Test
    public void test33DeleteAnimal() {
        assertNotNull(animal);
        assertNotNull(animal.getId());

        Response response = target(REST_SERVICE_URL)
                .path("animals/" + animal.getId())
                .request()
                .delete();

        assertNotNull(response);
        assertEquals(response.getStatus(), 200);
    }
}
