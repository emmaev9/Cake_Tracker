package com.example.datavid_cake_tracker.exception;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;

public class UnderagePersonException extends CustomException {
    private static final String MESSAGE = "Person must be at least 18 years old!";
    private static final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public UnderagePersonException(String resource) {
        super(MESSAGE,httpStatus, resource, new ArrayList<>());
    }
}
