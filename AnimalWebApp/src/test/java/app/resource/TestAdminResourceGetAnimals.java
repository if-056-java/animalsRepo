/*
 * For admin get animals request AnimalFilter.page and AnimalFilter.limit must be set.
 */
package app.resource;

import com.animals.app.domain.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

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
public class TestAdminResourceGetAnimals {
    private static Client client;

    private static final String LOGIN = "root";
    private static final String PASSWORD = "root";
    private static String accessToken;

    private static final String REST_SERVICE_URL = "http://localhost:8080/webapi/admin";

    @BeforeClass
    public static void runBeforeClass() {
        client = ClientBuilder.newClient();

        String passwordMd5 = getMd5(PASSWORD);
        String credentials = "Basic " + Base64.encodeBase64String((LOGIN + ':' + passwordMd5).getBytes());

        String result = client
                .target("http://localhost:8080/webapi/account")
                .path("/login/OFF")
                .request()
                .header("Authorization", credentials)
                .post(null, String.class);

        JSONObject json = new JSONObject(result);
        accessToken = json.getString("accessToken");
    }

    @AfterClass
    public static void runAfterClass() {
        client = null;
    }

    @Test
    public void test01GetAnimals() {
        assertNotNull(accessToken);

        AnimalsFilter animalsFilter = new AnimalsFilter(1, 5);

        List<Animal> animals = client
                .target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(animalsFilter, MediaType.APPLICATION_JSON), new GenericType<List<Animal>>() {});

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
                .post(null, new GenericType<List<Animal>>() {
                });
    }

    /*
     * AnimalsFilter.page = null
     */
    @Test(expected = BadRequestException.class)
    public void test03GetAnimals() {
        assertNotNull(accessToken);

        AnimalsFilter animalsFilter = new AnimalsFilter();
        animalsFilter.setLimit(5);

        client.target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(animalsFilter, MediaType.APPLICATION_JSON), new GenericType<List<Animal>>() {
                });
    }

    /*
     * AnimalsFilter.limit = null
     */
    @Test(expected = BadRequestException.class)
    public void test04GetAnimals() {
        assertNotNull(accessToken);

        AnimalsFilter animalsFilter = new AnimalsFilter();
        animalsFilter.setPage(1);

        client.target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(animalsFilter, MediaType.APPLICATION_JSON), new GenericType<List<Animal>>() {
                });
    }

    @Test(expected = BadRequestException.class)
    public void test05GetAnimals() {
        assertNotNull(accessToken);

        AnimalsFilter animalsFilter = new AnimalsFilter();
        animalsFilter.setPage(-1);
        animalsFilter.setLimit(1);

        client.target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(animalsFilter, MediaType.APPLICATION_JSON), new GenericType<List<Animal>>() {});
    }

    @Test(expected = BadRequestException.class)
    public void test06GetAnimals() {
        assertNotNull(accessToken);

        AnimalsFilter animalsFilter = new AnimalsFilter();
        animalsFilter.setPage(1);
        animalsFilter.setLimit(-1);

        client.target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(animalsFilter, MediaType.APPLICATION_JSON), new GenericType<List<Animal>>() {});
    }

    @Test(expected = BadRequestException.class)
    public void test07GetAnimals() {
        assertNotNull(accessToken);

        AnimalsFilter animalsFilter = new AnimalsFilter();
        animalsFilter.setPage(0);
        animalsFilter.setLimit(1);

        client.target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(animalsFilter, MediaType.APPLICATION_JSON), new GenericType<List<Animal>>() {});
    }

    @Test(expected = BadRequestException.class)
    public void test08GetAnimals() {
        assertNotNull(accessToken);

        AnimalsFilter animalsFilter = new AnimalsFilter();
        animalsFilter.setPage(1);
        animalsFilter.setLimit(0);

        client.target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(animalsFilter, MediaType.APPLICATION_JSON), new GenericType<List<Animal>>() {
                });
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

        List<Animal> animals = client
                .target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(animalsFilter, MediaType.APPLICATION_JSON), new GenericType<List<Animal>>() {});

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

        List<Animal> animals = client
                .target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(animalsFilter, MediaType.APPLICATION_JSON), new GenericType<List<Animal>>() {});

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

        List<Animal> animals = client
                .target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(animalsFilter, MediaType.APPLICATION_JSON), new GenericType<List<Animal>>() {});

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

        List<Animal> animals = client
                .target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(animalsFilter, MediaType.APPLICATION_JSON), new GenericType<List<Animal>>() {});

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

        List<Animal> animals = client
                .target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(animalsFilter, MediaType.APPLICATION_JSON), new GenericType<List<Animal>>() {});

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

        List<Animal> animals = client
                .target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(animalsFilter, MediaType.APPLICATION_JSON), new GenericType<List<Animal>>() {});

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

        List<Animal> animals = client
                .target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(animalsFilter, MediaType.APPLICATION_JSON), new GenericType<List<Animal>>() {});

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

        List<Animal> animals = client
                .target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(animalsFilter, MediaType.APPLICATION_JSON), new GenericType<List<Animal>>() {});

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

        List<Animal> animals = client
                .target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(animalsFilter, MediaType.APPLICATION_JSON), new GenericType<List<Animal>>() {});

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

        List<Animal> animals = client
                .target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(animalsFilter, MediaType.APPLICATION_JSON), new GenericType<List<Animal>>() {});

        assertNotNull(animals);
        assertEquals(animals.size(), 0);
    }

    @Test
    public void test19GetAnimalsPaginator() {
        assertNotNull(accessToken);

        AnimalsFilter animalsFilter = new AnimalsFilter(1, 5);

        String rowCount = client
                .target(REST_SERVICE_URL)
                .path("animals/paginator")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(animalsFilter, MediaType.APPLICATION_JSON), String.class);

        assertNotNull(rowCount);

        JSONObject json = new JSONObject(rowCount);
        Long count = json.getLong("rowsCount");

        assertNotNull(count);
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
