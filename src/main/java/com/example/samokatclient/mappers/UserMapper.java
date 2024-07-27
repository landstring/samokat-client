package com.example.samokatclient.mappers;

import com.example.samokatclient.DTO.session.UserDto;
import com.example.samokatclient.entities.user.SamokatUser;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(SamokatUser samokatUser) {
        return UserDto.builder()
                .name(samokatUser.getName())
                .phone_number(samokatUser.getPhone())
                .build();
    }

    public SamokatUser fromDto(UserDto userDto) {
        return SamokatUser.builder()
                .name(userDto.getName())
                .phone(userDto.getPhone_number())
                .build();
    }
}