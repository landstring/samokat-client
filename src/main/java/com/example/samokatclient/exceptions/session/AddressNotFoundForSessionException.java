package com.example.samokatclient.exceptions.session;

import com.example.samokatclient.exceptions.SamokatClientRuntimeException;

public class AddressNotFoundForSessionException extends SamokatClientRuntimeException {

    public AddressNotFoundForSessionException(String message) {
        super(message);
    }

}