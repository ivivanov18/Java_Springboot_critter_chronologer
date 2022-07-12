package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PetService {
    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerService customerService;

    public Pet addPet(Pet pet, Long ownerId) {
        Customer owner = customerService.getOne(ownerId);
        pet.setCustomer(owner);
        Pet savedPet = petRepository.save(pet);

        List<Pet> pets = owner.getPets();
        pets.add(savedPet);
        owner.setPets(pets);
        customerService.save(owner);

        return savedPet;
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public Pet getOne(long petId) {
        return petRepository.getOne(petId);
    }

    public List<Pet> getPetsByOwner(long ownerId) {
        return petRepository.getPetsByCustomer_Id(ownerId);
    }

    public List<Pet> findAllById(List<Long> petIds) {
        return petRepository.findAllById(petIds);
    }
}
