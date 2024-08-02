package com.example.samokatclient.exceptions.order;

import com.example.samokatclient.exceptions.SamokatClientRuntimeException;

public class PaymentNotFoundException extends SamokatClientRuntimeException {

    public PaymentNotFoundException(String message) {
        super(message);
    }

}