package com.example.samokatclient.DTO.cart;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartDto {
    List<CartItem> cartItemList;
    Long totalPrice;
}