package com.karna.ankur.Journal.Config;

import com.mongodb.MongoSocketException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * This class handles exceptions globally for all REST controllers in the application.
 * Instead of writing try-catch blocks everywhere, this allows centralized error handling.
 */
@RestControllerAdvice // Marks this class as a global exception handler for REST controllers
public class GlobalExceptionHandler {

    /**
     * This method handles MongoSocketException, which typically occurs
     * when the application cannot connect to MongoDB Atlas (e.g., network issues or DB downtime).
     *
     * @param ex The exception that was thrown
     * @return A user-friendly error response with HTTP 503 (Service Unavailable)
     */
    @ExceptionHandler(MongoSocketException.class) // Triggers this method when a MongoSocketException is thrown
    public ResponseEntity<String> handleMongoConnectionError(MongoSocketException ex) {
        // Create and return a ResponseEntity with status 503 and a custom error message
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE) // HTTP 503 indicates the service is temporarily unavailable
                .body("Database unavailable. Please try later."); // Custom message sent to the frontend/user
    }
}
