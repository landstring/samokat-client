package com.example.samokatclient.exceptions.cart;

public class CartNotFoundException extends RuntimeException{

    public CartNotFoundException() {
        super();
    }

    public CartNotFoundException(String message) {
        super(message);
    }

}