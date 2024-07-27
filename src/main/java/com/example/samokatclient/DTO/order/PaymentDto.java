package com.example.samokatclient.DTO.order;

import com.example.samokatclient.entities.user.Payment;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentDto {
    String id;
    String card_number;
    String expiration_date;
    Integer cvc;

}