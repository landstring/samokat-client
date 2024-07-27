package com.example.samokatclient.exceptions.cart;

public class ProductNotFoundInCartException extends RuntimeException {

    public ProductNotFoundInCartException() {
        super();
    }

    public ProductNotFoundInCartException(String message) {
        super(message);
    }

}