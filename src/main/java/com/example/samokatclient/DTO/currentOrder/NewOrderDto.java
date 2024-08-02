package com.example.samokatclient.DTO.currentOrder;

import com.example.samokatclient.DTO.cart.OrderCartItemDto;
import com.example.samokatclient.entities.user.OrderCartItem;
import com.example.samokatclient.enums.CurrentOrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("cart")
    List<OrderCartItemDto> orderCartItemList;

    @JsonProperty("total_price")
    Long totalPrice;

    @JsonProperty("user_id")
    String userId;

    @JsonProperty("address_id")
    String addressId;

    @JsonProperty("payment_id")
    String paymentId;

    @JsonProperty("order_date_time")
    LocalDateTime orderDateTime;

    @JsonProperty("payment_code")
    String paymentCode;

    CurrentOrderStatus status;
}