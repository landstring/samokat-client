package com.example.samokatclient.exceptions.order;

import com.example.samokatclient.exceptions.SamokatClientRuntimeException;

public class CurrentOrderNotFoundException extends SamokatClientRuntimeException {

    public CurrentOrderNotFoundException(String message) {
        super(message);
    }

}