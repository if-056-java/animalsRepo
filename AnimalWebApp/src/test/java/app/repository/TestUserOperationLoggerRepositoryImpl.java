package app.repository;

import com.animals.app.domain.UserOperationLogger;
import com.animals.app.repository.Impl.UserOperationLoggerRepositoryImpl;
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
public class TestUserOperationLoggerRepositoryImpl {

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

    static InitialContext ic = null;
    private static void configureJNDIForJUnit(){
        // rcarver - setup the jndi context and the datasource
        try {
            // Create initial context
            System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                    "org.apache.naming.java.javaURLContextFactory");
            System.setProperty(Context.URL_PKG_PREFIXES,
                    "org.apache.naming");
             ic = new InitialContext();

            ic.createSubcontext("java:");
            ic.createSubcontext("java:/comp");
            ic.createSubcontext("java:/comp/env");
            ic.createSubcontext("java:/comp/env/jdbc");

            // Construct DataSource
            MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
            ds.setURL("jdbc:mysql://tym.dp.ua:3306/animals");
            ds.setUser("u_remoteuser");
            ds.setPassword("ZF008NBp");

            ic.bind("java:/comp/env/jdbc/animals", ds);
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
    }

}
