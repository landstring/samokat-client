package com.example.samokatclient.DTO.order;

import com.example.samokatclient.DTO.cart.CartDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDto {
    String id;
    CartDto cartDto;
    Long totalPrice;
    AddressDto addressDto;
    PaymentDto paymentDto;
    LocalDateTime orderDateTime;
    String status;
}