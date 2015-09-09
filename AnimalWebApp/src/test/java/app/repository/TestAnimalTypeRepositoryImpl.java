package app.repository;

import com.animals.app.domain.AnimalType;
import com.animals.app.repository.Impl.AnimalTypeRepositoryImpl;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import org.junit.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Rostyslav.Viner on 23.07.2015.
 */
public class TestAnimalTypeRepositoryImpl {
    private static AnimalTypeRepositoryImpl animalTypeRepositoryImpl;

    @BeforeClass
    public static void runBeforeClass() {
        configureJNDIForJUnit();

        animalTypeRepositoryImpl = new AnimalTypeRepositoryImpl();
    }

    @AfterClass
    public static void runAfterClass() {
        animalTypeRepositoryImpl = null;
    }

    @Test
    public void test01GetAll() {
        List<AnimalType> expected = animalTypeRepositoryImpl.getAll();

        assertNotNull(expected);
        assertNotEquals(expected.size(), 0);
    }

    @Test
    public void test02GetById() {
        long testId = animalTypeRepositoryImpl.getAll().get(0).getId();

        assertNotEquals(testId, 0);

        AnimalType expected = animalTypeRepositoryImpl.getById(testId);

        assertNotNull(expected);
    }

    @Test
    public void test03GetById() {
        AnimalType expected = animalTypeRepositoryImpl.getById(-1);

        assertNull(expected);
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

            ic.bind("java:/comp/env/jdbc/animals", ds);
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
    }

}
