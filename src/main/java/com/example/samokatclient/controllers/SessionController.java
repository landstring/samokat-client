package com.example.samokatclient.controllers;

import com.example.samokatclient.DTO.order.AddressDto;
import com.example.samokatclient.DTO.order.PaymentDto;
import com.example.samokatclient.DTO.session.UserDto;
import com.example.samokatclient.services.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Tag(name = "Методы для работы с сессией")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/session")
@Slf4j
public class SessionController {
    private final SessionService sessionService;

    @Operation(
            summary = "Создать сессию",
            description = "Данный метод создаст сессию и выдаст токен"
    )
    @GetMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public String createSession() {
        log.info("Создание сессии");
        return sessionService.createSession();
    }

    @Operation(
            summary = "Авторизация пользователя",
            description =
                    "Данный метод привязывает пользователя к текущей сессии. " +
                            "Если пользователь впервые авторизуется на сервисе, регистрирует его"
    )
    @PostMapping("/authorization")
    @SecurityRequirement(name = "api_key")
    @ResponseStatus(HttpStatus.OK)
    public void authorization(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken,

            @RequestBody UserDto userDto) {
        log.info("Авторизация внутри сессии: {}, \nДанные пользователя: {}", sessionToken, userDto);
        sessionService.authorizeUser(sessionToken, userDto);
    }

    @Operation(
            summary = "Получить адрес",
            description =
                    "Данный метод выдаёт данные об адресе потенциального заказа для текущей сессии"
    )
    @GetMapping("/address")
    @SecurityRequirement(name = "api_key")
    @ResponseStatus(HttpStatus.OK)
    public AddressDto getAddress(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken) {
        log.info("Получить адрес возможного заказа для сессии: {}", sessionToken);
        return sessionService.getAddress(sessionToken);
    }

    @Operation(
            summary = "Получить способ оплаты",
            description =
                    "Данный метод выдаёт данные о способе оплаты потенциального заказа для текущей сессии"
    )
    @GetMapping("/payment")
    @SecurityRequirement(name = "api_key")
    @ResponseStatus(HttpStatus.OK)
    public PaymentDto getPayment(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken) {
        log.info("Получить способ оплаты возможного заказа для сессии: {}", sessionToken);
        return sessionService.getPayment(sessionToken);
    }

    @Operation(
            summary = "Задать адрес",
            description =
                    "Данный метод задаёт возможный адрес для заказа по ID. Адрес перед этим уже должен быть " +
                            "в системе"
    )
    @GetMapping("/address/{addressId}")
    @SecurityRequirement(name = "api_key")
    @ResponseStatus(HttpStatus.OK)
    public void setAddress(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken,

            @Parameter(description = "ID адреса")
            @PathVariable("addressId") String addressId) {
        log.info("Запрос на присвоение текущему возможному заказу id адреса для сессии: {}, id адреса: {}",
                sessionToken, addressId);
        sessionService.setAddress(sessionToken, addressId);
    }

    @Operation(
            summary = "Задать способ оплаты",
            description =
                    "Данный метод задаёт возможный способ оплаты для заказа по ID. Способ оплаты перед этим уже " +
                            "должен быть в системе"
    )
    @GetMapping("/payment/{paymentId}")
    @SecurityRequirement(name = "api_key")
    @ResponseStatus(HttpStatus.OK)
    public void setPayment(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken,

            @Parameter(description = "ID способа оплаты")
            @PathVariable("paymentId") String paymentId) {
        log.info("Запрос на присвоение текущему возможному заказу id способа оплаты для сессии: {}, id способа оплаты: {}",
                sessionToken, paymentId);
        sessionService.setPayment(sessionToken, paymentId);
    }
}