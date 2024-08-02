package com.example.samokatclient.DTO.cart;

import com.example.samokatclient.DTO.product.ProductDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonPropertyOrder({"product", "count"})
public class CartItemDto {

    @JsonProperty("product")
    ProductDto productDto;

    Integer count;
}