package com.example.samokatclient.mappers;

import com.example.samokatclient.DTO.product.ProductDto;
import com.example.samokatclient.entities.product.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductMapper {
    private final CategoryMapper categoryMapper;

    public ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .productImageUrl(product.getImageUrl())
                .category(categoryMapper.toDto(product.getCategory()))
                .build();
    }
}
