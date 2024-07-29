package com.example.samokatclient.DTO.order;

import com.example.samokatclient.DTO.cart.OrderCartItem;
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
    String user_id;
    String address_id;
    String payment_id;
    LocalDateTime orderDateTime;
    String payment_code;
    String status;
}