package com.example.samokatclient.mappers;

import com.example.samokatclient.DTO.order.AddressDto;
import com.example.samokatclient.entities.user.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public Address fromDto(AddressDto addressDto) {
        return Address.builder()
                .id(addressDto.getId())
                .plate(addressDto.getPlate())
                .userId(addressDto.getUserId())
                .apartment(addressDto.getApartment())
                .city(addressDto.getCity())
                .entrance(addressDto.getEntrance())
                .home(addressDto.getHome())
                .build();
    }

    public AddressDto toDto(Address address) {
        return AddressDto.builder()
                .id(address.getId())
                .plate(address.getPlate())
                .userId(address.getUserId())
                .home(address.getHome())
                .city(address.getCity())
                .entrance(address.getEntrance())
                .apartment(address.getApartment())
                .build();
    }
}