package com.example.samokatclient.services;

import com.example.samokatclient.DTO.product.CategoryDto;
import com.example.samokatclient.DTO.product.ProductDto;
import com.example.samokatclient.entities.product.Category;
import com.example.samokatclient.entities.product.Product;

import java.util.List;

public interface ProductService {

    Product getProductById(Long productId);

    Category getCategoryById(Long categoryId);

    ProductDto getProduct(Long productId);

    List<ProductDto> getAllProductsPage(int pageNumber, int pageSize);

    List<CategoryDto> getAllCategories();

    List<ProductDto> searchProductsByKeywords(String keywords, int pageNumber, int pageSize);

    List<ProductDto> getAllProductsFromCategory(Long categoryId, int pageNumber, int pageSize);
}
