package com.example.samokatclient.services;

import com.example.samokatclient.DTO.order.OrderDto;
import com.example.samokatclient.DTO.session.UserDto;

import java.util.List;

public interface UserService {

    UserDto getSessionUser(String sessionToken);

    List<OrderDto> getUserOrders(String sessionToken);

    OrderDto getOrderById(String sessionToken, String orderId);

}
