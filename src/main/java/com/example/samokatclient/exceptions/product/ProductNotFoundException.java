package com.example.samokatclient.exceptions.product;

import com.example.samokatclient.exceptions.SamokatClientRuntimeException;

public class ProductNotFoundException extends SamokatClientRuntimeException {

    public ProductNotFoundException() {
        super();
    }

    public ProductNotFoundException(String message) {
        super(message);
    }

}