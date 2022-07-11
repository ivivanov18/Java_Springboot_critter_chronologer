package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.api.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
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

    @Autowired
    CustomerRepository customerRepository;

    public Pet addPet(Pet pet, Long ownerId) {
        Customer owner = customerService.getOne(ownerId);
        owner.getPets().add(pet);
        customerService.addCustomer(owner);
        pet.setCustomer(owner);
        return petRepository.save(pet);
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public Pet getOne(long petId) {
        return petRepository.getOne(petId);
    }

    public List<Pet> getPetsByOwner(long ownerId) {
        Customer customer = customerService.getOne(ownerId);
        if (customer == null) {
            throw new CustomerNotFoundException(ownerId);
        }
        return customer.getPets();
    }

    public List<Pet> findAllById(List<Long> petIds) {
        return petRepository.findAllById(petIds);
    }
}
