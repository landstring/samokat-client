package com.example.samokatclient.DTO.currentOrder;

import com.example.samokatclient.DTO.cart.CartDto;
import com.example.samokatclient.DTO.order.AddressDto;
import com.example.samokatclient.DTO.order.PaymentDto;
import com.example.samokatclient.enums.CurrentOrderStatus;
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
@JsonPropertyOrder({"id", "cart", "address", "payment", "order_date_time", "payment_code", "status"})
public class CurrentOrderClientDto {

    String id;
    @JsonProperty("cart")
    CartDto cartDto;

    @JsonProperty("address")
    AddressDto addressDto;

    @JsonProperty("payment")
    PaymentDto paymentDto;

    @JsonProperty("order_date_time")
    LocalDateTime orderDateTime;

    @JsonProperty("payment_code")
    String paymentCode;

    CurrentOrderStatus status;
}
