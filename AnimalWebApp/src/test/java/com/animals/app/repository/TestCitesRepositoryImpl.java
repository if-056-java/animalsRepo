package com.animals.app.repository;

import com.animals.app.domain.CitesType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;


/**
 * Created by Rostyslav.Viner on 23.07.2015.
 */
public class TestCitesRepositoryImpl {

    private static CitesTypeRepositoryImpl citesTypeRepositoryImpl;

    @Before
    public void runBeforeClass() {
        citesTypeRepositoryImpl = new CitesTypeRepositoryImpl();
    }

    @After
    public void runAfterClass() {
        citesTypeRepositoryImpl = null;
    }

    @Test
    public void testSelectAll() {
        List<CitesType> list = citesTypeRepositoryImpl.query();
        assertNotNull(list);
    }

    @Test
    public void testSelectById() {
        CitesType expected = citesTypeRepositoryImpl.query(1);

        assertNotNull(expected);
    }
}