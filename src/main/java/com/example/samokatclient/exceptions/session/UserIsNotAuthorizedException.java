package com.example.samokatclient.exceptions.session;

public class UserIsNotAuthorizedException extends RuntimeException{

    public UserIsNotAuthorizedException() {
        super();
    }

    public UserIsNotAuthorizedException(String message) {
        super(message);
    }

}