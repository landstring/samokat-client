package com.example.samokatclient.DTO.order;

import com.example.samokatclient.DTO.cart.CartDto;
import com.example.samokatclient.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonPropertyOrder({"id", "cart", "address", "payment", "order_date_time", "status"})
public class OrderDto {

    String id;

    @JsonProperty("cart")
    CartDto cartDto;

    @JsonProperty("address")
    AddressDto addressDto;

    @JsonProperty("payment")
    PaymentDto paymentDto;

    @JsonProperty("order_date_time")
    LocalDateTime orderDateTime;

    OrderStatus status;
}