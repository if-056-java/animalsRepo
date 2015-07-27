package com.animals.app.repository;

import com.animals.app.domain.AnimalService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;

/**
 * Created by Rostyslav.Viner on 24.07.2015.
 */
public class TestAnimalServiceRepositoryImpl {
    private static AnimalServiceRepositoryImpl animalServiceRepositoryImpl;

    @Before
    public void runBeforeClass() {
        animalServiceRepositoryImpl = new AnimalServiceRepositoryImpl();
    }

    @After
    public void runAfterClass() {
        animalServiceRepositoryImpl = null;
    }

    @Test
    public void testGetAll() {
        List<AnimalService> list = animalServiceRepositoryImpl.getAll();

        assertNotNull(list);
    }

    @Test
    public void testGetById() {
        AnimalService expected = animalServiceRepositoryImpl.getById(1);

        assertNotNull(expected);
    }
}
