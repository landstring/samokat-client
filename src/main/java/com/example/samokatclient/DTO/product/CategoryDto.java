package com.example.samokatclient.DTO.product;

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
@JsonPropertyOrder({"id", "name", "image", "children"})
public class CategoryDto {

    Long id;
    String name;

    @JsonProperty("image")
    String categoryImageUrl;

    List<CategoryDto> children;
}