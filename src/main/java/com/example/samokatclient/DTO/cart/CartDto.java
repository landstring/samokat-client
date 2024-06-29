package com.example.samokatclient.DTO.cart;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CartDto {
    public List<CartItem> cartItemList;
    public Long totalCost;

}
