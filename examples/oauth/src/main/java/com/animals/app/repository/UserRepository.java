package com.animals.app.repository;

import java.util.ArrayList;

import com.animals.app.domain.User;

public interface UserRepository {
	
	void saveUser(String json);
	
	User getUserById(String id);
	
	ArrayList<User> getAllUsers();

}
