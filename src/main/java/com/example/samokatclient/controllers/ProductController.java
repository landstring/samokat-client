package com.example.samokatclient.controllers;

import com.example.samokatclient.DTO.product.CategoryDto;
import com.example.samokatclient.DTO.product.ProductDto;
import com.example.samokatclient.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Методы для получения информации о продуктах")
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class ProductController {
    private final ProductService productService;

    @Operation(
            summary = "Вывести все категории",
            description = "Категории выводятся иерархической структурой"
    )
    @GetMapping("/categories")
    @ResponseStatus(HttpStatus.OK)
    private List<CategoryDto> getCategories() {
        log.info("Запрос на вывод всего дерева категорий продуктов");
        return productService.getAllCategories();
    }

    @Operation(
            summary = "Вывести продукты категории",
            description =
                    "Выводит все продукты для данной категории. Если у категории есть дочерние," +
                            " возьмёт продукты из каждой, объединив их в список"
    )
    @GetMapping("/categories/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    private List<ProductDto> getCategories(
            @Parameter(description = "ID категории")
            @PathVariable("categoryId") Long categoryId,

            @Parameter(description = "Номер страницы")
            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,

            @Parameter(description = "Размер страницы")
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        log.info("Запрос на вывод всех продуктов из категории с id: {}, страница: {}, размер страницы: {}",
                categoryId, pageNumber, pageSize);
        return productService.getAllProductsFromCategory(categoryId, pageNumber, pageSize);
    }

    @Operation(
            summary = "Вывести продукты",
            description =
                    "Выводит все продукты, которые представлены в каталоге"
    )
    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    private List<ProductDto> getProducts(
            @Parameter(description = "Номер страницы")
            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,

            @Parameter(description = "Размер страницы")
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        log.info("Запрос на вывод всех продуктов, страница: {}, размер страницы: {}", pageNumber, pageSize);
        return productService.getAllProductsPage(pageNumber, pageSize);
    }

    @Operation(
            summary = "Вывести продукт",
            description = "Выводит информацию о конкретном продукте"
    )
    @GetMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.OK)
    private ProductDto getProduct(
            @Parameter(description = "ID продукта")
            @PathVariable("productId") Long productId) {
        log.info("Запрос на вывод информации о продукте с id {}", productId);
        return productService.getProduct(productId);
    }

    @Operation(
            summary = "Поиск продукта",
            description = "Ищет продукты по ключевым словам запроса"
    )
    @GetMapping("/products/find")
    @ResponseStatus(HttpStatus.OK)
    private List<ProductDto> getProducts(
            @Parameter(description = "Поисковой запрос")
            @RequestParam("search") String search,

            @Parameter(description = "Номер страницы")
            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,

            @Parameter(description = "Размер страницы")
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        log.info("Поисковой запрос: {}, страница: {}, размер страницы: {}", search, pageNumber, pageSize);
        return productService.searchProductsByKeywords(search, pageNumber, pageSize);
    }


}
