package com.example.datavid_cake_tracker.exception;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;

public class ResourceAlreadyExistsException extends CustomException {
    private static final String MESSAGE = "Person already exists in the database!";
    private static final HttpStatus httpStatus = HttpStatus.CONFLICT;

    public ResourceAlreadyExistsException(String resource) {
        super(MESSAGE,httpStatus, resource, new ArrayList<>());
    }
}
