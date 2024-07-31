package com.example.samokatclient.services;

import com.example.samokatclient.entities.user.Address;
import com.example.samokatclient.exceptions.order.AddressNotFoundException;
import com.example.samokatclient.repositories.user.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public Address getAddressById(String addressId){
        return addressRepository.findById(addressId).orElseThrow(
                () -> new AddressNotFoundException("Такого адреса не существует")
        );
    }
}
