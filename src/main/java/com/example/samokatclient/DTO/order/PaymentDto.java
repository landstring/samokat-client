package com.example.samokatclient.DTO.order;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentDto {
    String id;
    String cardNumber;
    String expirationDate;
    Integer cvc;
}