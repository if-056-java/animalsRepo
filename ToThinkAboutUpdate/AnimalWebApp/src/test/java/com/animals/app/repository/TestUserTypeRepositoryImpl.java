package com.animals.app.repository;

import com.animals.app.domain.UserType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by oleg on 24.07.2015.
 */

public class TestUserTypeRepositoryImpl {

    private static UserTypeRepositoryImpl userTypeRepository;

    @Before
    public void runBeforeClass() {
        userTypeRepository = new UserTypeRepositoryImpl();
    }

    @After
    public void runAfterClass() {
        userTypeRepository = null;
    }

    @Test
    public void testGetAll(){
        List<UserType> userTypeList = userTypeRepository.getAll();

        assertNotNull(userTypeList);
    }

    @Test
    public void testGetById(){
        UserType userType = userTypeRepository.getById(3);

        assertNotNull(userType);
    }
}
