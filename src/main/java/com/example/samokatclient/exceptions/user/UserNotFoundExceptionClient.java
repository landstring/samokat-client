package com.example.samokatclient.exceptions.user;

import com.example.samokatclient.exceptions.SamokatClientRuntimeException;

public class UserNotFoundExceptionClient extends SamokatClientRuntimeException {

    public UserNotFoundExceptionClient() {
        super();
    }

    public UserNotFoundExceptionClient(String message) {
        super(message);
    }

}