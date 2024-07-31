package com.example.samokatclient.DTO.currentOrder;

import com.example.samokatclient.DTO.cart.CartDto;
import com.example.samokatclient.DTO.order.AddressDto;
import com.example.samokatclient.DTO.order.PaymentDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CurrentOrderClientDto {
    String id;
    CartDto cartDto;
    AddressDto addressDto;
    PaymentDto paymentDto;
    LocalDateTime orderDateTime;
    String paymentCode;
    String status;
}
