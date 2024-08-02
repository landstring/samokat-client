package com.example.samokatclient.controllers;

import com.example.samokatclient.DTO.order.AddressDto;
import com.example.samokatclient.DTO.order.OrderDto;
import com.example.samokatclient.DTO.order.PaymentDto;
import com.example.samokatclient.DTO.session.UserDto;
import com.example.samokatclient.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUser(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken) {
        return userService.getSessionUser(sessionToken);
    }

    @Operation(
            summary = "Получить историю заказов пользователя",
            description = "Данный метод выдаст историю заказов авторизованного пользователя"
    )
    @GetMapping("/orders")
    @SecurityRequirement(name = "api_key")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> getUserOrders(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken) {
        return userService.getUserOrders(sessionToken);
    }

    @Operation(
            summary = "Получить заказ пользователя",
            description = "Данный метод выдаст данные о заказе по id"
    )
    @GetMapping("/orders/{orderId}")
    @SecurityRequirement(name = "api_key")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto getUserOrderById(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken,

            @Parameter(description = "id заказа")
            @PathVariable(value = "orderId") String orderId) {
        return userService.getOrderById(sessionToken, orderId);
    }

    @Operation(
            summary = "Получить адреса пользователя",
            description = "Данный метод выдаст адреса, который сохранял пользователь"
    )
    @GetMapping("/addresses")
    @SecurityRequirement(name = "api_key")
    @ResponseStatus(HttpStatus.OK)
    public List<AddressDto> getUserAddresses(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken) {
        return userService.getUserAddresses(sessionToken);
    }

    @Operation(
            summary = "Получить адрес пользователя",
            description = "Данный метод выдаст адрес пользователя по ID для данного пользователя"
    )
    @GetMapping("/addresses/{address_id}")
    @SecurityRequirement(name = "api_key")
    @ResponseStatus(HttpStatus.OK)
    public AddressDto getUserAddress(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken,

            @Parameter(description = "ID Адреса")
            @PathVariable("address_id") String addressId
    ) {
        return userService.getUserAddress(sessionToken, addressId);
    }

    @Operation(
            summary = "Получить способы оплаты пользователя",
            description = "Данный метод выдаст способы оплаты, который сохранял пользователь"
    )
    @GetMapping("/payments")
    @SecurityRequirement(name = "api_key")
    @ResponseStatus(HttpStatus.OK)
    public List<PaymentDto> getUserPayments(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken) {
        return userService.getUserPayments(sessionToken);
    }

    @Operation(
            summary = "Получить адрес пользователя",
            description = "Данный метод выдаст адрес пользователя по ID для данного пользователя"
    )
    @GetMapping("/payments/{payment_id}")
    @SecurityRequirement(name = "api_key")
    @ResponseStatus(HttpStatus.OK)
    public PaymentDto getUserPayment(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken,

            @Parameter(description = "ID способа оплаты")
            @PathVariable("payment_id") String paymentId
    ) {
        return userService.getUserPayment(sessionToken, paymentId);
    }

    @Operation(
            summary = "Сохранить адрес",
            description = "Данный метод позволяет пользователю сохранить адрес для будущих заказов"
    )
    @PostMapping("/new-address")
    @SecurityRequirement(name = "api_key")
    @ResponseStatus(HttpStatus.OK)
    public void createNewAddress(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken,

            @RequestBody AddressDto addressDto) {
        userService.createNewAddress(sessionToken, addressDto);
    }

    @Operation(
            summary = "Сохранить способ оплаты",
            description = "Данный метод позволяет пользователю сохранить способ оплаты для будущих заказов"
    )
    @PostMapping("/new-payment")
    @SecurityRequirement(name = "api_key")
    @ResponseStatus(HttpStatus.OK)
    public void createNewAddress(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken,

            @RequestBody PaymentDto paymentDto) {
        userService.createNewPayment(sessionToken, paymentDto);
    }
}
