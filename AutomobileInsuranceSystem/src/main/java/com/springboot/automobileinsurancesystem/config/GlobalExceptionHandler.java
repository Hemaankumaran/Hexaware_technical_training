package com.springboot.automobileinsurancesystem.config;

import com.springboot.automobileinsurancesystem.exceptions.ResourceNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, Object> map = new HashMap<>();
        BindingResult bindingResult = e.getBindingResult(); // to hold error results
        List<FieldError> list = bindingResult.getFieldErrors();
        for (FieldError error : list) {
            map.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(map);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<?> handleUnexpectedTypeException(UnexpectedTypeException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(map);
    }

    @ExceptionHandler(ResourceNotFoundException.class) // my exception
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e){
        Map<String, Object> map = new HashMap<>();
        map.put("message", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(map);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", e.getMostSpecificCause().getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(map);
    }

    @ExceptionHandler(ConstraintViolationException.class) // own method, but works!! (for validation exceptions)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e){
        Map<Path, Object> map = new HashMap<>();
        Set<ConstraintViolation<?>> set = e.getConstraintViolations();
        List<ConstraintViolation<?>> list = set.stream().toList();
        for (ConstraintViolation<?> error : list) {
            map.put(error.getPropertyPath(), error.getMessage());
        }
        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(map);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException e){
        Map<String, Object> map = new HashMap<>();
        map.put("message", e.getMostSpecificCause().getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(map);
    }
}
