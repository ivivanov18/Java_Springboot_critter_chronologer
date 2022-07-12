package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.api.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.model.*;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    CustomerService customerService;

    public void save(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    public List<Schedule> getAll() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> findScheduleByPetId(long petId) {
        return scheduleRepository.findAllByPets_Id(petId);
    }

    public List<Schedule> findScheduleByEmployeeId(long employeeId) {
        return scheduleRepository.findAllByEmployees_Id(employeeId);
    }

    public List<Schedule> findScheduleByCustomerId(long customerId) {
        Customer customer = customerService.getOne(customerId);
        if (customer != null) {
            List<Pet> pets = customer.getPets();
            List<Schedule> schedules = new ArrayList<>();
            for (Pet pet: pets) {
                schedules.addAll(scheduleRepository.findAllByPets_Id(pet.getId()));
            }
            return schedules;
        } else {
            throw new CustomerNotFoundException(customerId);
        }
    }
}
