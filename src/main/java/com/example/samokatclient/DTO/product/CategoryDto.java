package com.example.samokatclient.DTO.product;

import com.example.samokatclient.entities.product.Category;
import lombok.Data;

@Data
public class CategoryDto {
    Long id;
    String name;
    Boolean isMostChild;
    byte[] categoryImage;
    CategoryDto parent;

    public CategoryDto(Category category){
        id = category.getId();
        name = category.getName();
        isMostChild = category.getHasChildren();
        categoryImage = category.getImage().clone();
        if (category.getParent() == null){
            parent = null;
        }
        else{
            parent = new CategoryDto(category.getParent());
        }
    }
}
