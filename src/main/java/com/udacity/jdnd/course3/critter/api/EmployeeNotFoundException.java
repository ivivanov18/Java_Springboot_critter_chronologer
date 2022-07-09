package com.udacity.jdnd.course3.critter.api;

public class EmployeeNotFoundException extends RuntimeException{
        public EmployeeNotFoundException(Long id) {
            super("Employee id not found " + id);
        }
}
