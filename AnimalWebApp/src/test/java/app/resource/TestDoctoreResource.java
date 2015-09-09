package app.resource;

import com.animals.app.domain.Animal;
import com.animals.app.domain.AnimalMedicalHistory;
import com.animals.app.domain.AnimalsFilter;
import com.animals.app.repository.Impl.AnimalRepositoryImpl;
import com.animals.app.repository.Impl.AnimalStatusRepositoryImpl;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Rostyslav.Viner on 04.09.2015.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDoctoreResource {
    private static Client client;

    private static final String LOGIN = "doctor";
    private static final String PASSWORD = "doctor";
    private static String accessToken;

    private static Long animalId;
    private static Integer rowsCount;
    private static AnimalMedicalHistory animalMedicalHistory;

    private static final String REST_SERVICE_URL = "http://localhost:8080/webapi/doctor";

    @BeforeClass
    public static void runBeforeClass() {
        configureJNDIForJUnit();

        client = ClientBuilder.newClient();
        //login
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
        animalMedicalHistory.setDescription(RandomStringUtils.random(254, true, true));
        animalMedicalHistory.setAnimalId(animalId);
    }

    @AfterClass
    public static void runAfterClass() {
        client = null;
        accessToken = null;
        animalId = null;
        animalMedicalHistory = null;
    }

    @Test
    public void test01InsertAnimalMedicalHistoryItem() {
        assertNotNull(accessToken);
        assertNotNull(animalMedicalHistory);

        Response response = client
                .target(REST_SERVICE_URL)
                .path("medical_history/item")
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(animalMedicalHistory, MediaType.APPLICATION_JSON));

        assertNotNull(response);
        assertEquals(response.getStatus(), 200);
    }

    @Test
    public void test02GetAnimalMedicalHistoryItemsCount() {
        assertNotNull(accessToken);
        assertNotNull(animalId);

        String result = client
                .target(REST_SERVICE_URL)
                .path("medical_history/paginator/" + animalId)
                .request()
                .header("AccessToken", accessToken)
                .get(String.class);

        JSONObject json = new JSONObject(result);
        rowsCount = json.getInt("rowsCount");

        assertNotNull(rowsCount);
        assertNotEquals(rowsCount, new Integer(0));
    }

    @Test
    public void test03GetAnimalMedicalHistoryItems() {
        assertNotNull(accessToken);
        assertNotNull(animalId);
        assertNotNull(rowsCount);
        assertNotEquals(rowsCount, new Integer(0));

        AnimalsFilter animalsFilter = new AnimalsFilter(1, rowsCount);

        List<AnimalMedicalHistory> animalMedicalHistories = client
                .target(REST_SERVICE_URL)
                .path("medical_history/" + animalId)
                .request()
                .header("AccessToken", accessToken)
                .post(Entity.entity(animalsFilter, MediaType.APPLICATION_JSON),
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
    public void test04DeleteAnimalMedicalHistoryItem() {
        assertNotNull(animalMedicalHistory);
        assertNotNull(animalMedicalHistory.getId());

        Response response = client
                .target(REST_SERVICE_URL)
                .path("medical_history/item/" + animalMedicalHistory.getId())
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

    private static void configureJNDIForJUnit(){
        // rcarver - setup the jndi context and the datasource
        try {
            // Create initial context
            System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                    "org.apache.naming.java.javaURLContextFactory");
            System.setProperty(Context.URL_PKG_PREFIXES,
                    "org.apache.naming");
            InitialContext ic = new InitialContext();

            ic.createSubcontext("java:");
            ic.createSubcontext("java:/comp");
            ic.createSubcontext("java:/comp/env");
            ic.createSubcontext("java:/comp/env/jdbc");

            // Construct DataSource
            MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
            ds.setURL("jdbc:mysql://tym.dp.ua:3306/animals");
            ds.setUser("u_remoteuser");
            ds.setPassword("ZF008NBp");

            ic.rebind("java:/comp/env/jdbc/animals", ds);
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
    }
}
