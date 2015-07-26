package com.softserve.app.repository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.softserve.app.model.Owner;
import com.softserve.app.model.Pet;

public class PetRepositoryStub implements PetRepository {
	
	private ArrayList<Pet> pets;
	
	private final static String FILEPATH = "data.ser" ; //alternative route 
//	private final static String FILEPATH = "C:\\dev\\STSworkspace\\rest2\\rest2\\data.ser" ;
	
//	public PetRepositoryStub (){
//		
//		loadDataFromFile();
//		
//	}
	
	// Temporary method for creating data.ser file
	public void init() {
		
		System.out.println("start");
		
		Pet pet1 = new Pet();
		Pet pet2 = new Pet();
		Pet pet3 = new Pet();
		Pet pet4 = new Pet();
		
		pet1.setId("101");
		pet1.setType("Dog");
		pet1.setSize(55);
		
		Owner owner1 = new Owner();
		owner1.setId("101");
		owner1.setName("Bryan");
		owner1.setType("usual");
		owner1.setAdress("Franik");
		
		pet1.setOwner(owner1);
		
		pet2.setId("102");
		pet2.setType("Dog");
		pet2.setSize(56);
		
		Owner owner2 = new Owner();
		owner2.setId("102");
		owner2.setName("Arthur");
		owner2.setType("usual2");
		owner2.setAdress("Franik2");
		
		pet2.setOwner(owner2);
		
		pet3.setId("103");
		pet3.setType("Dog");
		pet3.setSize(55);
		
		Owner owner3 = new Owner();
		owner3.setId("103");
		owner3.setName("Bryan");
		owner3.setType("usual");
		owner3.setAdress("Franik");
		
		pet3.setOwner(owner3);
		
		pet4.setId("104");
		pet4.setType("Dog");
		pet4.setSize(55);
		
		Owner owner4 = new Owner();
		owner4.setId("103");
		owner4.setName("Bryan");
		owner4.setType("usual");
		owner4.setAdress("Franik");
		
		pet4.setOwner(owner4);
		
		ArrayList<Pet> petss = new ArrayList<Pet>();
		petss.add(pet1);
		petss.add(pet2);
		petss.add(pet3);
		petss.add(pet4);
		
		this.pets=petss;
		
		System.out.println("Inizialization. array is ready Value - " + pets.get(1).getOwner().getName());
		
		saveDataToFile();
		//loadDataFromFile();
	}

	@Override
	public void create(Pet pet) {
		
		loadDataFromFile();
		this.pets.add(pet);
		saveDataToFile();				
		//should issue a insert statement to the db
	}	
	

	public List<Pet> findAllPets () {
		loadDataFromFile();		
		return this.pets;
	}
	
	@Override
	public Pet update(String petId, String petType) {
		System.out.println("ping2");
		loadDataFromFile();
		System.out.println("ping3");
		
		for (int i = 0; i < this.pets.size(); i++) {
			if(pets.get(i).getId().equals(petId)){
				System.out.println("Pet Type Updted1");
				pets.get(i).setType(petType);
				System.out.println("Pet Type Updted");
				saveDataToFile();
				return pets.get(i);				
			}			
		}
		System.out.println("Pet Not Found");
		return null;
	}
	
	@Override
	public int delete(String petId) {
		
		loadDataFromFile();
		int k=0;
		
		for (int i = 0; i < this.pets.size(); i++) {
			if(pets.get(i).getId().equals(petId)){
				System.out.println("Pet Type founded");
				this.pets.remove(pets.get(i));
				System.out.println("Pet Removed");
				saveDataToFile();
				return k=1;				
			}			
		}
		if (k==0) System.out.println("Pet Not found");
		return k;				
	}
	
	@Override
	public Pet findPet (String petId) {
		
		loadDataFromFile();	
		
		for (Pet pet2 : pets) {
			if (pet2.getId().equals(petId)){
				System.out.println("Found");
				return pet2;
			} 
		} System.out.println("Pet not found");
		
		return pets.get(-1);
	}
	
	public void loadDataFromFile() {
		System.out.println("before input stream");
		try 	
		// Reading serialized data from txt file(input stream ois). 	
		(	ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILEPATH));	) 
		{	
		// Creating ArrayList to work with (unit)
		System.out.println("inside try block input stream");
		
		@SuppressWarnings("unchecked")
		ArrayList<Pet> pet = (ArrayList<Pet>) ois.readObject();
		System.out.println("****************************************");
		System.out.println("Exsisting Pets in DB");
		System.out.println("****************************************");
		for (int i = 0; i < pet.size(); i++) {
			System.out.println((i+1) + ". " + pet.get(i).getId() + " type = " + 
					pet.get(i).getType()+ "sm, owner - " + pet.get(i).getOwner().getName());
		}

		this.pets = pet;
		
		} catch (Exception e) {
			System.out.println("File not found. Add new value and new file will be created. " + e.getMessage());
		}
		
	}
	
	public void saveDataToFile() {
		System.out.println("before saving");
		try
			// Saving serialized data to txt file(output stream out). 
			( ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILEPATH)); )
			{
			out.writeObject(this.pets);
			out.flush();
			
			System.out.println("Saved!");
			} catch (Exception e) {
				System.out.println(e.getMessage());				
		}
	}	
	
}
