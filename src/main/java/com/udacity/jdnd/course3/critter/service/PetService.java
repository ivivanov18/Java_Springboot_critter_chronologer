package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.PetDTO;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {
    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerService customerService;

    public Pet addPet(Pet pet, Long ownerId) {
        Customer owner = customerService.getOne(ownerId);
        pet.setCustomer(owner);
        petRepository.save(pet);
        return pet;
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }
}
