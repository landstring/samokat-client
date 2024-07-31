package com.example.samokatclient.exceptions.cart;

import com.example.samokatclient.exceptions.SamokatClientRuntimeException;

public class CartIsEmptyException extends SamokatClientRuntimeException {
    public CartIsEmptyException() {
        super();
    }

    public CartIsEmptyException(String message) {
        super(message);
    }
}
