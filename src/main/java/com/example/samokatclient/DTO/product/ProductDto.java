package com.example.samokatclient.DTO.product;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDto {
    public Long id;
    public String name;
    public String description;
    public Long price;
    public String productImage_url;
    public CategoryDto category;
}