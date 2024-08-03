package com.example.samokatclient.services.Impl;

import com.example.samokatclient.DTO.order.OrderDto;
import com.example.samokatclient.DTO.session.UserDto;
import com.example.samokatclient.entities.session.Session;
import com.example.samokatclient.entities.user.Order;
import com.example.samokatclient.exceptions.order.OrderNotFoundException;
import com.example.samokatclient.exceptions.session.InvalidTokenException;
import com.example.samokatclient.exceptions.session.UserIsNotAuthorizedException;
import com.example.samokatclient.mappers.OrderMapper;
import com.example.samokatclient.mappers.UserMapper;
import com.example.samokatclient.repositories.session.SessionRepository;
import com.example.samokatclient.repositories.user.OrderRepository;
import com.example.samokatclient.services.UserService;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final OrderRepository orderRepository;
    private final SessionRepository sessionRepository;
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;
    @Override
    public UserDto getSessionUser(String sessionToken) {
        Session session = sessionRepository.findById(sessionToken).orElseThrow(
                () -> new InvalidTokenException("Неверный ключ сессии")
        );
        if (session.getUser() == null) {
            throw new UserIsNotAuthorizedException("Пользователь не авторизован");
        }
        return userMapper.toDto(session.getUser());
    }

    @Override
    public List<OrderDto> getUserOrders(String sessionToken) {
        UserDto userDto = getSessionUser(sessionToken);
        return orderRepository
                .findByUserId(userDto.getId())
                .stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public OrderDto getOrderById(String sessionToken, String orderId) {
        UserDto userDto = getSessionUser(sessionToken);
        Order order = orderRepository.findByIdAndUserId(orderId, userDto.getId()).orElseThrow(
                () -> new OrderNotFoundException("Такого заказа не существует, или он создан другим пользователем")
        );
        return orderMapper.toDto(order);
    }

}