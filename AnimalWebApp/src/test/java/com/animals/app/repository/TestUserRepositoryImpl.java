package com.animals.app.repository;

import com.animals.app.domain.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by oleg on 24.07.2015.
 */

public class TestUserRepositoryImpl {

    private static UserRepositoryImpl userRepository;

    @Before
    public void runBeforeClass() {
        userRepository = new UserRepositoryImpl();
    }

    @After
    public void runAfterClass() {
        userRepository = null;
    }

/*
    @Test
    public void testGetAll(){
        List<User> userList = userRepository.getAll();

        assertNotNull(userList);
    }
*/

    @Test
    public void testGetById(){
        User user = userRepository.getById(1);

        System.out.println(user);
        assertNotNull(user);
    }
}
