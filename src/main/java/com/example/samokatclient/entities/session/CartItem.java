package com.example.samokatclient.entities.session;

import com.example.samokatclient.entities.product.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItem implements Serializable {
    Product product;
    Integer count;
}