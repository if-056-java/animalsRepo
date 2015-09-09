package app.repository;

import com.animals.app.domain.User;
import com.animals.app.repository.Impl.UserRepositoryImpl;
import com.animals.app.repository.Impl.UserRoleRepositoryImpl;
import com.animals.app.repository.Impl.UserTypeRepositoryImpl;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.Date;
import java.util.List;

import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

/**
 * Created by oleg on 24.07.2015.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUserRepositoryImpl {

    private static UserRepositoryImpl userRepository;
    private static User actual;

    @BeforeClass
    public static void runBeforeClass() {
        configureJNDIForJUnit();

        userRepository = new UserRepositoryImpl();

        actual = new User();
        actual.setName(RandomStringUtils.random(10, true, true));
        actual.setSurname(RandomStringUtils.random(10, true, true));
        actual.setRegistrationDate(new Date(System.currentTimeMillis()));
        actual.setUserType(new UserTypeRepositoryImpl().getAll().get(0));
        actual.setUserRole(new UserRoleRepositoryImpl().getAll().subList(0,1));
        actual.setPhone(RandomStringUtils.random(10, true, true));
        actual.setAddress(RandomStringUtils.random(10, true, true));
        actual.setEmail(RandomStringUtils.random(10, true, true));
        actual.setSocialLogin(RandomStringUtils.random(10, true, true));
        actual.setPassword(RandomStringUtils.random(10, true, true));
        actual.setOrganizationName(RandomStringUtils.random(10, true, true));
        actual.setOrganizationInfo(RandomStringUtils.random(10, true, true));
        actual.setActive(true);
    }

    @AfterClass
    public static void runAfterClass() {
        userRepository = null;
        actual = null;
    }

    @Test
    public void test01Insert() {
        assertNull(actual.getId());

        userRepository.insert(actual);

        assertNotNull(actual.getId());
    }

    @Test
    public void test02GetAll(){
        List<User> userList = userRepository.getAll();

        assertNotNull(userList);
    }

    @Test
    public void test03GetById(){
        User user = userRepository.getById(actual.getId());

        assertNotNull(user);
    }

    @Test
    public void test04Update() {
        User expected = userRepository.getById(actual.getId());

        assertNotNull(expected);
        /*assertSame(expected, actual); //comment, because conflict in list object*/

        expected.setName(RandomStringUtils.random(10, true, true));
        expected.setSurname(RandomStringUtils.random(10, true, true));
        expected.setEmail(RandomStringUtils.random(10, true, true));
        expected.setPassword(RandomStringUtils.random(10, true, true));
        expected.setActive(false);
        expected.setPhone(RandomStringUtils.random(10, true, true));

        userRepository.update(expected);

        assertNotNull(expected);
        assertNotSame(expected, actual);
    }

    @Test
    public void test05Delete() {
        userRepository.delete(actual.getId());

        User expected = userRepository.getById(actual.getId());

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

            ic.rebind("java:/comp/env/jdbc/animals", ds);
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
    }

}
