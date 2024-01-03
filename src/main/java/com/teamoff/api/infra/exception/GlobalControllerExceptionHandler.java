package com.teamoff.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    private final String REGEX_ERROR = "\\[ERROR:.*?\\]\\.";
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public Map<String, String> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(ex.getName(), ex.getMessage());
        return error;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ResourceNotFoundException.class})
    public Map<String, String> handleUserNotFoundException(
            ResourceNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return error;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleSqlException (DataIntegrityViolationException ex){
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @ExceptionHandler({EntityNotFoundException.class, NoHandlerFoundException.class})
    public ResponseEntity<Object> tratarErro404() {
        return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public Map<String, String> handleMessageNotReadableException(
            HttpMessageNotReadableException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return error;
    }

    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<Map<String, String>> handleResponseStatusException(
            ResponseStatusException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getReason());
        return new ResponseEntity<>(error, ex.getStatusCode());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public Map<String, String> handleException(
            Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return error;
    }


}
