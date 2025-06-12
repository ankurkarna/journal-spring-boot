package com.karna.ankur.Journal.Config;

import com.mongodb.MongoSocketException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MongoSocketException.class)
    public ResponseEntity<String> handleMongoConnectionError(MongoSocketException ex) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Database unavailable. Please try later.");
    }
}