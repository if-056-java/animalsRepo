package com.softserve.app.repository;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.softserve.app.model.Pet;

@RunWith(MockitoJUnitRunner.class)
public class PetRepositoryStubTest2 {

	@Mock PetRepositoryStub stubM;
	
	@Test
	public void findAllTest() {
		
		PetRepositoryStub stub = new PetRepositoryStub();
		
		ArrayList<Pet> pets = (ArrayList<Pet>) stub.findAllPets();
		
		assertNotNull(pets);
	}

}
