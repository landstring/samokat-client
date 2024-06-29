package com.example.samokatclient.DTO.details;

import com.example.samokatclient.entities.user.Payment;
import lombok.AllArgsConstructor;


public class PaymentDto {
    public String card_number;
    public String expiration_date;
    public Integer cvc;

    public PaymentDto(Payment payment) {
        this.card_number = payment.getCard_number();
        this.expiration_date = payment.getExpiration_date();
        this.cvc = payment.getCvc();
    }
}
