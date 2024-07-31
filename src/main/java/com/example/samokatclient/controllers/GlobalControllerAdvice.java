package com.example.samokatclient.controllers;

import com.example.samokatclient.exceptions.SamokatClientRuntimeException;
import com.example.samokatclient.exceptions.cart.CartIsEmptyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(SamokatClientRuntimeException.class)
    public ResponseEntity<?> handleSamokatRuntimeException(SamokatClientRuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(CartIsEmptyException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
