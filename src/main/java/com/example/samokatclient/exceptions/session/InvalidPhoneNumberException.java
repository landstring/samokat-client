package com.example.samokatclient.exceptions.session;

import com.example.samokatclient.exceptions.SamokatClientRuntimeException;

public class InvalidPhoneNumberException extends SamokatClientRuntimeException {

    public InvalidPhoneNumberException(String message) {
        super(message);
    }

}
