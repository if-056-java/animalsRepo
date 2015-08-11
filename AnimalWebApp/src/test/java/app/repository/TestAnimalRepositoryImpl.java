package app.repository;

import com.animals.app.domain.Animal;
import com.animals.app.domain.AnimalType;
import com.animals.app.domain.AnimalsFilter;
import com.animals.app.repository.Impl.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.sql.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Rostyslav.Viner on 24.07.2015.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAnimalRepositoryImpl {
    private static final Logger LOG = LogManager.getLogger(TestAnimalRepositoryImpl.class);

    private static AnimalRepositoryImpl animalRepositoryImpl;
    private static Animal actual;

    @BeforeClass
    public static void runBeforeClass() {
        animalRepositoryImpl = new AnimalRepositoryImpl();

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
        animalRepositoryImpl = null;
    }

    @Test
    public void test01Insert() {
        assertNull(actual.getId());

        animalRepositoryImpl.insert(actual);

        assertNotNull(actual.getId());
    }

    @Test
    public void test02GetAdminAnimals() {
        List<Animal> expected = animalRepositoryImpl.getAdminAnimals(new AnimalsFilter(1,10));

        assertNotNull(expected);
    }

    @Test
    public void test03GetAdminAnimalsPaginator() {
        AnimalsFilter animalsFilter = new AnimalsFilter(1, 10);
        Animal animal = new Animal();
        animal.setSex(Animal.SexType.FEMALE);
        animal.setSize(Animal.SizeType.SMALL);
        animalsFilter.setAnimal(animal);
        long count = animalRepositoryImpl.getAdminAnimalsPaginator(animalsFilter);

        assertNotEquals(0, count);
    }

    @Test
    public void test04GetAllForAdopting() {
        List<Animal> expected = animalRepositoryImpl.getAllForAdopting();

        assertNotNull(expected);
    }

    @Test
    public void test05GetById() {
        actual = animalRepositoryImpl.getById(actual.getId());

        assertNotNull(actual);
    }

    @Test
    public void test06Update() {
        Animal expected = animalRepositoryImpl.getById(actual.getId());

        assertNotNull(expected);
        //assertEquals(expected, actual);

        expected.setTranspNumber(RandomStringUtils.random(10, true, true));
        expected.setTokenNumber(RandomStringUtils.random(10, true, true));
        expected.setColor(RandomStringUtils.random(10, true, true));
        expected.setActive(false);
        expected.setImage(RandomStringUtils.random(10, true, true));

        animalRepositoryImpl.update(expected);
        expected = animalRepositoryImpl.getById(expected.getId());

        assertNotNull(expected);
        assertNotSame(expected, actual);
        assertNotEquals(expected, actual);
    }

    @Test
    public void test07Delete() {
        animalRepositoryImpl.delete(actual.getId());

        Animal expected = animalRepositoryImpl.getById(actual.getId());

        assertNull(expected);
    }
}
