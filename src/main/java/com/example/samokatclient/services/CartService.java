package com.example.samokatclient.services;

import com.example.samokatclient.DTO.cart.CartDto;
import com.example.samokatclient.entities.product.Product;
import com.example.samokatclient.entities.session.Cart;
import com.example.samokatclient.entities.session.CartItem;
import com.example.samokatclient.entities.session.Session;
import com.example.samokatclient.exceptions.cart.ProductNotFoundInCartException;
import com.example.samokatclient.exceptions.product.ProductNotFoundException;
import com.example.samokatclient.exceptions.session.InvalidTokenException;
import com.example.samokatclient.mappers.CartMapper;
import com.example.samokatclient.repositories.product.ProductRepository;
import com.example.samokatclient.repositories.session.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class CartService {

    private final SessionRepository sessionRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;

    public CartDto getCart(String sessionToken) {
        Session session = getSession(sessionToken);
        return cartMapper.cartToDto(session.getCart());
    }

    public void addToCart(String sessionToken, Long productId) {
        Session session = getSession(sessionToken);
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException("Такого продукта не существует")
        );
        Cart cart = addToCart(session.getCart(), product);
        session.setCart(cart);
        sessionRepository.save(session);
    }

    public void deleteFromCart(String sessionToken, Long productId) {
        Session session = getSession(sessionToken);
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException("Такого продукта не существует")
        );
        Cart cart = deleteFromCart(session.getCart(), product);
        session.setCart(cart);
        sessionRepository.save(session);
    }

    public void clearCart(String sessionToken) {
        Session session = getSession(sessionToken);
        session.setCart(Cart.builder()
                .products(new HashMap<Long, CartItem>())
                .build());
        sessionRepository.save(session);
    }

    private Cart addToCart(Cart cart, Product product) {
        Map<Long, CartItem> products = cart.getProducts();
        if (products.containsKey(product.getId())) {
            CartItem cartItem = products.get(product.getId());
            cartItem.setCount(cartItem.getCount() + 1);
            products.put(product.getId(), cartItem);
        } else {
            products.put(
                    product.getId(),
                    CartItem.builder()
                            .product(product)
                            .count(1)
                            .build());
        }
        cart.setProducts(products);
        return cart;
    }

    private Cart deleteFromCart(Cart cart, Product product) {
        Map<Long, CartItem> products = cart.getProducts();
        if (products.containsKey(product.getId())) {
            CartItem cartItem = products.get(product.getId());
            if (cartItem.getCount() == 1) {
                products.remove(product.getId());
            } else {
                cartItem.setCount(cartItem.getCount() - 1);
                products.put(product.getId(), cartItem);
            }
        } else {
            throw new ProductNotFoundInCartException("Такого продукта нет в корзине");
        }
        cart.setProducts(products);
        return cart;
    }

    private Session getSession(String sessionToken) {
        return sessionRepository.findById(sessionToken).orElseThrow(
                () -> new InvalidTokenException("Неверный ключ сессии")
        );
    }
}