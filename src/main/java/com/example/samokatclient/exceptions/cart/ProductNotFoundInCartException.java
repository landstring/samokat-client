package com.example.samokatclient.exceptions.cart;

import com.example.samokatclient.exceptions.SamokatClientRuntimeException;

public class ProductNotFoundInCartException extends SamokatClientRuntimeException {

    public ProductNotFoundInCartException() {
        super();
    }

    public ProductNotFoundInCartException(String message) {
        super(message);
    }
}