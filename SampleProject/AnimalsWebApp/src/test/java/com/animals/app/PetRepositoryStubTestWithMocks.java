package com.animals.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.animals.app.domain.Pet;
import com.animals.app.repository.PetRepositoryStub;

//Forbidden to look at this code!!! It's perfect example HOW YOU SHOULDN'T USE MOCK's objects :D
@RunWith(MockitoJUnitRunner.class) 
public class PetRepositoryStubTestWithMocks {
	
	@Mock PetRepositoryStub stubM; 
	@Mock Pet petM;
	@Mock List<Pet> pets;

	@Test
	public void testFindAllPetsWithMockStub() {
		
		List<Pet> petts = stubM.findAllPets();
		
		assertNotNull(petts);						//peremoga
	}
	
	@Test
	public void testFindAllPetsWithMockStub2() {
		
		when(stubM.findAllPets().get(anyInt())).thenReturn(petM);		
		when(petM.getType()).thenReturn("White Cat");
		
		Pet petlocal = stubM.findAllPets().get(0);	
		
		String type = petlocal.getType();
		
		assertEquals("White Cat", type);			//zrada
		
	}
	
	@Test
	public void testFindPetWithMockStub() {
		
		when(stubM.findPet("10")).thenReturn(petM);
		
		Pet pet = stubM.findPet("10");
		
		assertNotNull(pet);							//peremoga
		
	}
	
	@Test
	public void testFindPetWithMockStub2() {
		
		when(stubM.findPet(anyString())).thenReturn(petM);
		when(petM.getType()).thenReturn("dog");
		
		assertEquals("dog", stubM.findPet("10").getType());	//peremoga
		
	}
	
	@Test
	public void testCreateWithMockStub2() {
		
		stubM.create(petM);	
		
		assertNotNull(stubM.getPets());						//peremoga
		
	}
	
	@Test
	public void testCreateWithMockStub3() {
		
		//petM.setId("20");
		when(stubM.findPet(anyString())).thenReturn(petM);
		
		stubM.create(petM);	
		
		assertEquals(petM, stubM.findPet("20")); 				//peremoga
		
	}
	
	@Test
	public void testUpdateWithMockStub() {
		
		when(stubM.findPet("10")).thenReturn(petM);
		when(petM.getType()).thenReturn("snake");
				
		String newType = "snake";
		stubM.update("10", newType);
		
		System.out.println(stubM.findPet("10").getType());
		
		assertEquals("snake", stubM.findPet("10").getType()); 		//peremoga 4u zrada?
		
	}
	
	@Test
	public void testUpdateWithMockStub2() {
		
		Pet petToUpdate = petM;
		
		petToUpdate.setId("10");

		petToUpdate.setType("dog");
		
		stubM.create(petToUpdate);	
		
		String newType = "snake";
		stubM.update("10", newType);
		
		when(stubM.findPet("10")).thenReturn(petToUpdate);
		when(petM.getType()).thenReturn("snake");  //without this line - zrada
		
		//System.out.println(stubM.findPet("10").getType());
		
		assertEquals("snake", stubM.findPet("10").getType()); 		//peremoga
		
	}
	
	@Test
	public void testDeleteWithMockStub() {
		
		when(stubM.delete(anyString())).thenReturn(1);
		
		int k = stubM.delete("10");	
		
		assertEquals(1, k);	
		
	}

}
