package app.resource;

import app.JNDIConfigurationForTests;
import com.animals.app.domain.Animal;
import com.animals.app.domain.AnimalMedicalHistory;
import com.animals.app.domain.AnimalsFilter;
import com.animals.app.repository.Impl.AnimalRepositoryImpl;
import com.animals.app.repository.Impl.AnimalStatusRepositoryImpl;
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
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Rostyslav.Viner on 04.09.2015.
 */
@Category(IntegrationTest.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDoctoreResource extends ResourceTestTemplate {
    private static Logger LOG = LogManager.getLogger(TestDoctoreResource.class);

    protected static Client client;

    private static final String LOGIN = "doctor";
    private static final String PASSWORD = "doctor";
    private static String accessToken;

    private static Long animalId;
    private static Integer rowsCount;
    private static AnimalMedicalHistory animalMedicalHistory;

    private static final String REST_SERVICE_URL = BASE_URL + "doctor";

    private final int LENGTH_DESCRIPTION = 255;

    @BeforeClass
    public static void runBeforeClass() {
        JNDIConfigurationForTests.configureJNDIForJUnit();

        client = ClientBuilder.newClient();
    }

    @AfterClass
    public static void runAfterClass() {
        client = null;
        accessToken = null;
        animalId = null;
        animalMedicalHistory = null;
    }

    @Test
    public void test00Initialization() {
        accessToken = login(client, LOGIN, PASSWORD);

        assertNotNull(accessToken);

        //get animal list
        AnimalsFilter animalsFilter = new AnimalsFilter(1, 10);

        List<Animal> animals = new AnimalRepositoryImpl().getAdminAnimals(animalsFilter);

        assertNotNull(animals);
        assertNotEquals(animals.size(), 0);
        //get animal id
        animalId = animals.get(0).getId();

        animalMedicalHistory = new AnimalMedicalHistory();
        animalMedicalHistory.setDate(new Date(new java.util.Date().getTime()));
        animalMedicalHistory.setStatus(new AnimalStatusRepositoryImpl().getAll().get(0));
        animalMedicalHistory.setDescription(RandomStringUtils.random(LENGTH_DESCRIPTION - 5, true, true));
        animalMedicalHistory.setAnimalId(animalId);
    }

    @Test
    public void test01InsertAnimalMedicalHistoryItem() {
        assertNotNull(accessToken);
        assertNotNull(animalMedicalHistory);
        assertNotNull(animalMedicalHistory.getStatus());
        assertNotNull(animalMedicalHistory.getAnimalId());
        assertNotNull(animalMedicalHistory.getDate());

        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(animalMedicalHistory);

        LOG.debug("TestName: test01InsertAnimalMedicalHistoryItem - " + json);

        System.out.println(accessToken);
        System.out.println(json);
        Response response = client
                .target(REST_SERVICE_URL)
                .path("medical_history/item")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), Response.class);

        System.out.println(response.readEntity(String.class));
        assertNotNull(response);
        assertEquals(response.getStatus(), 200);
    }

    /*
     * AnimalMedicalHistory = null
     */
    @Test
    public void test02InsertAnimalMedicalHistoryItem() {
        assertNotNull(accessToken);

        Response response = client.target(REST_SERVICE_URL)
                .path("medical_history/item")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(null, MediaType.APPLICATION_JSON + ";charset=UTF-8"), Response.class);

        assertNotNull(response);
        assertEquals(response.getStatus(), 400);
    }

    /*
     * AnimalMedicalHistory.status = null
     */
    @Test
    public void test03InsertAnimalMedicalHistoryItem() {
        assertNotNull(accessToken);
        assertNotNull(animalMedicalHistory);
        assertNotNull(animalMedicalHistory.getStatus());
        assertNotNull(animalMedicalHistory.getAnimalId());
        assertNotNull(animalMedicalHistory.getDate());

        AnimalMedicalHistory actual = SerializationUtils.clone(animalMedicalHistory);
        actual.setStatus(null);

        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test03InsertAnimalMedicalHistoryItem - " + json);

        Response response = client.target(REST_SERVICE_URL)
                .path("medical_history/item")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), Response.class);

        assertNotNull(response);
        assertEquals(response.getStatus(), 400);
    }

    /*
     * AnimalMedicalHistory.status.id = null
     */
    @Test
    public void test04InsertAnimalMedicalHistoryItem() {
        assertNotNull(accessToken);
        assertNotNull(animalMedicalHistory);
        assertNotNull(animalMedicalHistory.getStatus());
        assertNotNull(animalMedicalHistory.getAnimalId());
        assertNotNull(animalMedicalHistory.getDate());

        AnimalMedicalHistory actual = SerializationUtils.clone(animalMedicalHistory);
        actual.getStatus().setId(null);

        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test04InsertAnimalMedicalHistoryItem - " + json);

        Response response = client.target(REST_SERVICE_URL)
                .path("medical_history/item")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), Response.class);

        assertNotNull(response);
        assertEquals(response.getStatus(), 400);
    }

    /*
     * AnimalMedicalHistory.status.id = 0
     */
    @Test
    public void test05InsertAnimalMedicalHistoryItem() {
        assertNotNull(accessToken);
        assertNotNull(animalMedicalHistory);
        assertNotNull(animalMedicalHistory.getStatus());
        assertNotNull(animalMedicalHistory.getAnimalId());
        assertNotNull(animalMedicalHistory.getDate());

        AnimalMedicalHistory actual = SerializationUtils.clone(animalMedicalHistory);
        actual.getStatus().setId(new Long(0));

        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test05InsertAnimalMedicalHistoryItem - " + json);
        System.out.println(json);
        Response response = client.target(REST_SERVICE_URL)
                .path("medical_history/item")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), Response.class);

        assertNotNull(response);
        assertEquals(response.getStatus(), 400);
    }

    /*
     * AnimalMedicalHistory.status.id = -1
     */
    @Test
    public void test06InsertAnimalMedicalHistoryItem() {
        assertNotNull(accessToken);
        assertNotNull(animalMedicalHistory);
        assertNotNull(animalMedicalHistory.getStatus());
        assertNotNull(animalMedicalHistory.getAnimalId());
        assertNotNull(animalMedicalHistory.getDate());

        AnimalMedicalHistory actual = SerializationUtils.clone(animalMedicalHistory);
        actual.getStatus().setId(new Long(-1));

        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test06InsertAnimalMedicalHistoryItem - " + json);

        Response response = client.target(REST_SERVICE_URL)
                .path("medical_history/item")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), Response.class);

        assertNotNull(response);
        assertEquals(response.getStatus(), 400);
    }

    /*
     * AnimalMedicalHistory.animalId = null
     */
    @Test
    public void test07InsertAnimalMedicalHistoryItem() {
        assertNotNull(accessToken);
        assertNotNull(animalMedicalHistory);
        assertNotNull(animalMedicalHistory.getStatus());
        assertNotNull(animalMedicalHistory.getAnimalId());
        assertNotNull(animalMedicalHistory.getDate());

        AnimalMedicalHistory actual = SerializationUtils.clone(animalMedicalHistory);
        actual.setAnimalId(null);

        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test07InsertAnimalMedicalHistoryItem - " + json);

        Response response = client.target(REST_SERVICE_URL)
                .path("medical_history/item")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), Response.class);

        assertNotNull(response);
        assertEquals(response.getStatus(), 400);
    }

    /*
     * AnimalMedicalHistory.animalId = 0
     */
    @Test
    public void test08InsertAnimalMedicalHistoryItem() {
        assertNotNull(accessToken);
        assertNotNull(animalMedicalHistory);
        assertNotNull(animalMedicalHistory.getStatus());
        assertNotNull(animalMedicalHistory.getAnimalId());
        assertNotNull(animalMedicalHistory.getDate());

        AnimalMedicalHistory actual = SerializationUtils.clone(animalMedicalHistory);
        actual.setAnimalId(new Long(0));

        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test08InsertAnimalMedicalHistoryItem - " + json);

        Response response = client.target(REST_SERVICE_URL)
                .path("medical_history/item")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), Response.class);

        assertNotNull(response);
        assertEquals(response.getStatus(), 400);
    }

    /*
     * AnimalMedicalHistory.animalId = -1
     */
    @Test
    public void test09InsertAnimalMedicalHistoryItem() {
        assertNotNull(accessToken);
        assertNotNull(animalMedicalHistory);
        assertNotNull(animalMedicalHistory.getStatus());
        assertNotNull(animalMedicalHistory.getAnimalId());
        assertNotNull(animalMedicalHistory.getDate());

        AnimalMedicalHistory actual = SerializationUtils.clone(animalMedicalHistory);
        actual.setAnimalId(new Long(-1));

        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test09InsertAnimalMedicalHistoryItem - " + json);

        Response response = client.target(REST_SERVICE_URL)
                .path("medical_history/item")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), Response.class);

        assertNotNull(response);
        assertEquals(response.getStatus(), 400);
    }

    /*
     * AnimalMedicalHistory.date = null
     */
    @Test
    public void test10InsertAnimalMedicalHistoryItem() {
        assertNotNull(accessToken);
        assertNotNull(animalMedicalHistory);
        assertNotNull(animalMedicalHistory.getStatus());
        assertNotNull(animalMedicalHistory.getAnimalId());
        assertNotNull(animalMedicalHistory.getDate());

        AnimalMedicalHistory actual = SerializationUtils.clone(animalMedicalHistory);
        actual.setDate(null);

        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(actual);

        LOG.debug("TestName: test10InsertAnimalMedicalHistoryItem - " + json);

        Response response = client.target(REST_SERVICE_URL)
                .path("medical_history/item")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), Response.class);

        assertNotNull(response);
        assertEquals(response.getStatus(), 400);
    }

    /*
     * AnimalMedicalHistory.date = wrong format
     */
    @Test
    public void test11InsertAnimalMedicalHistoryItem() {
        assertNotNull(accessToken);
        assertNotNull(animalMedicalHistory);
        assertNotNull(animalMedicalHistory.getStatus());
        assertNotNull(animalMedicalHistory.getAnimalId());
        assertNotNull(animalMedicalHistory.getDate());

        String json = new GsonBuilder()
                .create()
                .toJson(animalMedicalHistory);

        LOG.debug("TestName: test11InsertAnimalMedicalHistoryItem - " + json);

        Response response = client.target(REST_SERVICE_URL)
                .path("medical_history/item")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), Response.class);

        assertNotNull(response);
        assertEquals(response.getStatus(), 400);
    }

    @Test
     public void test12GetAnimalMedicalHistoryItemsCount() {
        assertNotNull(accessToken);
        assertNotNull(animalId);

        String result = client
                .target(REST_SERVICE_URL)
                .path("medical_history/paginator/" + animalId)
                .request()
                .header("AccessToken", accessToken)
                .get(String.class);

        rowsCount = new Gson().fromJson(result, JsonObject.class).get("rowsCount").getAsInt();

        assertNotNull(rowsCount);
        assertNotEquals(rowsCount, new Integer(0));
    }

    /*
     * animalId = 0
     */
    @Test(expected = BadRequestException.class)
    public void test13GetAnimalMedicalHistoryItemsCount() {
        assertNotNull(accessToken);

        client.target(REST_SERVICE_URL)
                .path("medical_history/paginator/0")
                .request()
                .header("AccessToken", accessToken)
                .get(String.class);
    }

    /*
     * animalId = -1
     */
    @Test(expected = BadRequestException.class)
    public void test14GetAnimalMedicalHistoryItemsCount() {
        assertNotNull(accessToken);

        client.target(REST_SERVICE_URL)
                .path("medical_history/paginator/-1")
                .request()
                .header("AccessToken", accessToken)
                .get(String.class);
    }

    @Test
    public void test15GetAnimalMedicalHistoryItems() {
        assertNotNull(accessToken);
        assertNotNull(animalId);
        assertNotNull(rowsCount);
        assertNotEquals(rowsCount, new Integer(0));

        AnimalsFilter animalsFilter = new AnimalsFilter(1, rowsCount);

        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(animalsFilter);

        LOG.debug("TestName: test15GetAnimalMedicalHistoryItems - " + json);

        List<AnimalMedicalHistory> animalMedicalHistories = client
                .target(REST_SERVICE_URL)
                .path("medical_history/" + animalId)
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"),
                        new GenericType<List<AnimalMedicalHistory>>() {});

        assertNotNull(animalMedicalHistories);
        assertNotEquals(animalMedicalHistories.size(), 0);

        for (AnimalMedicalHistory temp: animalMedicalHistories) {
            if (animalMedicalHistory.getDescription().equals(temp.getDescription())) {
                animalMedicalHistory.setId(temp.getId());
                break;
            }
        }
    }

    /*
     * animalId = 0
     */
    @Test(expected = BadRequestException.class)
    public void test16GetAnimalMedicalHistoryItems() {
        assertNotNull(accessToken);
        assertNotNull(animalId);
        assertNotNull(rowsCount);
        assertNotEquals(rowsCount, new Integer(0));

        AnimalsFilter animalsFilter = new AnimalsFilter(1, rowsCount);

        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(animalsFilter);

        LOG.debug("TestName: test16GetAnimalMedicalHistoryItems - " + json);

        client.target(REST_SERVICE_URL)
                .path("medical_history/0")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"),
                        new GenericType<List<AnimalMedicalHistory>>() {});
    }

    /*
     * animalId = -1
     */
    @Test(expected = BadRequestException.class)
    public void test17GetAnimalMedicalHistoryItems() {
        assertNotNull(accessToken);
        assertNotNull(animalId);
        assertNotNull(rowsCount);
        assertNotEquals(rowsCount, new Integer(0));

        AnimalsFilter animalsFilter = new AnimalsFilter(1, rowsCount);

        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(animalsFilter);

        LOG.debug("TestName: test17GetAnimalMedicalHistoryItems - " + json);

        client.target(REST_SERVICE_URL)
                .path("medical_history/-1")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"),
                        new GenericType<List<AnimalMedicalHistory>>() {});
    }

    /*
     * no animalId
     */
    @Test(expected = NotFoundException.class)
    public void test18GetAnimalMedicalHistoryItems() {
        assertNotNull(accessToken);
        assertNotNull(animalId);
        assertNotNull(rowsCount);
        assertNotEquals(rowsCount, new Integer(0));

        AnimalsFilter animalsFilter = new AnimalsFilter(1, rowsCount);

        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(animalsFilter);

        LOG.debug("TestName: test18GetAnimalMedicalHistoryItems - " + json);

        client.target(REST_SERVICE_URL)
                .path("medical_history/")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"),
                        new GenericType<List<AnimalMedicalHistory>>() {});
    }

    @Test
    public void test25DeleteAnimalMedicalHistoryItem() {
        assertNotNull(animalMedicalHistory);
        assertNotNull(animalMedicalHistory.getId());

        Response response = client
                .target(REST_SERVICE_URL)
                .path("medical_history/item/" + animalMedicalHistory.getId())
                .request()
                .header("AccessToken", accessToken)
                .delete(Response.class);

        assertNotNull(response);
        assertEquals(response.getStatus(), 200);
    }
}
