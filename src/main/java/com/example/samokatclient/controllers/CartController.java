package com.example.samokatclient.controllers;

import com.example.samokatclient.DTO.cart.CartDto;
import com.example.samokatclient.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Tag(name = "Методы для работы с корзиной")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/cart")
@Slf4j
public class CartController {
    private final CartService cartService;

    @Operation(
            summary = "Получить состояние корзины",
            description = "Данный метод выводит продукты, которые на данный момент содержатся в корзине"
    )
    @GetMapping("/")
    @SecurityRequirement(name = "api_key")
    @ResponseStatus(HttpStatus.OK)
    private CartDto getCart(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken) {
        log.info("Запрос на получение состояние корзины для сессии: {}" , sessionToken);
        return cartService.getCart(sessionToken);
    }

    @Operation(
            summary = "Добавить продукт в корзину",
            description = "Данный метод добавляет продукт в корзину"
    )
    @GetMapping("/add/{productId}")
    @SecurityRequirement(name = "api_key")
    @ResponseStatus(HttpStatus.OK)
    private void addToCart(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken,

            @Parameter(description = "ID продукта")
            @PathVariable("productId") Long productId) {
        log.info("Запрос на добавление продукта в корзину id: {}, и ключом сессии: {}", productId, sessionToken);
        cartService.addToCart(sessionToken, productId);
    }

    @Operation(
            summary = "Удалить продукт из корзины",
            description = "Данный метод удаляет продукт из корзины"
    )
    @GetMapping("/delete/{productId}")
    @SecurityRequirement(name = "api_key")
    @ResponseStatus(HttpStatus.OK)
    private void deleteFromCart(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken,

            @Parameter(description = "ID продукта")
            @PathVariable("productId") Long productId) {
        log.info("Запрос на удаление продукта из корзины с id: {}, и ключом сессии: {}", productId, sessionToken);
        cartService.deleteFromCart(sessionToken, productId);
    }

    @Operation(
            summary = "Очистить корзину",
            description = "Данный метод удаляет все продукты из корзины"
    )
    @GetMapping("/clear")
    @SecurityRequirement(name = "api_key")
    @ResponseStatus(HttpStatus.OK)
    private void deleteFromCart(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken) {
        log.info("Запрос на удаление продукта из корзины для сессии: {}", sessionToken);
        cartService.clearCart(sessionToken);
    }
}
