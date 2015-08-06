package com.animals.app.repository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.animals.app.domain.FacebookProfile;
import com.animals.app.domain.GoogleProfile;
import com.animals.app.domain.User;


public class UserRepositoryImp implements UserRepository {
	
	private String FILEPATH = "C:\\dev\\STSworkspace\\oauth\\data.ser" ;
	
	private ArrayList<User> users;
	
	public void init() {
		
		System.out.println("start");
		
		User user = new User();
		
		user.setId("666");
		user.setName("braun");
		user.setEmail("yra@yo.com");
				
		GoogleProfile googlePr = new GoogleProfile(); 
		FacebookProfile fbPr = new FacebookProfile();
		
		googlePr.setName("Gname2");
		googlePr.setEmail("rtry");
		googlePr.setLinkPhoto("linkG");
		
		fbPr.setName("mark2");
		fbPr.setFacebookId(21);
		fbPr.setEmail("uyr@com");
		
		user.setGoogleProfile(googlePr);
		user.setFacebookProfile(fbPr);
		
		ArrayList <User> users = new ArrayList<>();
		users.add(user);
		
		this.users=users;
	}

	public void saveUser(String json) {
		
		String id=null;
		String firstName=null;
		String link=null;
		String email=null;
		
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
			
			firstName = (String) jsonObject.get("given_name");
			System.out.println("The first name is: " + firstName);
			
			id = (String) jsonObject.get("id");
			System.out.println("id is: " + id);
			
			link = (String) jsonObject.get("picture");
			System.out.println("Link to photo: " + link);
			
			email = (String) jsonObject.get("email");
			System.out.println("Email: " + email);
			
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		
		
		//create user from json		
		User user = new User();
		
		user.setId(id);
				
		GoogleProfile googlePr = new GoogleProfile(); 
				
		googlePr.setName(firstName);
		googlePr.setLinkPhoto(link);
		googlePr.setEmail(email);
				
		user.setGoogleProfile(googlePr);	
		
		loadDataFromFile();		
		this.users.add(user);		
		saveDataToFile();			
		
	}

	
	public User getUserById(String id) {
		
		int idi = Integer.parseInt(id);
		
		loadDataFromFile();	
		
		for (User user : users) {
			if (user.getId().equals(idi)) return user;			
		}
		
		return users.get(-1);		
		
	}
	
	public ArrayList<User> getAllUsers() {
		loadDataFromFile();	
		return this.users;
	}	
	
	public void loadDataFromFile() {
		System.out.println("loading data from File");
		try 
		
		(	ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILEPATH));) 
		
		{
		
			ArrayList<User> users = (ArrayList<User>) ois.readObject();
			
			System.out.println("Existing users");
			for (int i = 0; i < users.size(); i++) {
				
				System.out.println(users.get(i));
				System.out.println(users.get(i).getGoogleProfile());
			}
	
			this.users = users;
		
		} catch (Exception e) {
			System.out.println("File not found or error happend/ ZRADA" + e.getMessage());			
		}		
	}
	
	public void saveDataToFile() {
		System.out.println("Saving data to File");		
		try
			// Saving serialized data to txt file(output stream out). 
			( ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILEPATH)); )
			{
			out.writeObject(this.users);
			out.flush();
			
			System.out.println("Saved! PEREMOGA!!!");			
			} catch (Exception e) {
				System.out.println("ERROR! ZRADA" + e.getMessage());					
		}
	}

}