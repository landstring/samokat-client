package com.example.samokatclient.mappers;

import com.example.samokatclient.DTO.session.UserDto;
import com.example.samokatclient.entities.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(User user) {
        return UserDto.builder()
                .name(user.getName())
                .id(user.getId())
                .build();
    }

    public User fromDto(UserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .id(userDto.getId())
                .build();
    }
}