package app.resource;

import app.JNDIConfigurationForTests;
import com.animals.app.domain.Animal;
import com.animals.app.repository.Impl.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.sql.Date;

import static org.junit.Assert.assertNotNull;

/**
 * Created by oleg on 28.07.2015.
 */
@Ignore
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAnimalClient extends JNDIConfigurationForTests {

    private static Animal actual;

    @BeforeClass
    public static void runBeforeClass() {
        configureJNDIForJUnit();

        actual = new Animal();
        actual.setSex(Animal.SexType.NONE);
        actual.setType(new AnimalTypeRepositoryImpl().getAll().get(0));
        actual.setSize(Animal.SizeType.NONE);
        actual.setCites(Animal.CitesType.NONE);
        actual.setBreed(new AnimalBreedRepositoryImpl().getAll().get(0));
        actual.setTranspNumber(RandomStringUtils.random(10, true, true));
        actual.setTokenNumber(RandomStringUtils.random(10, true, true));
        actual.setDateOfRegister(new Date(System.currentTimeMillis()));
        actual.setDateOfBirth(new Date(System.currentTimeMillis()));
        actual.setDateOfSterilization(new Date(System.currentTimeMillis()));
        actual.setColor(RandomStringUtils.random(10, true, true));
        actual.setUser(new UserRepositoryImpl().getAll().get(0));
        actual.setAddress(RandomStringUtils.random(10, true, true));
        actual.setActive(true);
        actual.setImage(RandomStringUtils.random(10, true, true));
        actual.setService(new AnimalServiceRepositoryImpl().getAll().get(0));
    }

    @AfterClass
    public static void runAfterClass() {
        actual = null;
    }

    @Test
    public void test021GetAllForAdopting() {
        AnimalClient client = new AnimalClient();

        String s  = client.getAllForAdopting();

        System.out.println(s);
        assertNotNull(s);
    }

}
