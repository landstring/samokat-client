package com.example.samokatclient.exceptions.session;

public class PaymentNotFoundForSessionException extends RuntimeException{

    public PaymentNotFoundForSessionException() {
        super();
    }

    public PaymentNotFoundForSessionException(String message) {
        super(message);
    }

}