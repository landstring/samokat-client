package com.example.samokatclient.DTO.details;

import com.example.samokatclient.entities.user.Address;
import lombok.AllArgsConstructor;

public class AddressDto {
    public String city;
    public String home;
    public String apartment;
    public String entrance;
    public Integer plate;

    public AddressDto(Address address){
        this.city = address.getCity();
        this.home = address.getHome();
        this.apartment = address.getApartment();
        this.entrance = address.getEntrance();
        this.plate = address.getPlate();
    }
}
