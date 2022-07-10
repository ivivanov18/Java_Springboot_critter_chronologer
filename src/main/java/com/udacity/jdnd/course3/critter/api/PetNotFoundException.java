package com.udacity.jdnd.course3.critter.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such Pet")
public class PetNotFoundException extends RuntimeException {
    public PetNotFoundException(Long id) {
        super("Pet id not found " + id);
    }
}
