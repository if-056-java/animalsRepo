package com.animals.app.repository;

import com.animals.app.domain.Animal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;

/**
 * Created by Rostyslav.Viner on 24.07.2015.
 */
public class TestAnimalRepositoryImpl {
    private static AnimalRepositoryImpl animalRepositoryImpl;

    @Before
    public void runBeforeClass() {
        animalRepositoryImpl = new AnimalRepositoryImpl();
    }

    @After
    public void runAfterClass() {
        animalRepositoryImpl = null;
    }

    @Test
    public void testGetAll() {
        List<Animal> expected = animalRepositoryImpl.getAll();

        assertNotNull(expected);
    }

    @Test
    public void testGetById() {
        Animal expected = animalRepositoryImpl.getById(1);

        assertNotNull(expected);
    }
}
