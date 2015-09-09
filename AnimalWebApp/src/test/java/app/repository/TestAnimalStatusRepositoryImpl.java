package app.repository;

import com.animals.app.domain.AnimalStatus;
import com.animals.app.repository.Impl.AnimalStatusRepositoryImpl;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Rostyslav.Viner on 01.09.2015.
 */
public class TestAnimalStatusRepositoryImpl {
    private static AnimalStatusRepositoryImpl animalStatusRepositoryImpl;

    @BeforeClass
    public static void runBeforeClass() {
        configureJNDIForJUnit();

        animalStatusRepositoryImpl = new AnimalStatusRepositoryImpl();
    }

    @AfterClass
    public static void runAfterClass() {
        animalStatusRepositoryImpl = null;
    }

    @Test
    public void test01GetAll() {
        List<AnimalStatus> expected = animalStatusRepositoryImpl.getAll();

        assertNotNull(expected);
        assertNotEquals(expected.size(), 0);
    }

    @Test
    public void test02GetById() {
        long testId = animalStatusRepositoryImpl.getAll().get(0).getId();

        assertNotEquals(testId, 0);

        AnimalStatus expected = animalStatusRepositoryImpl.getById(testId);

        assertNotNull(expected);
    }

    @Test
    public void test03GetById() {
        AnimalStatus expected = animalStatusRepositoryImpl.getById(-1);

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
