package com.example.samokatclient.DTO.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonPropertyOrder({"id", "name", "description", "price", "image", "category"})
public class ProductDto {

    Long id;
    String name;
    String description;
    Long price;

    @JsonProperty("image")
    String productImageUrl;

    CategoryDto category;
}