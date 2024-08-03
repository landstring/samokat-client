package com.example.samokatclient.services;

import com.example.samokatclient.DTO.currentOrder.CurrentOrderClientDto;

import java.util.List;

public interface CurrentOrderService {

    String createCurrentOrder(String sessionToken);

    CurrentOrderClientDto getCurrentOrderById(String sessionToken, String orderId);

    void cancelCurrentOrder(String sessionToken, String orderId);

    List<CurrentOrderClientDto> getCurrentOrders(String sessionToken);

}
