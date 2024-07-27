package com.example.samokatclient.mappers;

import com.example.samokatclient.DTO.order.PaymentDto;
import com.example.samokatclient.entities.user.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public Payment fromDto(PaymentDto paymentDto) {
        return Payment.builder()
                .id(paymentDto.getId())
                .cvc(paymentDto.getCvc())
                .card_number(paymentDto.getCard_number())
                .expiration_date(paymentDto.getExpiration_date())
                .build();
    }

    public PaymentDto toDto(Payment payment) {
        return PaymentDto.builder()
                .id(payment.getId())
                .cvc(payment.getCvc())
                .card_number(payment.getCard_number())
                .expiration_date(payment.getExpiration_date())
                .build();
    }
}