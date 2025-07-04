package com.auction.springrestapi.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
    }

//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException e) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//    }
}

