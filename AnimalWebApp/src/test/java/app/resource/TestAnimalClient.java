/*
package app.resource;

import com.animals.app.controller.client.AnimalClient;
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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;
import static org.junit.Assert.assertNotNull;

*/
/**
 * Created by oleg on 28.07.2015.
 *//*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAnimalClient {

    private static Animal actual;

    @BeforeClass
    public static void runBeforeClass() {
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
    }

    @Test
    public void test01Insert() {
        AnimalClient client = new AnimalClient();

        actual = client.insert(actual);

        assertNotNull(actual);
    }

    @Test
    public void test02GetAll() {
        AnimalClient client = new AnimalClient();

        List<Animal> animalList = client.getAll();

        assertNotNull(animalList);
    }

    @Test
    public void test03Get() {
        AnimalClient client = new AnimalClient();

        actual = client.get(String.valueOf(actual.getId()));

        assertNotNull(actual);
    }

    @Test
    public void test04Update() {
        AnimalClient client = new AnimalClient();

        Animal expected = client.get(String.valueOf(actual.getId()));

        assertNotNull(expected);
        assertEquals(expected, actual);

        expected.setSort(RandomStringUtils.random(10, true, true));
        expected.setTranspNumber(RandomStringUtils.random(10, true, true));
        expected.setTokenNumber(RandomStringUtils.random(10, true, true));
        expected.setColor(RandomStringUtils.random(10, true, true));
        expected.setActive(false);
        expected.setImage(RandomStringUtils.random(10, true, true));

        expected = client.update(expected);

        assertNotNull(expected);
        assertNotSame(expected, actual);

        assertNotNull(actual);
    }

    @Test
    public void test05Delete() {
        AnimalClient client = new AnimalClient();

        List<Animal> beforeDelete = client.getAll();

        List<Animal> animals =
            client.delete(String.valueOf(actual.getId()));

        List<Animal> afterDelete = client.getAll();

        assertNotSame(beforeDelete, afterDelete);
    }
}*/
