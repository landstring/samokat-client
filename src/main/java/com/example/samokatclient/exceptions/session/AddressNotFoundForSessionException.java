package com.example.samokatclient.exceptions.session;

public class AddressNotFoundForSessionException extends RuntimeException{

    public AddressNotFoundForSessionException() {
        super();
    }

    public AddressNotFoundForSessionException(String message) {
        super(message);
    }

}