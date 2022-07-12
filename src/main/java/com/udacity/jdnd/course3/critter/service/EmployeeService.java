package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.api.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.api.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.EmployeeDTO;
import com.udacity.jdnd.course3.critter.model.EmployeeSkill;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public void setDaysAvailableByEmployeeId(long id, Set<DayOfWeek> daysAvailable) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }

    public List<Employee> findAllById(List<Long> employeeIds) {
        return employeeRepository.findAllById(employeeIds);
    }

    public List<Employee> getAllEmployeesWithSkillsAvailableOnDate(Set<EmployeeSkill> skills, LocalDate date) {
        return employeeRepository
                .findAllByDaysAvailableContaining(date.getDayOfWeek())
                .stream()
                .filter(e -> e.getSkills().containsAll(skills))
                .collect(Collectors.toList());
    }
}
