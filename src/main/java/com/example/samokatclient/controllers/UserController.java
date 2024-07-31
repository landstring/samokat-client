package com.example.samokatclient.controllers;

import com.example.samokatclient.DTO.order.AddressDto;
import com.example.samokatclient.DTO.order.PaymentDto;
import com.example.samokatclient.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Методы для работы с пользовательскими данными")
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Получить данные пользователя",
            description = "Данный метод выдаст данные авторизованного пользователя"
    )
    @GetMapping("/")
    @SecurityRequirement(name = "api_key")
    public ResponseEntity<?> getUser(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken) {
        return new ResponseEntity<>(userService.getSessionUser(sessionToken), HttpStatus.OK);
    }

    @Operation(
            summary = "Получить историю заказов пользователя",
            description = "Данный метод выдаст историю заказов авторизованного пользователя"
    )
    @GetMapping("/orders")
    @SecurityRequirement(name = "api_key")
    public ResponseEntity<?> getUserOrders(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken) {
        return new ResponseEntity<>(userService.getUserOrders(sessionToken), HttpStatus.OK);
    }

    @Operation(
            summary = "Получить заказ пользователя",
            description = "Данный метод выдаст данные о заказе по id"
    )
    @GetMapping("/orders/{orderId}")
    @SecurityRequirement(name = "api_key")
    public ResponseEntity<?> getUserOrderById(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken,

            @Parameter(description = "id заказа")
            @PathVariable(value = "orderId") String orderId) {
        return new ResponseEntity<>(userService.getOrderById(sessionToken, orderId), HttpStatus.OK);
    }

    @Operation(
            summary = "Получить адреса пользователя",
            description = "Данный метод выдаст адреса, который сохранял пользователь"
    )
    @GetMapping("/addresses")
    @SecurityRequirement(name = "api_key")
    public ResponseEntity<?> getUserAddresses(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken) {
        return new ResponseEntity<>(userService.getUserAddresses(sessionToken), HttpStatus.OK);
    }

    @Operation(
            summary = "Получить адрес пользователя",
            description = "Данный метод выдаст адрес пользователя по ID для данного пользователя"
    )
    @GetMapping("/addresses/{address_id}")
    @SecurityRequirement(name = "api_key")
    public ResponseEntity<?> getUserAddress(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken,

            @Parameter(description = "ID Адреса")
            @PathVariable("address_id") String addressId
    ) {
        return new ResponseEntity<>(userService.getUserAddress(sessionToken, addressId), HttpStatus.OK);
    }

    @Operation(
            summary = "Получить способы оплаты пользователя",
            description = "Данный метод выдаст способы оплаты, который сохранял пользователь"
    )
    @GetMapping("/payments")
    @SecurityRequirement(name = "api_key")
    public ResponseEntity<?> getUserPayments(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken) {
        return new ResponseEntity<>(userService.getUserPayments(sessionToken), HttpStatus.OK);
    }

    @Operation(
            summary = "Получить адрес пользователя",
            description = "Данный метод выдаст адрес пользователя по ID для данного пользователя"
    )
    @GetMapping("/payments/{payment_id}")
    @SecurityRequirement(name = "api_key")
    public ResponseEntity<?> getUserPayment(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken,

            @Parameter(description =  "ID способа оплаты")
            @PathVariable("payment_id") String paymentId
    ) {
        return new ResponseEntity<>(userService.getUserPayment(sessionToken, paymentId), HttpStatus.OK);
    }

    @Operation(
            summary = "Сохранить адрес",
            description = "Данный метод позволяет пользователю сохранить адрес для будущих заказов"
    )
    @PostMapping("/new-address")
    @SecurityRequirement(name = "api_key")
    public ResponseEntity<?> createNewAddress(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken,

            @RequestBody AddressDto addressDto) {
        userService.createNewAddress(sessionToken, addressDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Сохранить способ оплаты",
            description = "Данный метод позволяет пользователю сохранить способ оплаты для будущих заказов"
    )
    @PostMapping("/new-payment")
    @SecurityRequirement(name = "api_key")
    public ResponseEntity<?> createNewAddress(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken,

            @RequestBody PaymentDto paymentDto) {
        userService.createNewPayment(sessionToken, paymentDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
