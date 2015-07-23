package com.animals.app;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.animals.app.domain.Owner;
import com.animals.app.domain.Pet;
import com.animals.app.repository.PetRepositoryStub;

public class PetRepositoryStubTestInitBefore {

	private static PetRepositoryStub stubT;
	
	@BeforeClass
    public static void setUpRepositoryStub() {
		
        System.out.println("@BeforeClass - SetUp");
        
        PetRepositoryStub stubTt = new PetRepositoryStub();
        
        stubTt.setFILEPATH("dataTest.ser");
        
        Pet pet1 = new Pet();
        
        pet1.setId("10");
		pet1.setType("DogStub");
		pet1.setSize(55);
		
		Owner owner1 = new Owner();
		owner1.setId("101");
		owner1.setName("NameStub");
		owner1.setType("usualStub");
		owner1.setAdress("CityStub");
		
		pet1.setOwner(owner1);
		
		ArrayList<Pet> petsT = new ArrayList<Pet>();
		
		petsT.add(pet1);
		
		stubTt.setPets(petsT);	
		
		stubTt.saveDataToFile();		
		
		stubT=stubTt;
		
    }
	
	@AfterClass
    public static void TimeTearDownRepositoryStub() {
        System.out.println("@AfterClass - TearDown");
        
        //stubT=null; // no need as expert said :)
    }	
		
	@Test
	public void testFindAllPetsWithStubT() {
		
		System.out.println("r");
		System.out.println(stubT.getPets().get(0).getType());
		
		List<Pet> petts = stubT.findAllPets();
		
//		System.out.println(petts.get(0).getType());
		//System.out.println(stubT.getPets().get(1).getType());
		
		assertNotNull(petts); 									//peremoga
		assertEquals("DogStub", petts.get(0).getType()); 		//peremoga
		
	}
	
	@Test
	public void testFindPetByIdWithStubT() {
		
		Pet pet = stubT.findPet("10");
		
		System.out.println(pet);
		
		assertNotNull(pet); 									//peremoga
	}
	
	@Test(expected=RuntimeException.class)
	public void testFindPetWithBadRequestWithStubT() {
		
		Pet pet = stubT.findPet("0");							//peremoga (bo zrada)
	}
	
	@Test
	public void testCreateWithStubT() {
		
		Pet pet5 = new Pet();
				
		pet5.setId("5");
		pet5.setType("bigBlackDog");
		
		System.out.println(pet5.getType());
		
		stubT.create(pet5);
		
		System.out.println(stubT.findPet("5").getType());
		
		assertNotNull(stubT.findPet("5").getType());			//peremoga
		
		assertEquals("bigBlackDog", stubT.getPets().get(1).getType());  //peremoga
		
	}
	
	@Test
	public void testUpdateWithStubT() {
		
		String newType = "snake";
		
		stubT.update("10", newType);
		
		System.out.println(stubT.getPets().get(0).getType());
		
		assertEquals("snake", stubT.getPets().get(0).getType()); //peremoga
		
	}
	
	@Test
	public void testDeleteWithStubT() {
		
		int k = stubT.delete("10");
		
		System.out.println(k);
		
		assertEquals(1, k);										//peremoga
		
	}	

}
