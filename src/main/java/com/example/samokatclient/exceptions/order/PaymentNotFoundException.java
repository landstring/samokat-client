package com.example.samokatclient.exceptions.order;

public class PaymentNotFoundException extends RuntimeException {

    public PaymentNotFoundException() {
        super();
    }

    public PaymentNotFoundException(String message) {
        super(message);
    }

}