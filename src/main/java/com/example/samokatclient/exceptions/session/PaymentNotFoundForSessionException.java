package com.example.samokatclient.exceptions.session;

import com.example.samokatclient.exceptions.SamokatClientRuntimeException;

public class PaymentNotFoundForSessionException extends SamokatClientRuntimeException {

    public PaymentNotFoundForSessionException(String message) {
        super(message);
    }

}