package com.example.samokatclient.DTO.cart;

import com.example.samokatclient.DTO.product.ProductDto;

public class CartItem {
    public ProductDto product;
    public Integer count;

    public CartItem(ProductDto product, Integer count){
        this.product = product;
        this.count = count;
    }
}
