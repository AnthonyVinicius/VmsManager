package com.claro.vmsmanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponse(Instant.now().toString(), 404, ex.getMessage())
        );
    }

    static class ErrorResponse {
        public String timestamp;
        public int status;
        public String message;

        public ErrorResponse(String timestamp, int status, String message) {
            this.timestamp = timestamp;
            this.status = status;
            this.message = message;
        }
    }
}
