package com.example.samokatclient.services;

import com.example.samokatclient.DTO.cart.CartDto;


public interface CartService {

    CartDto getCart(String sessionToken);

    void addToCart(String sessionToken, Long productId);

    void deleteFromCart(String sessionToken, Long productId);

    void clearCart(String sessionToken);

}