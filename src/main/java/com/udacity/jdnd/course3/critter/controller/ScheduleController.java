package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.model.ScheduleDTO;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @Autowired
    PetService petService;

    @Autowired
    EmployeeService employeeService;

    @PostMapping
    //TODO: add check if necessary data is present
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertScheduleDtoToEntity(scheduleDTO);

        List<Employee> employees =employeeService.findAllById(scheduleDTO.getEmployeeIds());
        schedule.setEmployees(employees);

        List<Pet> pets = petService.findAllById(scheduleDTO.getPetIds());
        schedule.setPets(pets);

        scheduleService.save(schedule);
        return scheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAll();
        return schedules
                .stream()
                .map(ScheduleController::convertEntityToScheduleDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
       return scheduleService
               .findScheduleByPetId(petId)
               .stream().map(ScheduleController::convertEntityToScheduleDto)
               .collect(Collectors.toList());

    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleService
                .findScheduleByEmployeeId(employeeId)
                .stream().map(ScheduleController::convertEntityToScheduleDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return scheduleService
                .findScheduleByCustomerId(customerId)
                .stream()
                .map(ScheduleController::convertEntityToScheduleDto)
                .collect(Collectors.toList());
    }

    private static ScheduleDTO convertEntityToScheduleDto(Schedule schedule) {
        ScheduleDTO dto = new ScheduleDTO();
        List<Long> employeeIds = schedule.getEmployees().stream().map(e -> e.getId()).collect(Collectors.toList());
        List<Long> petIds = schedule.getPets().stream().map(e -> e.getId()).collect(Collectors.toList());
        dto.setEmployeeIds(employeeIds);
        dto.setPetIds(petIds);
        BeanUtils.copyProperties(schedule, dto);
        return dto;
    }

    private static Schedule convertScheduleDtoToEntity(ScheduleDTO dto) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(dto, schedule);
        return schedule;
    }
}
