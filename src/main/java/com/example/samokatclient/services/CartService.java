package com.example.samokatclient.services;

import com.example.samokatclient.DTO.cart.CartDto;
import com.example.samokatclient.exceptions.cart.CartNotFoundException;
import com.example.samokatclient.redis.Cart;
import com.example.samokatclient.mappers.CartMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService {
    private final static String HASH_KEY = "Cart";
    private final RedisTemplate redisTemplate;
    private final CartMapper cartMapper;

    public String createCart(){
        String token =UUID.randomUUID().toString();
        Cart cart = new Cart(token);
        redisTemplate.opsForHash().put(HASH_KEY, token, cart);
        return token;
    }

    public CartDto getCart(String token){
        Cart cart = (Cart) redisTemplate.opsForHash().get(HASH_KEY, token);
        if (cart == null){
            throw new CartNotFoundException();
        }
        return cartMapper.cartToDto(cart);
    }

    public void addToCart(String token, Long productId){
        Cart cart = (Cart) redisTemplate.opsForHash().get(HASH_KEY, token);
        if (cart == null){
            throw new CartNotFoundException();
        }
        cart.addToCart(productId);
    }

    public void deleteFromCart(String token, Long productId){
        Cart cart = (Cart) redisTemplate.opsForHash().get(HASH_KEY, token);
        if (cart == null){
            throw new CartNotFoundException();
        }
        cart.deleteFromCart(productId);
    }

    public void deleteCart(String token){
        Cart cart = (Cart) redisTemplate.opsForHash().get(HASH_KEY, token);
        if (cart == null){
            throw new CartNotFoundException();
        }
        redisTemplate.opsForHash().delete(HASH_KEY, token);
    }
}
