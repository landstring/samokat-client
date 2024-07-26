package com.example.samokatclient.services;

import com.example.samokatclient.DTO.cart.CartDto;
import com.example.samokatclient.entities.product.Product;
import com.example.samokatclient.exceptions.cart.CartNotFoundException;
import com.example.samokatclient.exceptions.product.ProductNotFoundException;
import com.example.samokatclient.redis.Cart;
import com.example.samokatclient.mappers.CartMapper;
import com.example.samokatclient.repositories.product.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService {
    private final static String HASH_KEY = "Cart";
    private final RedisTemplate redisTemplate;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;

    public String createCart(){
        String token =UUID.randomUUID().toString();
        Cart cart = new Cart(token);
        putCart(cart);
        return token;
    }

    public CartDto getCartDto(String token){
        Cart cart = getCart(token);
        return cartMapper.cartToDto(cart);
    }

    public void addToCart(String token, Long productId){
        Cart cart = getCart(token);
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()){
            cart.addToCart(productId);
            putCart(cart);
        }
        else{
            throw new ProductNotFoundException();
        }
    }

    public void deleteFromCart(String token, Long productId){
        Cart cart = getCart(token);
        cart.deleteFromCart(productId);
        putCart(cart);
    }

    public String deleteCart(String token){
        getCart(token);                                     //проверка существования корзины
        redisTemplate.opsForHash().delete(HASH_KEY, token);
        return createCart();
    }

    private Cart getCart(String token){
        Cart cart = (Cart) redisTemplate.opsForHash().get(HASH_KEY, token);
        if (cart == null){
            throw new CartNotFoundException();
        }
        else{
            return cart;
        }
    }

    private void putCart(Cart cart){
        redisTemplate.opsForHash().put(HASH_KEY, cart.getId(), cart);
    }
}
