package com.animals.app.repository;

import com.animals.app.domain.UserOperationType;
import com.animals.app.domain.UserType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by oleg on 24.07.2015.
 */

public class TestUserOperationTypeRepositoryImpl {

    private static UserOperationTypeRepositoryImpl userOperationTypeRepository;

    @Before
    public void runBeforeClass() {
        userOperationTypeRepository = new UserOperationTypeRepositoryImpl();
    }

    @After
    public void runAfterClass() {
        userOperationTypeRepository = null;
    }

    @Test
    public void testGetAll(){
        List<UserOperationType> userOperationTypeList = userOperationTypeRepository.getAll();

        assertNotNull(userOperationTypeList);
    }

    @Test
    public void testGetById(){
        UserOperationType userOperationType = userOperationTypeRepository.getById(1);

        assertNotNull(userOperationType);
    }
}
