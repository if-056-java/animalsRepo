/*
 * For admin get animals request AnimalFilter.page and AnimalFilter.limit must be set.
 */
package app.resource;

import app.JNDIConfigurationForTests;
import com.animals.app.domain.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runners.MethodSorters;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Rostyslav.Viner on 08.09.2015.
 */
@Category(IntegrationTest.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAdminResourceGetAnimals extends ResourceTestTemplate {
    private static Logger LOG = LogManager.getLogger(TestAdminResourceGetAnimals.class);

    private static Client client;

    private static final String LOGIN = "root";
    private static final String PASSWORD = "root";
    private static String accessToken;

    private static final String REST_SERVICE_URL = BASE_URL + "admin";

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
    public void test00Initialization() {
        accessToken = login(client, LOGIN, PASSWORD);

        assertNotNull(accessToken);
    }

    @Test
    public void test01GetAnimals() {
        assertNotNull(accessToken);

        AnimalsFilter animalsFilter = new AnimalsFilter(1, 5);
        String json = new GsonBuilder()
                .create()
                .toJson(animalsFilter);

        LOG.debug("TestName: test01GetAnimals - " + json);

        List<Animal> animals = client
                .target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), new GenericType<List<Animal>>() {});

        assertNotNull(animals);
        assertNotEquals(animals.size(), 0);
    }

    /*
     * AnimalsFilter = null
     */
    @Test(expected = BadRequestException.class)
    public void test02GetAnimals() {
        assertNotNull(accessToken);

        client.target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(null, new GenericType<List<Animal>>() {});
    }

    /*
     * AnimalsFilter.page = null
     */
    @Test(expected = BadRequestException.class)
    public void test03GetAnimals() {
        assertNotNull(accessToken);

        AnimalsFilter animalsFilter = new AnimalsFilter();
        animalsFilter.setLimit(5);
        String json = new GsonBuilder()
                .create()
                .toJson(animalsFilter);

        LOG.debug("TestName: test03GetAnimals - " + json);

        client.target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), new GenericType<List<Animal>>() {});
    }

    /*
     * AnimalsFilter.limit = null
     */
    @Test(expected = BadRequestException.class)
    public void test04GetAnimals() {
        assertNotNull(accessToken);

        AnimalsFilter animalsFilter = new AnimalsFilter();
        animalsFilter.setPage(1);
        String json = new GsonBuilder()
                .create()
                .toJson(animalsFilter);

        LOG.debug("TestName: test04GetAnimals - " + json);

        client.target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), new GenericType<List<Animal>>() {});
    }

    @Test(expected = BadRequestException.class)
    public void test05GetAnimals() {
        assertNotNull(accessToken);

        AnimalsFilter animalsFilter = new AnimalsFilter();
        animalsFilter.setPage(-1);
        animalsFilter.setLimit(1);
        String json = new GsonBuilder()
                .create()
                .toJson(animalsFilter);

        LOG.debug("TestName: test05GetAnimals - " + json);

        client.target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), new GenericType<List<Animal>>() {});
    }

    @Test(expected = BadRequestException.class)
    public void test06GetAnimals() {
        assertNotNull(accessToken);

        AnimalsFilter animalsFilter = new AnimalsFilter();
        animalsFilter.setPage(1);
        animalsFilter.setLimit(-1);
        String json = new GsonBuilder()
                .create()
                .toJson(animalsFilter);

        LOG.debug("TestName: test06GetAnimals - " + json);

        client.target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), new GenericType<List<Animal>>() {});
    }

    @Test(expected = BadRequestException.class)
    public void test07GetAnimals() {
        assertNotNull(accessToken);

        AnimalsFilter animalsFilter = new AnimalsFilter();
        animalsFilter.setPage(0);
        animalsFilter.setLimit(1);
        String json = new GsonBuilder()
                .create()
                .toJson(animalsFilter);

        LOG.debug("TestName: test07GetAnimals - " + json);

        client.target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), new GenericType<List<Animal>>() {});
    }

    @Test(expected = BadRequestException.class)
    public void test08GetAnimals() {
        assertNotNull(accessToken);

        AnimalsFilter animalsFilter = new AnimalsFilter();
        animalsFilter.setPage(1);
        animalsFilter.setLimit(0);
        String json = new GsonBuilder()
                .create()
                .toJson(animalsFilter);

        LOG.debug("TestName: test08GetAnimals - " + json);

        client.target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), new GenericType<List<Animal>>() {});
    }

    /*
     * AnimalFilter.animal.type.id = null
     */
    @Test
    public void test09GetAnimals() {
        assertNotNull(accessToken);

        AnimalsFilter animalsFilter = new AnimalsFilter(1, 5);
        Animal animal = new Animal();
        animal.setType(new AnimalType());
        animalsFilter.setAnimal(animal);
        String json = new GsonBuilder()
                .create()
                .toJson(animalsFilter);

        LOG.debug("TestName: test09GetAnimals - " + json);

        List<Animal> animals = client
                .target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), new GenericType<List<Animal>>() {});

        assertNotNull(animals);
        assertNotEquals(animals.size(), 0);
    }

    /*
     * AnimalFilter.animal.type.id = -1
     */
    @Test
    public void test10GetAnimals() {
        assertNotNull(accessToken);

        AnimalsFilter animalsFilter = new AnimalsFilter(1, 5);
        Animal animal = new Animal();

        AnimalType animalType = new AnimalType();
        animalType.setId(new Long(-1));

        animal.setType(animalType);
        animalsFilter.setAnimal(animal);

        String json = new GsonBuilder()
                .create()
                .toJson(animalsFilter);

        LOG.debug("TestName: test10GetAnimals - " + json);

        List<Animal> animals = client
                .target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), new GenericType<List<Animal>>() {});

        assertNotNull(animals);
        assertEquals(animals.size(), 0);
    }

    /*
     * AnimalFilter.animal.type.id = 0
     */
    @Test
    public void test11GetAnimals() {
        assertNotNull(accessToken);

        AnimalsFilter animalsFilter = new AnimalsFilter(1, 5);
        Animal animal = new Animal();

        AnimalType animalType = new AnimalType();
        animalType.setId(new Long(0));

        animal.setType(animalType);
        animalsFilter.setAnimal(animal);

        String json = new GsonBuilder()
                .create()
                .toJson(animalsFilter);

        LOG.debug("TestName: test10GetAnimals - " + json);

        List<Animal> animals = client
                .target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), new GenericType<List<Animal>>() {});

        assertNotNull(animals);
        assertEquals(animals.size(), 0);
    }

    /*
     * AnimalFilter.animal.breed.id = null
     */
    @Test
    public void test12GetAnimals() {
        assertNotNull(accessToken);

        AnimalsFilter animalsFilter = new AnimalsFilter(1, 5);
        Animal animal = new Animal();
        animal.setBreed(new AnimalBreed());
        animalsFilter.setAnimal(animal);

        String json = new GsonBuilder()
                .create()
                .toJson(animalsFilter);

        LOG.debug("TestName: test12GetAnimals - " + json);

        List<Animal> animals = client
                .target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), new GenericType<List<Animal>>() {});

        assertNotNull(animals);
        assertNotEquals(animals.size(), 0);
    }

    /*
     * AnimalFilter.animal.breed.id = -1
     */
    @Test
    public void test13GetAnimals() {
        assertNotNull(accessToken);

        AnimalsFilter animalsFilter = new AnimalsFilter(1, 5);
        Animal animal = new Animal();

        AnimalBreed animalBreed = new AnimalBreed();
        animalBreed.setId(new Long(-1));

        animal.setBreed(animalBreed);
        animalsFilter.setAnimal(animal);

        String json = new GsonBuilder()
                .create()
                .toJson(animalsFilter);

        LOG.debug("TestName: test13GetAnimals - " + json);

        List<Animal> animals = client
                .target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), new GenericType<List<Animal>>() {});

        assertNotNull(animals);
        assertEquals(animals.size(), 0);
    }

    /*
     * AnimalFilter.animal.breed.id = 0
     */
    @Test
    public void test14GetAnimals() {
        assertNotNull(accessToken);

        AnimalsFilter animalsFilter = new AnimalsFilter(1, 5);
        Animal animal = new Animal();

        AnimalBreed animalBreed = new AnimalBreed();
        animalBreed.setId(new Long(0));

        animal.setBreed(animalBreed);
        animalsFilter.setAnimal(animal);

        String json = new GsonBuilder()
                .create()
                .toJson(animalsFilter);

        LOG.debug("TestName: test14GetAnimals - " + json);

        List<Animal> animals = client
                .target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), new GenericType<List<Animal>>() {});

        assertNotNull(animals);
        assertEquals(animals.size(), 0);
    }

    /*
     * AnimalFilter.animal.service.id = null
     */
    @Test
    public void test15GetAnimals() {
        assertNotNull(accessToken);

        AnimalsFilter animalsFilter = new AnimalsFilter(1, 5);
        Animal animal = new Animal();
        animal.setService(new AnimalService());
        animalsFilter.setAnimal(animal);

        String json = new GsonBuilder()
                .create()
                .toJson(animalsFilter);

        LOG.debug("TestName: test15GetAnimals - " + json);

        List<Animal> animals = client
                .target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), new GenericType<List<Animal>>() {});

        assertNotNull(animals);
        assertNotEquals(animals.size(), 0);
    }

    /*
     * AnimalFilter.animal.service.id = -1
     */
    @Test
    public void test16GetAnimals() {
        assertNotNull(accessToken);

        AnimalsFilter animalsFilter = new AnimalsFilter(1, 5);
        Animal animal = new Animal();

        AnimalService animalService = new AnimalService();
        animalService.setId(new Long(-1));

        animal.setService(animalService);
        animalsFilter.setAnimal(animal);

        String json = new GsonBuilder()
                .create()
                .toJson(animalsFilter);

        LOG.debug("TestName: test16GetAnimals - " + json);

        List<Animal> animals = client
                .target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), new GenericType<List<Animal>>() {});

        assertNotNull(animals);
        assertEquals(animals.size(), 0);
    }

    /*
     * AnimalFilter.animal.service.id = 0
     */
    @Test
    public void test17GetAnimals() {
        assertNotNull(accessToken);

        AnimalsFilter animalsFilter = new AnimalsFilter(1, 5);
        Animal animal = new Animal();

        AnimalService animalService = new AnimalService();
        animalService.setId(new Long(0));

        animal.setService(animalService);
        animalsFilter.setAnimal(animal);

        String json = new GsonBuilder()
                .create()
                .toJson(animalsFilter);

        LOG.debug("TestName: test17GetAnimals - " + json);

        List<Animal> animals = client
                .target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), new GenericType<List<Animal>>() {});

        assertNotNull(animals);
        assertEquals(animals.size(), 0);
    }

    /*
     * Animal.transpNumber max length is 15
     */
    @Test
    public void test18GetAnimals() {
        assertNotNull(accessToken);

        AnimalsFilter animalsFilter = new AnimalsFilter(1, 5);
        Animal animal = new Animal();

        animal.setTranspNumber(RandomStringUtils.random(20, true, true));
        animalsFilter.setAnimal(animal);

        String json = new GsonBuilder()
                .create()
                .toJson(animalsFilter);

        LOG.debug("TestName: test18GetAnimals - " + json);

        List<Animal> animals = client
                .target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), new GenericType<List<Animal>>() {});

        assertNotNull(animals);
        assertEquals(animals.size(), 0);
    }

    @Test
    public void test19GetAnimalsPaginator() {
        assertNotNull(accessToken);

        AnimalsFilter animalsFilter = new AnimalsFilter(1, 5);

        String json = new GsonBuilder()
                .create()
                .toJson(animalsFilter);

        LOG.debug("TestName: test19GetAnimals - " + json);

        String result = client
                .target(REST_SERVICE_URL)
                .path("animals/paginator")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), String.class);

        LOG.debug("TestName: test19GetAnimals - " + result);

        Long rowCount = new Gson().fromJson(result, JsonObject.class).get("rowsCount").getAsLong();

        assertNotNull(rowCount);
    }

    @Test(expected = BadRequestException.class)
    public void test20GetAnimalsPaginator() {
        assertNotNull(accessToken);

        client.target(REST_SERVICE_URL)
                .path("animals/paginator")
                .request()
                .header("AccessToken", accessToken)
                .post(null, String.class);
    }
}
