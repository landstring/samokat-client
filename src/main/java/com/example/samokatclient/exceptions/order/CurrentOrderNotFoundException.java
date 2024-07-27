package com.example.samokatclient.exceptions.order;

public class CurrentOrderNotFoundException extends RuntimeException {

    public CurrentOrderNotFoundException() {
        super();
    }

    public CurrentOrderNotFoundException(String message) {
        super(message);
    }

}