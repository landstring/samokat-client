package com.example.samokatclient.mappers;

import com.example.samokatclient.DTO.order.PaymentDto;
import com.example.samokatclient.entities.user.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public Payment fromDto(PaymentDto paymentDto) {
        return Payment.builder()
                .cvc(paymentDto.getCvc())
                .cardNumber(paymentDto.getCardNumber())
                .expirationDate(paymentDto.getExpirationDate())
                .build();
    }

    public PaymentDto toDto(Payment payment) {
        return PaymentDto.builder()
                .id(payment.getId())
                .cvc(payment.getCvc())
                .cardNumber(payment.getCardNumber())
                .expirationDate(payment.getExpirationDate())
                .build();
    }
}