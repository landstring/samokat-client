package com.example.samokatclient.services;

import com.example.samokatclient.DTO.order.AddressDto;
import com.example.samokatclient.DTO.order.PaymentDto;
import com.example.samokatclient.DTO.session.UserDto;

public interface SessionService {

    String createSession();

    void authorizeUser(String sessionToken, UserDto userDto);

    AddressDto getAddress(String sessionToken);

    PaymentDto getPayment(String sessionToken);

    void setAddress(String sessionToken, String addressId);

    void setPayment(String sessionToken, String paymentId);

}
