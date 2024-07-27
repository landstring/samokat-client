package com.example.samokatclient.exceptions.product;

public class CategoryNotFoundException extends RuntimeException{

    public CategoryNotFoundException() {
        super();
    }

    public CategoryNotFoundException(String message) {
        super(message);
    }

}