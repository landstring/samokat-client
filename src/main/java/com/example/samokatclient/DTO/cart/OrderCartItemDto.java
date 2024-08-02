package com.example.samokatclient.DTO.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonPropertyOrder({"product_id", "count"})
public class OrderCartItemDto {
    @JsonProperty("product_id")
    Long productId;
    Integer count;
}
