package com.example.samokatclient.exceptions.payment;

import com.example.samokatclient.exceptions.SamokatClientRuntimeException;

public class BadConnectionToPaymentException extends SamokatClientRuntimeException {

    public BadConnectionToPaymentException(String message) {
        super(message);
    }

}