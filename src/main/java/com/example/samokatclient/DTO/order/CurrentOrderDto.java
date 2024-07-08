package com.example.samokatclient.DTO.order;

import com.example.samokatclient.DTO.cart.CartDto;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@NoArgsConstructor
public class CurrentOrderDto {
    public String id;
    public CartDto cartDto;
    public String address_id;
    public String payment_id;
    public LocalDateTime orderDateTime;
    public String status;

    public CurrentOrderDto(String id,
                           CartDto cartDto,
                           String address_id,
                           String payment_id,
                           LocalDateTime orderDateTime,
                           String status) {
        this.cartDto = cartDto;
        this.address_id = address_id;
        this.payment_id = payment_id;
        this.orderDateTime = orderDateTime;
        this.status = status;
    }
}
