package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.model.ScheduleDTO;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    PetService petService;

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
}
