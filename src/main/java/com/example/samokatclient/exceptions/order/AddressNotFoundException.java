package com.example.samokatclient.exceptions.order;

public class AddressNotFoundException extends RuntimeException{

    public AddressNotFoundException() {
        super();
    }

    public AddressNotFoundException(String message) {
        super(message);
    }

}