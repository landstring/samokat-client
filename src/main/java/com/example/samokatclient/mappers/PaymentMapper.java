package com.example.samokatclient.mappers;

import com.example.samokatclient.DTO.details.PaymentDto;
import com.example.samokatclient.entities.user.Payment;

public class PaymentMapper {
    public static Payment fromDto(PaymentDto paymentDto){
        Payment payment = new Payment();
        payment.setCard_number(paymentDto.card_number);
        payment.setExpiration_date(paymentDto.expiration_date);
        payment.setCvc(paymentDto.cvc);
        return payment;
    }

    public static PaymentDto toDto(Payment payment){
        return new PaymentDto(payment);
    }
}
