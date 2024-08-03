package com.example.samokatclient.services.Impl;

import com.example.samokatclient.DTO.order.AddressDto;
import com.example.samokatclient.entities.session.Session;
import com.example.samokatclient.entities.user.Address;
import com.example.samokatclient.entities.user.User;
import com.example.samokatclient.exceptions.order.AddressNotFoundException;
import com.example.samokatclient.exceptions.session.InvalidTokenException;
import com.example.samokatclient.exceptions.session.UserIsNotAuthorizedException;
import com.example.samokatclient.mappers.AddressMapper;
import com.example.samokatclient.repositories.session.SessionRepository;
import com.example.samokatclient.repositories.user.AddressRepository;
import com.example.samokatclient.services.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final SessionRepository sessionRepository;
    private final AddressMapper addressMapper;
    public Address getAddressById(String addressId) {
        return addressRepository.findById(addressId).orElseThrow(
                () -> new AddressNotFoundException("Такого адреса не существует")
        );
    }

    @Override
    public List<AddressDto> getUserAddresses(String sessionToken) {
        User user = getSessionUser(sessionToken);
        return addressRepository
                .findByUserId(user.getId())
                .stream()
                .map(addressMapper::toDto)
                .toList();
    }

    @Override
    public AddressDto getUserAddress(String sessionToken, String addressId) {
        User user = getSessionUser(sessionToken);
        Address address = addressRepository.findByIdAndUserId(addressId, user.getId()).orElseThrow(
                () -> new AddressNotFoundException("Такого адреса не существует, или он создан другим пользователем")
        );
        return addressMapper.toDto(address);
    }

    @Override
    public void createNewAddress(String sessionToken, AddressDto addressDto) {
        String addressId;
        do {
            addressId = UUID.randomUUID().toString();
        } while (addressRepository.existsById(addressId));
        User user = getSessionUser(sessionToken);
        Address address = addressMapper.fromDto(addressDto);
        address.setId(addressId);
        address.setUserId(user.getId());
        addressRepository.save(address);
    }

    private User getSessionUser(String sessionToken) {
        Session session = sessionRepository.findById(sessionToken).orElseThrow(
                () -> new InvalidTokenException("Неверный ключ сессии")
        );
        if (session.getUser() == null) {
            throw new UserIsNotAuthorizedException("Пользователь не авторизован");
        }
        return session.getUser();
    }
}