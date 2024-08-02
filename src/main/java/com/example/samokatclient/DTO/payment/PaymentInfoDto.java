package com.example.samokatclient.DTO.payment;

import com.example.samokatclient.DTO.order.PaymentDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentInfoDto {

    @JsonProperty("payment")
    PaymentDto paymentDto;

    @JsonProperty("total_price")
    Long totalPrice;

    String uri;
}