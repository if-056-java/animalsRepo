package app.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import java.sql.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.animals.app.controller.client.UserClient;
import com.animals.app.domain.User;
import com.animals.app.repository.Impl.AddressRepositoryImpl;
import com.animals.app.repository.Impl.UserRoleRepositoryImpl;
import com.animals.app.repository.Impl.UserTypeRepositoryImpl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUserClient {
		
    private static User actual;
    private static UserClient client;
	 

	@BeforeClass
    public static void runBeforeClass() {
		
		client = new UserClient();
		
		actual = new User();
        actual.setName(RandomStringUtils.random(10, true, true));
        actual.setSurname(RandomStringUtils.random(10, true, true));
        actual.setRegistrationDate(new Date(System.currentTimeMillis()));
        actual.setUserType(new UserTypeRepositoryImpl().getAll().get(0));
        actual.setUserRole(new UserRoleRepositoryImpl().getAll().subList(0,1));
        actual.setPhone(RandomStringUtils.random(10, true, true));
        //actual.setAddress(new AddressRepositoryImpl().getAll().get(0));
        actual.setEmail(RandomStringUtils.random(10, true, true));
        actual.setSocialLogin(RandomStringUtils.random(10, true, true));
        actual.setPassword(RandomStringUtils.random(10, true, true));
        actual.setOrganizationName(RandomStringUtils.random(10, true, true));
        actual.setOrganizationInfo(RandomStringUtils.random(10, true, true));
        actual.setActive(true);	       
        
	}
	
	@AfterClass
    public static void runAfterClass() {
        actual = null;
    }
	
	@Test
    public void test01Insert() {		
		 		 
		 actual = client.insert(actual);
		 		
		 assertNotNull(actual);
    }

    @Test
    public void test02GetAll() {
    	
    	List<User> userlList = client.getAll();

        assertNotNull(userlList);
    }

    @Test
    public void test03Get() {
    	
        actual = client.get(String.valueOf(actual.getId()));

        assertNotNull(actual);
    }

    @Test
    public void test04Update() {    	

        User expected = client.get(String.valueOf(actual.getId()));

        assertNotNull(expected);
        assertEquals(expected, actual);

        expected.setName(RandomStringUtils.random(10, true, true));
        expected.setPhone(RandomStringUtils.random(10, true, true));        
        expected.setActive(false);       

        expected = client.update(expected);

        assertNotNull(expected);
        assertNotSame(expected, actual);

        assertNotNull(actual);
    }

    @Test
    public void test05Delete() {
    	
        List<User> beforeDelete = client.getAll();

        String response = client.delete(String.valueOf(actual.getId()));       

        List<User> afterDelete = client.getAll();

        assertNotSame(beforeDelete, afterDelete);
    }        

}
