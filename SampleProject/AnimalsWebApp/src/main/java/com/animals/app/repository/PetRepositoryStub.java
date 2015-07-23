package com.animals.app.repository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.animals.app.domain.Owner;
import com.animals.app.domain.Pet;


public class PetRepositoryStub implements PetRepository {
	
	static Logger logger = Logger.getLogger(PetRepositoryStub.class);
	static Logger loggerR = Logger.getLogger("R");
	
	private String FILEPATH = "C:\\dev\\STSworkspace\\AnimalsWebApp\\data.ser";
	
	private ArrayList<Pet> pets;
	
	public String getFILEPATH() {
		return FILEPATH;
	}
	
	public void setFILEPATH(String filepath) {
		FILEPATH = filepath;
	}
	
	public ArrayList<Pet> getPets() {
		return pets;
	}
	
	public void setPets(ArrayList<Pet> pets) {
		this.pets = pets;
	}
	
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
		
		saveDataToFile();		
		
		logger.warn("Initializing data.ser file");
		loggerR.warn("Initializing data.ser file");
		
	}

	@Override
	public void create(Pet pet) {
		
		loadDataFromFile();
		this.pets.add(pet);
		saveDataToFile();				
		logger.info("From RepositoryStub: Creating Pet");
		loggerR.info("From RepositoryStub: Creating Pet");
	}	
	

	public List<Pet> findAllPets () {
		logger.info("From RepositoryStub: findAllPets was launched");
		loggerR.info("From RepositoryStub: findAllPets was launched");
		loadDataFromFile();	
		return this.pets;		
	}
	
	@Override
	public Pet update(String petId, String petType) {
		logger.info("From RepositoryStub: update was launched");
		loggerR.info("From RepositoryStub: update was launched");
		loadDataFromFile();		
		
		for (int i = 0; i < this.pets.size(); i++) {
			if(pets.get(i).getId().equals(petId)){				
				pets.get(i).setType(petType);
				logger.info("Pet Type successfully updated. PEREMOGA");
				loggerR.info("Pet Type successfully updated. PEREMOGA");
				saveDataToFile();
				return pets.get(i);				
			}			
		}
		logger.info("Pet not found. ZRADA");
		loggerR.info("Pet not found. ZRADA");
		return null;
	}
	
	@Override
	public int delete(String petId) {
		
		logger.info("From RepositoryStub: delete was launched");
		loggerR.info("From RepositoryStub: delete was launched");
		loadDataFromFile();
		int k=0;
		
		for (int i = 0; i < this.pets.size(); i++) {
			if(pets.get(i).getId().equals(petId)){
				this.pets.remove(pets.get(i));
				logger.info("Pet successfully deleted. PEREMOGA");
				loggerR.info("Pet successfully deleted. PEREMOGA");
				saveDataToFile();
				return k=1;				
			}			
		}
		if (k==0){
			logger.error("Pet not found. ZRADA");
			loggerR.error("Pet not found. ZRADA");
		}
			return k;				
	}
	
	@Override
	public Pet findPet (String petId) {
		
		logger.info("From RepositoryStub: findPet was launched");
		loggerR.info("From RepositoryStub: findPet was launched");
		loadDataFromFile();	
		
		for (Pet pet2 : pets) {
			if (pet2.getId().equals(petId)){
				logger.info("Pet was found. PEREMOGA");
				loggerR.info("Pet was found. PEREMOGA");
				return pet2;
			} 
		} 
		logger.error("Pet not found. ZRADA");
		loggerR.error("Pet not found. ZRADA");
		
		return pets.get(-1);
	}
	
	public void loadDataFromFile() {
		logger.info("loading data from File");
		try 	
		// Reading serialized data from txt file(input stream ois). 	
		(	ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILEPATH));	) 
		{	
		// Creating ArrayList to work with (unit)		
		
		@SuppressWarnings("unchecked")
		ArrayList<Pet> pet = (ArrayList<Pet>) ois.readObject();
		logger.debug("Existing pets");
		for (int i = 0; i < pet.size(); i++) {
			
			logger.debug((i+1) + ". " + pet.get(i).getId() + " type = " + 
					pet.get(i).getType()+ "sm, owner - " + pet.get(i).getOwner().getName());
		}

		this.pets = pet;
		
		} catch (Exception e) {
			logger.error("File not found or error happend/ ZRADA" + e.getMessage());
			loggerR.error("File not found or error happend/ ZRADA" + e.getMessage());
		}
		
	}
	
	public void saveDataToFile() {
		logger.info("Saving data to File");
		loggerR.info("Saving data to File");
		try
			// Saving serialized data to txt file(output stream out). 
			( ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILEPATH)); )
			{
			out.writeObject(this.pets);
			out.flush();
			
			logger.info("Saved! PEREMOGA!!!");
			loggerR.info("Saved! PEREMOGA!!!");
			} catch (Exception e) {
				logger.error("ERROR! ZRADA" + e.getMessage());	
				loggerR.error("ERROR! ZRADA" + e.getMessage());
		}
	}		
}
