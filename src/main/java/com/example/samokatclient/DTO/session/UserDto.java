package com.example.samokatclient.DTO.session;

import lombok.Data;

@Data
public class UserDto {
    String phone_number;
    String name;

    public UserDto(String phone_number, String name){
        this.phone_number = phone_number;
        this.name = name;
    }
}
