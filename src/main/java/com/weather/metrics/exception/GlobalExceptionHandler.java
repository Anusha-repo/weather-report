package com.weather.metrics.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        Map<String, Object> response = new HashMap<>();
        response.put("error_code", "400");
        response.put("error_message", "Validation failed for one or more fields");
        response.put("details", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomExceptionResponse.class)
    public ResponseEntity<Object> handleCustomException(CustomExceptionResponse ex) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", ex.getStatus().value());
        responseBody.put("error", ex.getStatus().getReasonPhrase());
        responseBody.put("message", ex.getMessage());

        return new ResponseEntity<>(responseBody, ex.getStatus());
    }

}