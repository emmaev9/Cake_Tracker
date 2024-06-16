package com.example.datavid_cake_tracker.exception;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;

public class ResourceNotFoundException extends CustomException {
    private static final String MESSAGE = "Person not found!";
    private static final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public ResourceNotFoundException(String resource) {
        super(MESSAGE,httpStatus, resource, new ArrayList<>());
    }
    public ResourceNotFoundException(String resource, String message){super(message, httpStatus, resource,new ArrayList<>());}
}
