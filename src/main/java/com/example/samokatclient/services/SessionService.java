package com.example.samokatclient.services;

import com.example.samokatclient.DTO.cart.CartDto;
import com.example.samokatclient.DTO.details.AddressDto;
import com.example.samokatclient.DTO.details.OrderDto;
import com.example.samokatclient.DTO.details.PaymentDto;
import com.example.samokatclient.DTO.session.UserDto;
import com.example.samokatclient.exceptions.session.InvalidTokenException;
import com.example.samokatclient.mappers.AddressMapper;
import com.example.samokatclient.mappers.PaymentMapper;
import com.example.samokatclient.redis.Session;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SessionService {
    private final static String HASH_KEY = "Session";
    private final RedisTemplate redisTemplate;
    private final CartService cartService;
    private final UserService userService;

    public String createSession(){
        String token = UUID.randomUUID().toString();
        Session session = new Session(token, cartService.createCart());
        redisTemplate.opsForHash().put(HASH_KEY, token, session);
        return token;
    }

    public String getUserId(String token){
        Session session = (Session) redisTemplate.opsForHash().get(HASH_KEY, token);
        if (session == null){
            throw new InvalidTokenException();
        }
        return session.getUser_id();
    }

    public List<OrderDto> getUserOrders(String token){
        return userService.getUserOrders(this.getUserId(token));
    }

    public List<AddressDto> getUserAddress(String token){
        return userService.getUserAddress(this.getUserId(token));
    }

    public List<PaymentDto> getUserPayment(String token){
        return userService.getUserPayment(this.getUserId(token));
    }

    public void createNewAddress(String token, AddressDto addressDto){
        userService.createNewAddress(this.getUserId(token), addressDto);
    }

    public void createNewPayment(String token, PaymentDto paymentDto){
        userService.createNewPayment(this.getUserId(token), paymentDto);
    }

    public CartDto getCart(String token){
        Session session = (Session) redisTemplate.opsForHash().get(HASH_KEY, token);
        if (session == null){
            throw new InvalidTokenException();
        }
        return cartService.getCart(session.getCartToken());
    }

    public void addToCart(String token, Long product_id){
        Session session = (Session) redisTemplate.opsForHash().get(HASH_KEY, token);
        if (session == null){
            throw new InvalidTokenException();
        }
        cartService.addToCart(session.getCartToken(), product_id);
    }

    public void deleteFromCart(String token, Long product_id){
        Session session = (Session) redisTemplate.opsForHash().get(HASH_KEY, token);
        if (session == null){
            throw new InvalidTokenException();
        }
        cartService.deleteFromCart(session.getCartToken(), product_id);
    }
}

// TODO: 30.06.2024 Описать логику работы для добавления телефона к текущей сессии
// TODO: 30.06.2024 Описать логику работы для отправки заказа через Kafka
// TODO: 30.06.2024 Описать логику чтения текущих заказов пользователя