package com.example.samokatclient.services;

import com.example.samokatclient.DTO.order.PaymentDto;
import com.example.samokatclient.entities.user.Payment;

import java.util.List;

public interface PaymentService {

    public Payment getPaymentById(String paymentId);

    List<PaymentDto> getUserPayments(String sessionToken);

    PaymentDto getUserPayment(String sessionToken, String paymentId);

    void createNewPayment(String sessionToken, PaymentDto paymentDto);
}
