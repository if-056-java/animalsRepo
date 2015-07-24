package com.animals.app.repository;

import java.util.List;

import com.animals.app.domain.Pet;

public interface PetRepository {

	List<Pet> findAllPets();

	Pet findPet(String petId);

	void create(Pet pet);

	Pet update(String petId, String petType);

	int delete(String petId);

}