package app.repository;

import app.JNDIConfigurationForTests;
import com.animals.app.domain.UserOperationType;
import com.animals.app.repository.Impl.UserOperationTypeRepositoryImpl;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by oleg on 24.07.2015.
 */

public class TestUserOperationTypeRepositoryImpl extends JNDIConfigurationForTests {

    private static UserOperationTypeRepositoryImpl userOperationTypeRepository;

    @BeforeClass
    public static void runBeforeClass() {
        configureJNDIForJUnit();
        userOperationTypeRepository = new UserOperationTypeRepositoryImpl();
    }

    @AfterClass
    public static void runAfterClass() {
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
