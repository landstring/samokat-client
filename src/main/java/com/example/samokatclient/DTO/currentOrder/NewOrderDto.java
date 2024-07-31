package com.example.samokatclient.DTO.currentOrder;

import com.example.samokatclient.entities.user.OrderCartItem;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewOrderDto {
    String id;
    List<OrderCartItem> orderCartItemList;
    Long totalPrice;
    String userId;
    String addressId;
    String paymentId;
    LocalDateTime orderDateTime;
    String paymentCode;
    String status;
}