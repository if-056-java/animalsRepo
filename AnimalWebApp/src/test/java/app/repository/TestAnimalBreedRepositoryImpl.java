package app.repository;

import com.animals.app.domain.AnimalBreed;
import com.animals.app.repository.AnimalBreedRepository;
import com.animals.app.repository.Impl.AnimalBreedRepositoryImpl;
import com.animals.app.repository.Impl.AnimalTypeRepositoryImpl;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.ibatis.exceptions.PersistenceException;
import org.junit.*;
import org.junit.runners.MethodSorters;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Rostyslav.Viner on 06.08.2015.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAnimalBreedRepositoryImpl {

    private static AnimalBreedRepository animalBreedRepository;

    private static AnimalBreed actual;

    @BeforeClass
    public static void runBeforeClass() throws Exception {
            configureJNDIForJUnit();

            animalBreedRepository = new AnimalBreedRepositoryImpl();

            actual = new AnimalBreed();
            actual.setBreedUa(RandomStringUtils.random(10, true, true));
            actual.setBreedEn(RandomStringUtils.random(10, true, true));
            actual.setType(new AnimalTypeRepositoryImpl().getAll().get(0));
    }

    @AfterClass
    public static void runAfterClass() {
        animalBreedRepository = null;
        actual = null;
    }

    @Test
    public void test01Insert_ua() throws NamingException {
        assertNotNull(actual);
        assertNull(actual.getId());

        animalBreedRepository.insert_ua(actual);

        assertNotNull(actual.getId());
    }

    //field breedUa is unique
    @Test(expected = PersistenceException.class)
    public void test02Insert_ua() {
        assertNotNull(actual);

        animalBreedRepository.insert_ua(actual);
    }

    @Test
    public void test03GetAll() {
        List<AnimalBreed> list = animalBreedRepository.getAll();
        assertNotNull(list);
    }

    @Test
    public void test04GetById() {
        assertNotNull(actual);
        assertNotNull(actual.getId());

        AnimalBreed expected = animalBreedRepository.getById(actual.getId());

        assertNotNull(expected);
    }

    @Test
    public void test05GetById() {
        AnimalBreed expected = animalBreedRepository.getById(-1);

        assertNull(expected);
    }

    @Test
    public void test06GetByTypeId() {
        assertNotNull(actual);
        assertNotNull(actual.getType());
        assertNotNull(actual.getType().getId());

        List<AnimalBreed> expected = animalBreedRepository.getByTypeId(actual.getType().getId());

        assertNotNull(expected);
    }

    @Test
    public void test07GetByTypeId() {
        List<AnimalBreed> expected = animalBreedRepository.getByTypeId(-1);

        assertNotNull(expected);
        assertEquals(expected.size(), 0);
    }

    @Test
    public void test08DeleteById() {
        assertNotNull(actual);
        assertNotNull(actual.getId());

        animalBreedRepository.deleteById(actual.getId());

        AnimalBreed expected = animalBreedRepository.getById(actual.getId());

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
