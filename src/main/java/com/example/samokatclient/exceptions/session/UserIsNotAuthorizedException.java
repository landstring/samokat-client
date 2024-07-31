package com.example.samokatclient.exceptions.session;

import com.example.samokatclient.exceptions.SamokatClientRuntimeException;

public class UserIsNotAuthorizedException extends SamokatClientRuntimeException {

    public UserIsNotAuthorizedException() {
        super();
    }

    public UserIsNotAuthorizedException(String message) {
        super(message);
    }
}