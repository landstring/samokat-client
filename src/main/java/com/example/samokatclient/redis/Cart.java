package com.example.samokatclient.redis;

import com.example.samokatclient.exceptions.cart.ProductNotFoundInCartException;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.TreeMap;

@RedisHash("Cart")
@Getter
public class Cart implements Serializable {
    @Id
    private String id;
    private final TreeMap<Long, Integer> products;

    public Cart(String id) {
        this.id = id;
        products = new TreeMap<>();
    }

    public void addToCart(Long productId){
        if (products.containsKey(productId)){
            products.put(productId, products.get(productId) + 1);
        }
        else{
            products.put(productId, 1);
        }
    }

    public void deleteFromCart(Long productId){
        if (products.containsKey(productId)){
            if (products.get(productId) == 1){
                products.remove(productId);
            }
            else{
                products.put(productId, products.get(productId) - 1);
            }
        }
        else{
            throw new ProductNotFoundInCartException();
        }
    }
}
