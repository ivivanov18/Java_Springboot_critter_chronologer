package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.model.*;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    CustomerService customerService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = customerService.addCustomer(convertCustomerDTOToEntity(customerDTO));
        return convertEntityToCustomerDTO(customer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = customerService.getAllCustomers();
        List<CustomerDTO> dtos = new ArrayList<>();
        for (Customer customer: customers) {
            dtos.add(convertEntityToCustomerDTO(customer));
        }
        return dtos;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = customerService.getOwnerByPetId(petId);
        return convertEntityToCustomerDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeService.addEmployee(convertEmployeeDTOToEntity(employeeDTO));
        return convertEntityToEmployeeDTO(employee);
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.findById(employeeId);
        return convertEntityToEmployeeDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setDaysAvailableByEmployeeId(employeeId, daysAvailable);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employees = employeeService
                                        .getAllEmployeesWithSkillsAvailableOnDate(
                                                employeeDTO.getSkills(),
                                                employeeDTO.getDate()
                                        );
        return employees.stream().map(UserController::convertEntityToEmployeeDTO).collect(Collectors.toList());
    }

    private static CustomerDTO convertEntityToCustomerDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        BeanUtils.copyProperties(customer, dto);
        return dto;
    }

    private static EmployeeDTO convertEntityToEmployeeDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        BeanUtils.copyProperties(employee, dto);
        return dto;
    }

    private static Customer convertCustomerDTOToEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(dto, customer);
        return customer;
    }

    private static Employee convertEmployeeDTOToEntity(EmployeeDTO dto) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(dto, employee);
        return employee;
    }

}
