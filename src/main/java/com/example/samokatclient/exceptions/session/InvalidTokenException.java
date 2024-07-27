package com.example.samokatclient.exceptions.session;

public class InvalidTokenException extends RuntimeException{

    public InvalidTokenException() {
        super();
    }

    public InvalidTokenException(String message) {
        super(message);
    }

}