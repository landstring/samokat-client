package com.example.samokatclient.exceptions.session;

import com.example.samokatclient.exceptions.SamokatClientRuntimeException;

public class InvalidTokenException extends SamokatClientRuntimeException {

    public InvalidTokenException(String message) {
        super(message);
    }

}