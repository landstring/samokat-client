package com.example.samokatclient.services;

import com.example.samokatclient.DTO.order.AddressDto;
import com.example.samokatclient.entities.user.Address;

import java.util.List;

public interface AddressService {
    Address getAddressById(String addressId);

    List<AddressDto> getUserAddresses(String sessionToken);

    AddressDto getUserAddress(String sessionToken, String addressId);

    void createNewAddress(String sessionToken, AddressDto addressDto);
}
