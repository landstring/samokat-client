package com.example.samokatclient.entities.user;

import com.example.samokatclient.enums.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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

    @Field
    @Enumerated(EnumType.STRING)
    OrderStatus status;
}