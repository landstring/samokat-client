package com.example.samokatclient.DTO.payment;

import com.example.samokatclient.DTO.order.PaymentDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentInfoDto {
    PaymentDto paymentDto;
    Long totalPrice;
    String uri;
}