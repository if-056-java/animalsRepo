package app.repository;

import app.JNDIConfigurationForTests;
import com.animals.app.domain.UserType;
import com.animals.app.repository.Impl.UserTypeRepositoryImpl;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUserTypeRepositoryImpl extends JNDIConfigurationForTests{

    private static UserTypeRepositoryImpl userTypeRepository;

    @BeforeClass
    public static void runBeforeClass() {
        configureJNDIForJUnit();

        userTypeRepository = new UserTypeRepositoryImpl();
    }

    @AfterClass
    public static void runAfterClass() {
        userTypeRepository = null;
    }

    @Test
    public void test01GetAll(){
        List<UserType> userTypeList = userTypeRepository.getAll();

        assertNotNull(userTypeList);
    }

    @Test
    public void test02GetById(){
        UserType userType = userTypeRepository.getById(userTypeRepository.getAll().get(0).getId());

        assertNotNull(userType);
    }
}
