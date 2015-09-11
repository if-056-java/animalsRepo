package app.repository;

import app.JNDIConfigurationForTests;
import com.animals.app.domain.UserOperationLogger;
import com.animals.app.repository.Impl.UserOperationLoggerRepositoryImpl;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by oleg on 24.07.2015.
 */
public class TestUserOperationLoggerRepositoryImpl extends JNDIConfigurationForTests {

    private static UserOperationLoggerRepositoryImpl userOperationLoggerRepository;

    @BeforeClass
    public static void runBeforeClass() {
        configureJNDIForJUnit();

        userOperationLoggerRepository = new UserOperationLoggerRepositoryImpl();
    }

    @AfterClass
    public static void runAfterClass() {
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
