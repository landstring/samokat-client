package com.example.samokatclient.exceptions.cart;

public class CartIsEmptyException extends RuntimeException {
    public CartIsEmptyException() {
    }

    public CartIsEmptyException(String message) {
        super(message);
    }
}
