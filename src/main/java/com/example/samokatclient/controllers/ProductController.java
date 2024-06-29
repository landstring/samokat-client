package com.example.samokatclient.controllers;

import com.example.samokatclient.DTO.product.CategoryDto;
import com.example.samokatclient.DTO.product.ProductDto;
import com.example.samokatclient.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/categories")
    private List<CategoryDto> getCategories() {
        return productService.getAllMainCategories();
    }

    @GetMapping ("/categories/{category_id}")
    private List<ProductDto> getCategories(@PathVariable("category_id") Long category_id,
                                           @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                           @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        return productService.getAllProductsFromCategory(category_id, pageNumber, pageSize);
    }

    @GetMapping ("/products")
    private List<ProductDto> getProducts(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        return productService.getAllProductsPage(pageNumber, pageSize);
    }

    @GetMapping ("/products/{product_id}")
    private ProductDto getProduct(@PathVariable("product_id") Long product_id) {

        return productService.getProductById(product_id);
    }
    @GetMapping ("/products/find")
    private List<ProductDto> getProducts(@RequestParam("search") String search,
                                         @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        return productService.searchProductsByKeywords(search, pageNumber, pageSize);
    }


}
