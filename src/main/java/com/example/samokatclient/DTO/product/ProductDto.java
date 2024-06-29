package com.example.samokatclient.DTO.product;

import com.example.samokatclient.entities.product.Product;
import lombok.Data;

@Data
public class ProductDto {
    Long id;
    String name;
    String description;
    Long price;
    byte[] productImage;
    CategoryDto category;

    public ProductDto(Product product){
        id = product.getId();
        name = product.getName();
        description = product.getDescription();
        price = product.getPrice();
        productImage = product.getImage().clone();
        category = new CategoryDto(product.getCategory());
    }
}
