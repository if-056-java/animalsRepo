package app.repository;

import com.animals.app.domain.UserRole;
import com.animals.app.repository.Impl.UserRoleRepositoryImpl;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import org.junit.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by oleg on 24.07.2015.
 */

public class TestUserRoleRepositoryImpl {

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
    public void testGetAll(){
        List<UserRole> userRoleList = userRoleRepository.getAll();

        assertNotNull(userRoleList);
    }

    @Test
    public void testGetById(){
        UserRole userRole = userRoleRepository.getById(1);

        assertNotNull(userRole);
    }

    private static void configureJNDIForJUnit(){
        // rcarver - setup the jndi context and the datasource
        try {
            // Create initial context
            System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                    "org.apache.naming.java.javaURLContextFactory");
            System.setProperty(Context.URL_PKG_PREFIXES,
                    "org.apache.naming");
            InitialContext ic = new InitialContext();

            ic.createSubcontext("java:");
            ic.createSubcontext("java:/comp");
            ic.createSubcontext("java:/comp/env");
            ic.createSubcontext("java:/comp/env/jdbc");

            // Construct DataSource
            MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
            ds.setURL("jdbc:mysql://tym.dp.ua:3306/animals");
            ds.setUser("u_remoteuser");
            ds.setPassword("ZF008NBp");

            ic.rebind("java:/comp/env/jdbc/animals", ds);
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
    }

}
