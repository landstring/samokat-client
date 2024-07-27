package com.example.samokatclient.exceptions.payment;

public class BadConnectionToPaymentException extends RuntimeException {

    public BadConnectionToPaymentException() {
        super();
    }

    public BadConnectionToPaymentException(String message) {
        super(message);
    }

}