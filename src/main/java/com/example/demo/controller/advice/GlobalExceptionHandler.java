package com.example.demo.controller.advice;

import com.example.demo.exception.NotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(
            Instant.now(),
            HttpStatus.NOT_FOUND.value(),
            e.getMessage(),
            List.of(e.getMessage())
        ));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
        var errors = new ArrayList<String>();

        for (ConstraintViolation<?> constraint : e.getConstraintViolations()) {
            var attribute = constraint.getPropertyPath().toString();
            var error = constraint.getMessage();
            errors.add("The attribute '" + attribute + "' is wrong - error: " + error);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(
            Instant.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Bad Request",
            errors
        ));
    }
}