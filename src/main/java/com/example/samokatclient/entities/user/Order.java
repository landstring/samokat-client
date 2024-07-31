package com.example.samokatclient.entities.user;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order implements Serializable {
    @Id
    String id;

    List<OrderCartItem> orderCartItemList;
    Long totalPrice;
    @Indexed
    String userId;
    String addressId;
    String paymentId;

    LocalDateTime orderDateTime;
    String status;
}