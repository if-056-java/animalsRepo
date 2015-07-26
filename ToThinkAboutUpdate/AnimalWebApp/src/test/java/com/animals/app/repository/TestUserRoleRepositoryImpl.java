package com.animals.app.repository;

import com.animals.app.domain.UserRole;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by oleg on 24.07.2015.
 */

public class TestUserRoleRepositoryImpl {

    private static UserRoleRepositoryImpl userRoleRepository;

    @Before
    public void runBeforeClass() {
        userRoleRepository = new UserRoleRepositoryImpl();
    }

    @After
    public void runAfterClass() {
        userRoleRepository = null;
    }

    @Test
    public void testGetAll(){
        List<UserRole> userRoleList = userRoleRepository.getAll();

        assertNotNull(userRoleList);
    }

    @Test
    public void testGetById(){
        UserRole userRole = userRoleRepository.getById(1);

        assertNotNull(userRole);
    }
}
