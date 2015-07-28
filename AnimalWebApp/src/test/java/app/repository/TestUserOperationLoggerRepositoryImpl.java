package app.repository;

import com.animals.app.domain.UserOperationLogger;
import com.animals.app.repository.Impl.UserOperationLoggerRepositoryImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by oleg on 24.07.2015.
 */

public class TestUserOperationLoggerRepositoryImpl {

    private static UserOperationLoggerRepositoryImpl userOperationLoggerRepository;

    @Before
    public void runBeforeClass() {
        userOperationLoggerRepository = new UserOperationLoggerRepositoryImpl();
    }

    @After
    public void runAfterClass() {
        userOperationLoggerRepository = null;
    }

    @Test
    public void testGetAll(){
        List<UserOperationLogger> userOperationLoggerList = userOperationLoggerRepository.getAll();

        System.out.println(userOperationLoggerList);
        assertNotNull(userOperationLoggerList);
    }

    @Test
    public void testGetById(){
        UserOperationLogger userOperationLogger = userOperationLoggerRepository.getById(userOperationLoggerRepository.getAll().get(0).getId());

        assertNotNull(userOperationLogger);
    }
}
