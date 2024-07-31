package com.example.samokatclient.services;

import com.example.samokatclient.entities.user.Payment;
import com.example.samokatclient.exceptions.order.PaymentNotFoundException;
import com.example.samokatclient.repositories.user.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public Payment getPaymentById(String paymentId){
        return paymentRepository.findById(paymentId).orElseThrow(
                () -> new PaymentNotFoundException("Такого способа оплаты не существует")
        );
    }
}
