package com.example.samokatclient.DTO.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonPropertyOrder({"cart", "total_price"})
public class CartDto {

    @JsonProperty("cart")
    List<CartItemDto> cartItemDtoList;

    @JsonProperty("total_price")
    Long totalPrice;
}