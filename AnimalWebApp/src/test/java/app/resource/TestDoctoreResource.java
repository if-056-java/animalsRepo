package app.resource;

import app.JNDIConfigurationForTests;
import com.animals.app.domain.Animal;
import com.animals.app.domain.AnimalMedicalHistory;
import com.animals.app.domain.AnimalsFilter;
import com.animals.app.repository.Impl.AnimalRepositoryImpl;
import com.animals.app.repository.Impl.AnimalStatusRepositoryImpl;
import com.animals.app.service.DateSerializer;
import com.animals.app.service.ValidationFilterDomainFields;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runners.MethodSorters;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.BadRequestException;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Rostyslav.Viner on 04.09.2015.
 */
@Category(IntegrationTest.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDoctoreResource extends JerseyTest {
    private static Logger LOG = LogManager.getLogger(TestDoctoreResource.class);

    private static Client client;

    private static final String LOGIN = "doctor";
    private static final String PASSWORD = "doctor";
    private static String accessToken;

    private static Long animalId;
    private static Integer rowsCount;
    private static AnimalMedicalHistory animalMedicalHistory;

    private static final String REST_SERVICE_URL = "http://localhost:9998/doctor";
    private static final String REST_LOGIN_URL = "http://localhost:9998/account";

    private final int LENGTH_DESCRIPTION = 255;

    @Override
    protected TestContainerFactory getTestContainerFactory() {
        return new GrizzlyWebTestContainerFactory();
    }

    public static class HttpSessionFactory implements Factory<HttpSession> {

        private final HttpServletRequest request;
        
        public HttpSessionFactory(Provider<HttpServletRequest> requestProvider) {
            this.request = requestProvider.get();
        }

        @Override
        public HttpSession provide() {
            return request.getSession();
        }

        @Override
        public void dispose(HttpSession t) {
        }
    }


    @Override
    protected DeploymentContext configureDeployment() {
        ResourceConfig config = new ValidationFilterDomainFields();
        config.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindFactory(HttpSessionFactory.class).to(HttpSession.class);
            }
        });
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
        accessToken = null;
        animalId = null;
        animalMedicalHistory = null;
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

        Map<String, String> jsonMap = new Gson().fromJson(result, HashMap.class);
        accessToken = jsonMap.get("accessToken");

        assertNotNull(accessToken);

        //get animal list
        AnimalsFilter animalsFilter = new AnimalsFilter(1, 10);

        List<Animal> animals = new AnimalRepositoryImpl().getAdminAnimals(animalsFilter);

        assertNotNull(animals);
        assertNotEquals(animals.size(), 0);
        //get animal id
        animalId = animals.get(0).getId();

        animalMedicalHistory = new AnimalMedicalHistory();
        animalMedicalHistory.setDate(new java.sql.Date(new java.util.Date().getTime()));
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
                .post(Entity.entity(json, MediaType.APPLICATION_JSON), Response.class);

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
                .post(Entity.entity(null, MediaType.APPLICATION_JSON), Response.class);

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
                .post(Entity.entity(json, MediaType.APPLICATION_JSON), Response.class);

        assertNotNull(response);
        assertEquals(response.getStatus(), 400);
    }

    @Test
    public void test04GetAnimalMedicalHistoryItemsCount() {
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

    @Test
    public void test05GetAnimalMedicalHistoryItems() {
        assertNotNull(accessToken);
        assertNotNull(animalId);
        assertNotNull(rowsCount);
        assertNotEquals(rowsCount, new Integer(0));

        AnimalsFilter animalsFilter = new AnimalsFilter(1, rowsCount);

        String json = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(animalsFilter);

        LOG.debug("TestName: test03GetAnimalMedicalHistoryItems - " + json);

        System.out.println(accessToken);
        System.out.println(json);

        List<AnimalMedicalHistory> animalMedicalHistories = client
                .target(REST_SERVICE_URL)
                .path("medical_history/" + animalId)
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON),
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

    @Test
    public void test06DeleteAnimalMedicalHistoryItem() {
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
