package com.example.samokatclient.exceptions.session;

public class UserIsAlreadyAuthorized extends RuntimeException{

    public UserIsAlreadyAuthorized() {
        super();
    }

    public UserIsAlreadyAuthorized(String message) {
        super(message);
    }

}