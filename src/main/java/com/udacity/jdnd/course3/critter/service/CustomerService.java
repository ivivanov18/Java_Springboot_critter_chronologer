package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.api.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.api.PetNotFoundException;
import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PetService petService;

    @Autowired
    PetRepository petRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public Customer getOne(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    public Customer getOwnerByPetId(long petId) {
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new PetNotFoundException(petId));
        return pet.getCustomer();
    }
}
