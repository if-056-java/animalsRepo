package app.resource;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.glassfish.jersey.client.ClientResponse;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.animals.app.domain.User;
import com.animals.app.domain.UserRole;
import com.animals.app.domain.UserType;
import com.animals.app.repository.UserRepository;
import com.animals.app.repository.Impl.UserRepositoryImpl;
import com.animals.app.service.DateSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import app.JNDIConfigurationForTests;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAuthenticationResource extends ResourceTestTemplate  {
	
	private static Client client;
	
	//moderator credentials
	private static final String LOGIN = "root";
	private static final String PASSWORD = "root";
	private static final String ROLE = "moderator";
	private static final String TWITTER_TOKEN = "70100199-b2aQ9UqRiMCv2Qba2239Hume4YBOLRj3uI4TWUAQn";
	private static final String TWITTER_SECRET = "09Owdt8vE7OBnEErE2InI7h8u5tqrZ4yLynO2dx3jBKFf";
	
	//Wrong credentials
	private static final String LOGINW = "rootWrong";
	private static final String PASSWORDW = "rootWrong";
	
	private static String userLogin;
	private static String userEmail;
	private static User user;
	private static int id;
	
	private static final String REST_SERVICE_URL = BASE_URL + "account";	
	
	
	@BeforeClass
    public static void runBeforeClass() {
        JNDIConfigurationForTests.configureJNDIForJUnit();

        client = ClientBuilder.newClient();
        
        userLogin = RandomStringUtils.random(10, true, true);
        userEmail = userLogin +"@ukr.net";

        user = createEmptyUser(userLogin); 
        user.setEmail(userEmail);
        
        
        
    }

    @AfterClass
    public static void runAfterClass() {    
        
        client = null;   
        
    }
    
    @Test
    public void test01LoginToSite() {

    	String passwordMd5 = ResourceTestTemplate.getMd5(PASSWORD);
        String credentials = "Basic " + Base64.encodeBase64String((LOGIN + ':' + passwordMd5).getBytes());

        String result = client
                .target(REST_SERVICE_URL)
                .path("/login")
                .request()
                .header("Authorization", credentials)
                .header("rememberMe", "OFF")
                .post(null, String.class);

        Map<String, String> jsonMap = new Gson().fromJson(result, HashMap.class);

        String accessToken =  jsonMap.get("accessToken");
        String userLogin = jsonMap.get("socialLogin");
        String userRole = jsonMap.get("userRole"); 
        
        assertNotNull(accessToken);
        assertNotNull(result);
        
        assertEquals(LOGIN, userLogin);
        assertEquals(ROLE, userRole);   
        

    }
    
    @Test 
    public void test02RefreshSession() {    	

    	
        Response responseMsg = client
                .target(REST_SERVICE_URL)
                .path("/refresh")
                .request()                
                .get(Response.class);   
        
        assertNotNull(responseMsg);       
        assertEquals(400, responseMsg.getStatus()); //why not 200
        
    }
    
    
    @Test(expected = NotFoundException.class)
    public void test03LoginToSiteWrongCredentials() {

    	String passwordMd5 = ResourceTestTemplate.getMd5(PASSWORDW);
        String credentials = "Basic " + Base64.encodeBase64String((LOGINW + ':' + passwordMd5).getBytes());

        String result = client
                .target(REST_SERVICE_URL)
                .path("/login")
                .request()
                .header("rememberMe", "OFF")
                .header("Authorization", credentials)
                .post(null, String.class);             

    }
    
    @Test(expected = BadRequestException.class)
    public void test04LoginToSiteWithoutHeader() {

    	String passwordMd5 = ResourceTestTemplate.getMd5(PASSWORD);
        String credentials = "Basic " + Base64.encodeBase64String((LOGIN + ':' + passwordMd5).getBytes());

        String result = client
                .target(REST_SERVICE_URL)
                .path("/login")
                .request() 
                .header("Authorization", null)
                .post(null, String.class);             

    }
    
    @Test
    public void test05RegisterUser() {    
    	  	 
    	String json = new GsonBuilder()
    			.registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(user);      	
    	
    	Response responseMsg = client
                .target(REST_SERVICE_URL)
                .path("/registration")
                .request() 
                .header("locale", "en")
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), Response.class);             
                        
        assertNotNull(responseMsg);
    }
    
    @Test(expected = BadRequestException.class)
    public void test06LoginToSiteWithoutRegConfirmation() {

    	String passwordMd5 = ResourceTestTemplate.getMd5(PASSWORD);
        String credentials = "Basic " + Base64.encodeBase64String((user.getSocialLogin() + ':' + passwordMd5).getBytes());

        String result = client
                .target(REST_SERVICE_URL)
                .path("/login")
                .request()
                .header("Authorization", credentials)
                .header("locale", "en")
                .post(null, String.class);             

    }
    
    @Test (expected = NotAcceptableException.class)
    public void test07RegisterUserLoginExist() {   
        
    	String json = new GsonBuilder()
    			.registerTypeAdapter(Date.class, new DateSerializer())
                .create()
                .toJson(user); 	
    	
    	String result = client
                .target(REST_SERVICE_URL)
                .path("/registration")                
                .request()   
                .header("locale", "en")
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), String.class);      
                        
        
    }
    
   
    
    @Test (expected = NotFoundException.class)
    public void test08RegConfirmationWithWrongVerificationCode() {    	

    	String json = "\"socialLogin\":" + userLogin +"\""; ;
        
        String responseMsg = client
                .target(REST_SERVICE_URL)
                .path("/confirmRegistration/wrongCode")
                .request()                
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), String.class);                
        
    }
    
    @Test
    public void test09logout() { 
    	
        Response responseMsg = client
                .target(REST_SERVICE_URL)
                .path("/logout")
                .request()                
                .get(Response.class); 
        
        assertNotNull(responseMsg);
        assertEquals(200, responseMsg.getStatus());
        
    }    
     
    
    
    @Test   
    public void test10loginOauthTwitterDirrectWithResponse() { 
    	
    	Response responseMsg = client
                .target(REST_SERVICE_URL)
                .path("/login/twitter_login_direct")
                .queryParam("token", TWITTER_TOKEN)
                .queryParam("secret", TWITTER_SECRET)
                .request() 
                .get(Response.class);
        
    	assertNotNull(responseMsg);
    	assertEquals(200, responseMsg.getStatus());
        
    }
    
    @Test (expected = Exception.class)
    public void test11loginOauthTwitterDirrectWrongTokens() { 
    	
    	String result = client
                .target(REST_SERVICE_URL)
                .path("/login/twitter_login_direct")
                .queryParam("token", LOGINW)
                .queryParam("secret", LOGINW)
                .request()                
                .get(String.class);
    	
    } 
    
    @Test (expected = BadRequestException.class) //user is not active
    public void test12PasswordRestoreViaMail() {  
        
        String json = userEmail; 
        System.out.println(json);
        
        String result = client
                .target(REST_SERVICE_URL)
                .path("/restore_password")                
                .request() 
                .header("locale", "en")
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), String.class);
               
        
        assertNotNull(result);
       
    }
    
    @Test  //user is active
    public void test13PasswordRestoreViaMail() { 
        
        UserRepository userRep = new UserRepositoryImpl();
        
        User userDB = userRep.findUserByEmail(userEmail);      
        
        id = userDB.getId();
        
        userDB.setIsActive(true);
        
       
        userRep.update(userDB);        
        
        String json = userDB.getEmail(); 
        System.out.println(json);
        
        String result = client
                .target(REST_SERVICE_URL)
                .path("/restore_password")                
                .request() 
                .header("locale", "en")
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), String.class);
               
        
        assertNotNull(result);
       
    }
    
    @Test (expected = NotFoundException.class)
    public void test14PasswordRestoreViaMailBadEmail() {  
        
        String json = "wrong"+userEmail; 
        
        String result = client
                .target(REST_SERVICE_URL)
                .path("/restore_password")                
                .request() 
                .header("locale", "en")
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), String.class);
               
        
        
    }
    
    @Test (expected = BadRequestException.class)
    public void test15PasswordRestoreViaMailWrongFormatadEmail() {  
        
       String json = userEmail + "i@"; 
        
       String result = client
                .target(REST_SERVICE_URL)
                .path("/restore_password")                
                .request() 
                .header("locale", "en")
                .post(Entity.entity(json, MediaType.APPLICATION_JSON + ";charset=UTF-8"), String.class);
               
        
        assertNotNull(result);
       
    }
    
    @Test    
    public void test16DeleteUserFromDb() {        
               
        UserRepository userRep = new UserRepositoryImpl();
            
        userRep.delete(id);
                      
        assertNull(userRep.getById(id));
        
    } 
    
    private static User createEmptyUser(String userName) {
		
		User userToReg = new User();
		
		String userLogin;
		if (userName != null && !userName.isEmpty()) {
			userLogin = userName;
		} else {
			userLogin = "unknown";
		}		
		
		userToReg.setName(userLogin);
		userToReg.setSocialLogin(userLogin);
		userToReg.setSurname(userLogin);		
		userToReg.setAddress(userLogin);
		userToReg.setPhone("000-0000001");
		userToReg.setOrganizationInfo("N/A");
		userToReg.setOrganizationName("N/A");
		userToReg.setPassword(ResourceTestTemplate.getMd5(PASSWORD));

		UserRole userRole = new UserRole();		
		userRole.setId(3);
		List<UserRole> list = new ArrayList<UserRole>();
		list.add(userRole);
		userToReg.setUserRole(list);

		UserType userType = new UserType();
		userType.setId(1);
		userToReg.setUserType(userType);
		
		userToReg.setRegistrationDate(new Date(System.currentTimeMillis()));
		
		return userToReg;
		
	}
    

}
