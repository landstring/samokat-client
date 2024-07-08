package com.example.samokatclient.DTO.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    public List<CartItem> cartItemList;
    public Long totalCost;
}
