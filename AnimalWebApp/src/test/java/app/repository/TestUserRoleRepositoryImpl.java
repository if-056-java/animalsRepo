package app.repository;

import app.JNDIConfigurationForTests;
import com.animals.app.domain.UserRole;
import com.animals.app.repository.Impl.UserRoleRepositoryImpl;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUserRoleRepositoryImpl extends JNDIConfigurationForTests{

    private static UserRoleRepositoryImpl userRoleRepository;

    @BeforeClass
    public static void runBeforeClass() {
        configureJNDIForJUnit();
        userRoleRepository = new UserRoleRepositoryImpl();
    }

    @AfterClass
    public static void runAfterClass() {
        userRoleRepository = null;
    }

    @Test
    public void test01GetAll(){
        List<UserRole> userRoleList = userRoleRepository.getAll();

        assertNotNull(userRoleList);
    }

    @Test
    public void test02GetById(){
        UserRole userRole = userRoleRepository.getById(userRoleRepository.getAll().get(0).getId());

        assertNotNull(userRole);
    }
}
