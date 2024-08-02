package com.example.samokatclient.exceptions.order;

import com.example.samokatclient.exceptions.SamokatClientRuntimeException;

public class AddressNotFoundException extends SamokatClientRuntimeException {

    public AddressNotFoundException(String message) {
        super(message);
    }

}