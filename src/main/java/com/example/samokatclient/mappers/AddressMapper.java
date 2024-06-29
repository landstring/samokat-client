package com.example.samokatclient.mappers;

import com.example.samokatclient.DTO.details.AddressDto;
import com.example.samokatclient.entities.user.Address;


public class AddressMapper {
    public static Address fromDto(AddressDto addressDto){
        Address address = new Address();
        address.setCity(addressDto.city);
        address.setHome(addressDto.home);
        address.setEntrance(addressDto.entrance);
        address.setApartment(addressDto.apartment);
        address.setPlate(addressDto.plate);
        return address;
    }

    public static AddressDto toDto(Address address){
        return new AddressDto(address);
    }
}
