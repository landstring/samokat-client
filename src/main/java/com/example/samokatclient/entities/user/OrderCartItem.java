package com.example.samokatclient.entities.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderCartItem implements Serializable {

    Long productId;
    Integer count;
}