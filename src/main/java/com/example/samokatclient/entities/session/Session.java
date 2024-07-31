package com.example.samokatclient.entities.session;

import com.example.samokatclient.entities.user.Address;
import com.example.samokatclient.entities.user.Payment;
import com.example.samokatclient.entities.user.User;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Session")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Session implements Serializable {
    @Id
    String id;
    User user;
    Cart cart;
    Address address;
    Payment payment;
}