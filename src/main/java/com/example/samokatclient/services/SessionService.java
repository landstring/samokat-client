package com.example.samokatclient.services;

import com.example.samokatclient.DTO.cart.CartDto;
import com.example.samokatclient.DTO.order.AddressDto;
import com.example.samokatclient.DTO.order.OrderDto;
import com.example.samokatclient.DTO.order.PaymentDto;
import com.example.samokatclient.DTO.session.UserDto;
import com.example.samokatclient.exceptions.session.*;
import com.example.samokatclient.mappers.UserMapper;
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
    private final RedisTemplate<String, Object> redisTemplate;
    private final CartService cartService;
    private final UserService userService;
    private final UserMapper userMapper;

    public String createSession() {
        String sessionToken = UUID.randomUUID().toString();
        Session session = Session.builder()
                .id(sessionToken)
                .cartToken(cartService.createCart())
                .build();
        redisTemplate.opsForHash().put(HASH_KEY, sessionToken, session);
        return sessionToken;
    }

    public UserDto getSessionUser(String sessionToken) {
        Session session = getSession(sessionToken);
        if (session.getUser_id() == null) {
            throw new UserIsNotAuthorizedException();
        }
        return userMapper.toDto(userService.getUserById(session.getUser_id()));
    }

    public void authorizeUser(String sessionToken, UserDto userDto) {
        Session session = getSession(sessionToken);
        if (session.getUser_id() != null) {
            throw new UserIsAlreadyAuthorized();
        }
        userService.authorizeUser(userDto);
        session.setUser_id(userDto.getPhone_number());
        redisTemplate.opsForHash().put(HASH_KEY, sessionToken, session);
    }

    public void setAuthorizedUserName(String sessionToken, UserDto userDto) {
        Session session = getSession(sessionToken);
        if (session.getUser_id() == null) {
            throw new UserIsNotAuthorizedException();
        }
        userService.setUserName(session.getUser_id(), userDto.getName());
    }

    public AddressDto getAddress(String sessionToken) {
        Session session = getSession(sessionToken);
        String address_id = session.getAddress_id();
        if (address_id == null) {
            throw new AddressNotFoundForSessionException();
        }
        return userService.getAddress(address_id);
    }

    public PaymentDto getPayment(String sessionToken) {
        Session session = getSession(sessionToken);
        String payment_id = session.getPayment_id();
        if (payment_id == null) {
            throw new PaymentNotFoundForSessionException();
        }
        return userService.getPayment(payment_id);
    }

    public void setAddress(String sessionToken, String address_id) {
        Session session = getSession(sessionToken);
        session.setAddress_id(address_id);
        redisTemplate.opsForHash().put(HASH_KEY, sessionToken, session);
    }

    public void setPayment(String sessionToken, String payment_id) {
        Session session = getSession(sessionToken);
        session.setPayment_id(payment_id);
        putSession(session);
    }

    public List<OrderDto> getUserOrders(String sessionToken) {
        return userService.getUserOrders(getSessionUser(sessionToken).getPhone_number());
    }

    public OrderDto getUserOrderById(String sessionToken, String order_id) {
        return userService.getOrderById(getSessionUser(sessionToken).getPhone_number(), order_id);
    }

    public List<OrderDto> getUserCurrentOrders(String sessionToken) {
        return userService.getUserCurrentOrders(getSessionUser(sessionToken).getPhone_number());
    }

    public List<AddressDto> getUserAddresses(String sessionToken) {
        return userService.getUserAddresses(this.getSessionUser(sessionToken).getPhone_number());
    }

    public List<PaymentDto> getUserPayments(String sessionToken) {
        return userService.getUserPayments(this.getSessionUser(sessionToken).getPhone_number());
    }

    public void createNewAddress(String sessionToken, AddressDto addressDto) {
        userService.createNewAddress(this.getSessionUser(sessionToken).getPhone_number(), addressDto);
    }

    public void createNewPayment(String sessionToken, PaymentDto paymentDto) {
        userService.createNewPayment(this.getSessionUser(sessionToken).getPhone_number(), paymentDto);
    }

    public CartDto getCart(String sessionToken) {
        Session session = getSession(sessionToken);
        return cartService.getCartDto(session.getCartToken());
    }

    public void addToCart(String sessionToken, Long product_id) {
        Session session = getSession(sessionToken);
        cartService.addToCart(session.getCartToken(), product_id);
    }

    public void deleteFromCart(String sessionToken, Long product_id) {
        Session session = getSession(sessionToken);
        cartService.deleteFromCart(session.getCartToken(), product_id);
    }

    public void clearCart(String sessionToken) {
        Session session = getSession(sessionToken);
        session.setCartToken(cartService.deleteCart(session.getCartToken()));
        putSession(session);
    }

    private Session getSession(String sessionToken) {
        Session session = (Session) redisTemplate.opsForHash().get(HASH_KEY, sessionToken);
        if (session == null) {
            throw new InvalidTokenException();
        }
        return session;
    }

    private void putSession(Session session) {
        redisTemplate.opsForHash().put(HASH_KEY, session.getId(), session);
    }
}
