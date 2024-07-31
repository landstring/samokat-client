package com.example.samokatclient.controllers;

import com.example.samokatclient.services.CurrentOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Методы для работы с текущим заказом")
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/current-orders")
public class CurrentOrderController {
    private final CurrentOrderService currentOrderService;

    @Operation(
            summary = "Создать новый заказ",
            description = "Данный метод создаст отправит заказ на обработку"
    )
    @GetMapping("/send")
    @SecurityRequirement(name = "api_key")
    public ResponseEntity<?> createOrder(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken) {
        return new ResponseEntity<>(currentOrderService.createOrder(sessionToken), HttpStatus.OK);
    }

    @Operation(
            summary = "Отменить текущий заказ",
            description = "Данный отменит текущий заказ пользователя"
    )
    @GetMapping("/cancel/{orderId}")
    @SecurityRequirement(name = "api_key")
    public ResponseEntity<?> cancelOrder(
            @Parameter(description = "Номер заказа")
            @PathVariable("orderId") String orderId,

            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken) {
        currentOrderService.cancelOrder(sessionToken, orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Получить текущие заказы пользователя",
            description = "Данный метод выдаст все текущие заказы пользователя"
    )
    @GetMapping("/")
    @SecurityRequirement(name = "api_key")
    public ResponseEntity<?> getCurrentOrders(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken) {
        return new ResponseEntity<>(currentOrderService.getCurrentOrders(sessionToken), HttpStatus.OK);
    }
}
