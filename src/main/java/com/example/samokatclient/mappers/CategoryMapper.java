package com.example.samokatclient.mappers;

import com.example.samokatclient.DTO.product.CategoryDto;
import com.example.samokatclient.entities.product.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDto toDto(Category category){
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .categoryImage_url(category.getImage_url())
                .children(category.getChildren().stream().map(this::toDto).toList())
                .build();
    }

}
