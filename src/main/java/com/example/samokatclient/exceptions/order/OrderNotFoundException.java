package com.example.samokatclient.exceptions.order;

import com.example.samokatclient.exceptions.SamokatClientRuntimeException;

public class OrderNotFoundException extends SamokatClientRuntimeException {

    public OrderNotFoundException(String message) {
        super(message);
    }

}