package com.udacity.jdnd.course3.critter.api;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long id) {
        super("Customer id not found " + id);
    }
}
