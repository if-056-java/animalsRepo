package app.resource;

import com.animals.app.domain.Animal;
import com.animals.app.domain.AnimalsFilter;
import org.apache.commons.codec.binary.Base64;
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
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
    }

    @AfterClass
    public static void runAfterClass() {
        client = null;
    }

    @Test
    public void test01GetAnimals() {
        assertNotNull(accessToken);

        AnimalsFilter animalsFilter = new AnimalsFilter(1, 10);

        List<Animal> animals = client
                .target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(animalsFilter, MediaType.APPLICATION_JSON), new GenericType<List<Animal>>() {});

        assertNotNull(animals);
        assertNotEquals(animals.size(), 0);

        animal = animals.get(0);
    }

    @Test(expected = BadRequestException.class)
    public void test02GetAnimals() {
        assertNotNull(accessToken);

        List<Animal> animals = client
                .target(REST_SERVICE_URL)
                .path("animals")
                .request()
                .header("AccessToken", accessToken)
                .post(null, new GenericType<List<Animal>>() {});

        assertNotNull(animals);
        assertNotEquals(animals.size(), 0);
    }

    @Test
    public void test03GetAnimalsPaginator() {
        assertNotNull(accessToken);

        AnimalsFilter animalsFilter = new AnimalsFilter();

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
    public void test04GetAnimalsPaginator() {
        assertNotNull(accessToken);

        String rowCount = client
                .target(REST_SERVICE_URL)
                .path("animals/paginator")
                .request()
                .header("AccessToken", accessToken)
                .post(null, String.class);

        assertNull(rowCount);
    }

    @Test
    public void test05GetAnimal() {
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
    public void test06GetAnimal() {
        assertNotNull(accessToken);

        Animal expected = client
                .target(REST_SERVICE_URL)
                .path("animals/-1")
                .request()
                .header("AccessToken", accessToken)
                .get(Animal.class);

        assertNull(expected);
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
