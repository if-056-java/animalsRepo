package app.repository;

import com.animals.app.domain.Animal;
import com.animals.app.repository.Impl.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.sql.Date;
import java.util.List;

import static junit.framework.Assert.*;

/**
 * Created by Rostyslav.Viner on 24.07.2015.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAnimalRepositoryImpl {

    private static AnimalRepositoryImpl animalRepositoryImpl;
    private static Animal actual;

    @BeforeClass
    public static void runBeforeClass() {
        animalRepositoryImpl = new AnimalRepositoryImpl();

        actual = new Animal();
        actual.setSex(new AnimalSexTypeRepositoryImpl().getAll().get(0));
        actual.setType(new AnimalTypeRepositoryImpl().getAll().get(0));
        actual.setSize(new AnimalSizeRepositoryImpl().getAll().get(0));
        actual.setAnimalCites(new AnimalCitesTypeRepositoryImpl().getAll().get(0));
        actual.setSort(RandomStringUtils.random(10, true, true));
        actual.setTranspNumber(RandomStringUtils.random(10, true, true));
        actual.setTokenNumber(RandomStringUtils.random(10, true, true));
        actual.setDateOfRegister(new Date(System.currentTimeMillis()));
        actual.setDateOfBirth(new Date(System.currentTimeMillis()));
        actual.setDateOfSterilization(new Date(System.currentTimeMillis()));
        actual.setColor(RandomStringUtils.random(10, true, true));
        actual.setUser(new UserRepositoryImpl().getAll().get(0));
        actual.setAddress(new AddressRepositoryImpl().getAll().get(0));
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
    public void test02GetAll() {
        List<Animal> expected = animalRepositoryImpl.getAll();

        assertNotNull(expected);
    }

    @Test
    public void test021GetAllForAdopting() {
        List<Animal> expected = animalRepositoryImpl.getAllForAdopting();

        assertNotNull(expected);
    }

    @Test
    public void test03GetById() {
        actual = animalRepositoryImpl.getById(actual.getId());

        assertNotNull(actual);
    }

    @Test
    public void test04Update() {
        Animal expected = animalRepositoryImpl.getById(actual.getId());

        assertNotNull(expected);
        assertEquals(expected, actual);

        expected.setSort(RandomStringUtils.random(10, true, true));
        expected.setTranspNumber(RandomStringUtils.random(10, true, true));
        expected.setTokenNumber(RandomStringUtils.random(10, true, true));
        expected.setColor(RandomStringUtils.random(10, true, true));
        expected.setActive(false);
        expected.setImage(RandomStringUtils.random(10, true, true));

        animalRepositoryImpl.update(expected);
        expected = animalRepositoryImpl.getById(expected.getId());

        assertNotNull(expected);
        assertNotSame(expected, actual);
    }

    @Test
    public void test05Delete() {
        animalRepositoryImpl.delete(actual.getId());

        Animal expected = animalRepositoryImpl.getById(actual.getId());

        assertNull(expected);
    }
}
