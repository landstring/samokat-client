package com.example.samokatclient.controllers;

import com.example.samokatclient.exceptions.SamokatClientRuntimeException;
import com.example.samokatclient.exceptions.cart.CartIsEmptyException;
import com.example.samokatclient.exceptions.session.UserIsNotAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(UserIsNotAuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUserIsNotAuthorizedException(UserIsNotAuthorizedException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(CartIsEmptyException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleCartIsEmptyException(CartIsEmptyException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(SamokatClientRuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleSamokatRuntimeException(SamokatClientRuntimeException exception) {
        return exception.getMessage();
    }

}
