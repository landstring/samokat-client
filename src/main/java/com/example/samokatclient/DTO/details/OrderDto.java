package com.example.samokatclient.DTO.details;

import com.example.samokatclient.DTO.cart.CartDto;

import java.time.LocalDateTime;

public class OrderDto {
    public CartDto cart;
    public AddressDto address;
    public PaymentDto payment;
    public LocalDateTime orderDateTime;
}
