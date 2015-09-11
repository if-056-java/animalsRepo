package app.resource;

import app.JNDIConfigurationForTests;
import com.animals.app.domain.AnimalBreed;
import com.animals.app.domain.AnimalService;
import com.animals.app.domain.AnimalStatus;
import com.animals.app.domain.AnimalType;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by root on 03.09.2015.
 */
@Ignore
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAnimalResource extends JNDIConfigurationForTests{
    private static Client client;

    private static AnimalType animalType;

    private static final String REST_SERVICE_URL = "http://localhost:8080/webapi/animals";

    @BeforeClass
    public static void runBeforeClass() {
        client = ClientBuilder.newClient();
    }

    @AfterClass
    public static void runAfterClass() {
        client = null;
    }

    @Test
    public void test01GetAnimalTypes() {

        List<AnimalType> animalTypes = client
                .target(REST_SERVICE_URL)
                .path("/animal_types")
                .request()
                .get(new GenericType<List<AnimalType>>() {});

        assertNotNull(animalTypes);
        assertNotEquals(animalTypes.size(), 0);

        animalType = animalTypes.get(0);
    }

    @Test
    public void test02GetAnimalBreeds() {
        assertNotNull(animalType);
        assertNotNull(animalType.getId());

        List<AnimalBreed> animalBreeds = client
                .target(REST_SERVICE_URL)
                .path("/animal_breeds/" + animalType.getId())
                .request()
                .get(new GenericType<List<AnimalBreed>>() {});

        assertNotNull(animalBreeds);
        assertNotEquals(animalBreeds.size(), 0);
    }

    @Test
    public void test03GetAnimalServices() {

        List<AnimalService> animalServices = client
                .target(REST_SERVICE_URL)
                .path("/animal_services")
                .request()
                .get(new GenericType<List<AnimalService>>() {});

        assertNotNull(animalServices);
        assertNotEquals(animalServices.size(), 0);
    }

    @Test
    public void test04GetAnimalMedicalHistoryTypes() {

        List<AnimalStatus> animalStatuses = client
                .target(REST_SERVICE_URL)
                .path("/medical_history/types")
                .request()
                .get(new GenericType<List<AnimalStatus>>() {});

        assertNotNull(animalStatuses);
        assertNotEquals(animalStatuses.size(), 0);
    }
}
