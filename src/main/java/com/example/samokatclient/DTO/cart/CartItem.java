package com.example.samokatclient.DTO.cart;

import com.example.samokatclient.DTO.product.ProductDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItem {
    ProductDto product;
    Integer count;
}