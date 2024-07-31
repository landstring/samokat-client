package com.example.samokatclient.mappers;

import com.example.samokatclient.DTO.order.AddressDto;
import com.example.samokatclient.entities.user.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public Address fromDto(AddressDto addressDto) {
        return Address.builder()
                .city(addressDto.getCity())
                .home(addressDto.getHome())
                .apartment(addressDto.getApartment())
                .entrance(addressDto.getEntrance())
                .plate(addressDto.getPlate())
                .build();
    }

    public AddressDto toDto(Address address) {
        return AddressDto.builder()
                .id(address.getId())
                .city(address.getCity())
                .home(address.getHome())
                .apartment(address.getApartment())
                .entrance(address.getEntrance())
                .plate(address.getPlate())
                .build();
    }
}