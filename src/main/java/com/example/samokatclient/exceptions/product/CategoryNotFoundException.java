package com.example.samokatclient.exceptions.product;

import com.example.samokatclient.exceptions.SamokatClientRuntimeException;

public class CategoryNotFoundException extends SamokatClientRuntimeException {

    public CategoryNotFoundException() {
        super();
    }

    public CategoryNotFoundException(String message) {
        super(message);
    }

}