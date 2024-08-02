package com.example.samokatclient.exceptions.session;

import com.example.samokatclient.exceptions.SamokatClientRuntimeException;

public class UserIsAlreadyAuthorized extends SamokatClientRuntimeException {

    public UserIsAlreadyAuthorized(String message) {
        super(message);
    }

}