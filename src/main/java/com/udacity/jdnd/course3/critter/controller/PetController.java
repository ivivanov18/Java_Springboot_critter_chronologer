package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.api.PetNotFoundException;
import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.PetDTO;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet petToBeCreated = convertPetDTOToEntity(petDTO);
        Pet petCreated = petService.addPet(petToBeCreated, petDTO.getOwnerId());
        return convertEntityToPetDTO(petCreated);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getOne(petId);
        if (pet == null) {
            throw new PetNotFoundException(petId);
        }
        return convertEntityToPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAllPets();
        List<PetDTO> dtos = new ArrayList<>();
        for (Pet pet: pets) {
            dtos.add(convertEntityToPetDTO(pet));
        }
        return dtos;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.getPetsByOwner(ownerId);
        return pets.stream().map(PetController::convertEntityToPetDTO).collect(Collectors.toList());
    }

    private static PetDTO convertEntityToPetDTO(Pet pet) {
        PetDTO dto = new PetDTO();
        BeanUtils.copyProperties(pet, dto);
        if (pet.getCustomer() != null) {
            dto.setOwnerId(pet.getCustomer().getId());
        }
        return dto;
    }

    private static Pet convertPetDTOToEntity(PetDTO petDTO ) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }
}
