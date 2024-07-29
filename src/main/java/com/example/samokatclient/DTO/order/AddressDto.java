package com.example.samokatclient.DTO.order;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressDto {
    String id;
    String city;
    String home;
    String apartment;
    String entrance;
    Integer plate;
    String userId;
}