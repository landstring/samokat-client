package com.example.samokatclient.entities.currentOrder;

import com.example.samokatclient.entities.session.Cart;
import com.example.samokatclient.entities.user.Address;
import com.example.samokatclient.entities.user.Payment;
import com.example.samokatclient.enums.CurrentOrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.time.LocalDateTime;

@RedisHash("CurrentOrderClient")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CurrentOrderClient implements Serializable {
    String id;
    Cart cart;
    @Indexed
    String userId;
    Address address;
    Payment payment;
    LocalDateTime orderDateTime;
    String paymentCode;
    CurrentOrderStatus status;
}