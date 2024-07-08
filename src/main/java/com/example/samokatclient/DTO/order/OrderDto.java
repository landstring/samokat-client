package com.example.samokatclient.DTO.order;

import com.example.samokatclient.DTO.cart.CartDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@NoArgsConstructor
public class OrderDto {
    public CartDto cart;
    public AddressDto address;
    public PaymentDto payment;
    public LocalDateTime orderDateTime;
}
