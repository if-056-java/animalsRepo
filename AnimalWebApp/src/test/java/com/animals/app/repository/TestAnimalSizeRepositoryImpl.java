package com.animals.app.repository;

import com.animals.app.domain.AnimalSize;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;

/**
 * Created by Rostyslav.Viner on 23.07.2015.
 */
public class TestAnimalSizeRepositoryImpl {

    private static AnimalSizeRepositoryImpl animalSizeRepositoryImpl;

    @Before
    public void runBeforeClass() {
        animalSizeRepositoryImpl = new AnimalSizeRepositoryImpl();
    }

    @After
    public void runAfterClass() {
        animalSizeRepositoryImpl = null;
    }

    @Test
    public void testGetAll() {
        List<AnimalSize> expected = animalSizeRepositoryImpl.getAll();

        assertNotNull(expected);
    }

    @Test
    public void testGetById() {
        AnimalSize expected = animalSizeRepositoryImpl.getById(1);

        assertNotNull(expected);
    }
}
