package app.resource;

import com.animals.app.domain.Animal;
import com.animals.app.domain.AnimalsFilter;
import com.animals.app.repository.AnimalRepository;
import com.animals.app.repository.Impl.AnimalRepositoryImpl;
import com.animals.app.repository.Impl.AnimalServiceRepositoryImpl;
import com.animals.app.repository.Impl.AnimalTypeRepositoryImpl;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Rostyslav.Viner on 03.09.2015.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAdminResource {
    private static Client client;

    private static final String LOGIN = "root";
    private static final String PASSWORD = "root";
    private static String accessToken;
    private static Animal animal;

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
        client = null;
    }

    @Test
    public void test01GetAnimal() {
        assertNotNull(accessToken);
        assertNotNull(animal);
        assertNotNull(animal.getId());

        Animal expected = client
                .target(REST_SERVICE_URL)
                .path("animals/" + animal.getId())
                .request()
                .header("AccessToken", accessToken)
                .get(Animal.class);

        assertNotNull(expected);
    }

    @Test(expected = BadRequestException.class)
    public void test02GetAnimal() {
        assertNotNull(accessToken);

        client.target(REST_SERVICE_URL)
                .path("animals/-1")
                .request()
                .header("AccessToken", accessToken)
                .get(Animal.class);
    }

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
                .post(null, Animal.class);
    }

    /*
     * Animal.id = null
     */
    @Test(expected = BadRequestException.class)
    public void test05UpdateAnimal() {
        assertNotNull(accessToken);

        client.target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(new Animal(), MediaType.APPLICATION_JSON), Animal.class);
    }

    /*
     * Animal.id = -1
     */
    @Test(expected = BadRequestException.class)
    public void test06UpdateAnimal() {
        assertNotNull(accessToken);

        Animal actual = new Animal();
        actual.setId(new Long(-1));

        client.target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(actual, MediaType.APPLICATION_JSON), Animal.class);
    }

    /*
     * Animal.id = 0
     */
    @Test(expected = BadRequestException.class)
    public void test07UpdateAnimal() {
        assertNotNull(accessToken);

        Animal actual = new Animal();
        actual.setId(new Long(0));

        client.target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(actual, MediaType.APPLICATION_JSON), Animal.class);
    }

    @Test
    public void test08UpdateAnimal() {
        assertNotNull(accessToken);
        assertNotNull(animal);
        assertNotNull(animal.getId());
        assertNotNull(animal.getSex());

        Animal actual = SerializationUtils.clone(animal);
        System.out.println(animal);
        System.out.println(actual);
        actual.getType().setId(new Long(-1));

        String result = client
                .target(REST_SERVICE_URL)
                .path("animals/editor")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(actual, MediaType.APPLICATION_JSON), String.class);

        JSONObject json = new JSONObject(result);
        String filePath = json.getString("filePath");

        System.out.println(filePath);
    }

    //--------------------------------------------------
    @Test
    public void test09DeleteAnimal() {
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
    public void test10DeleteAnimal() {
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
    public void test11DeleteAnimal() {
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
