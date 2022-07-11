package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.model.*;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;

    @Autowired
    EmployeeService employeeService;

    public void save(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    public List<Schedule> getAll() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> findScheduleByPetId(long petId) {
        Pet pet = petService.getOne(petId);

        return scheduleRepository.getAllByPetsContains(pet);
    }

    public List<Schedule> findScheduleByEmployeeId(long employeeId) {
        Employee employee = employeeService.findById(employeeId);

        return scheduleRepository.getAllByEmployeesContains(employee);
    }

    public List<Schedule> findScheduleByCustomerId(long customerId) {
        Customer customer = customerService.getOne(customerId);

        return scheduleRepository.getAllByPetsIn(customer.getPets());
    }
}
