package com.softserve.app.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.softserve.app.model.Pet;

@RunWith(MockitoJUnitRunner.class)
public class PetRepositoryStubTest {
	
	@Mock PetRepository stubM; 
	@Mock Pet petM;
	
	@Test
	public void testFindAllPetsWithMockStub() {
		
		List<Pet> pets = stubM.findAllPets();
		
		assertNotNull(pets);
	}	

	@Test
	public void testFindAllPetsWithRealStub() {
		
		PetRepositoryStub stub = new PetRepositoryStub();
		
		List<Pet> pets = (ArrayList<Pet>) stub.findAllPets();
		
		assertNotNull(pets);
	}	
	
	
	@Test
	public void testFindPetWithRealStub() {
		
		PetRepositoryStub stub = new PetRepositoryStub();
		
		Pet pet = stub.findPet("101");
		
		System.out.println(pet);
		
		assertNotNull(pet);
	}
	
	@Test
	public void testFindPetWithMockStub() {
		
		when(stubM.findPet("10").getType()).thenReturn("dog");
		
	    assertEquals("dog", stubM.findPet("0").getType());			
		
	}
	
	
	
	
	@Test(expected=RuntimeException.class)
	public void testFindPetWithBadRequestWithRealStub() {
		
		PetRepositoryStub stub = new PetRepositoryStub();
		
		Pet pet = stub.findPet("0");
	}
	
	@Test
	public void testCreate() {
		
		PetRepositoryStub stub = new PetRepositoryStub();
		
		Pet pet5 = new Pet();
				
		pet5.setId("5");
		pet5.setType("Dog");
		
		System.out.println(pet5.getType());
		
		stub.create(pet5);
		
		System.out.println(stub.findPet("5").getType());
		
		assertNotNull(stub.findPet("5").getType());
	}
	
	

}
