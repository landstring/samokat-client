package com.example.samokatclient.controllers;

import com.example.samokatclient.DTO.session.UserDto;
import com.example.samokatclient.services.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Tag(name = "Методы для работы с сессией")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/session")
public class SessionController {
    private final SessionService sessionService;

    @Operation(
            summary = "Создать сессию",
            description = "Данный метод создаст сессию и выдаст токен"
    )
    @GetMapping("/create")
    public ResponseEntity<?> createSession() {
        return new ResponseEntity<>(sessionService.createSession(), HttpStatus.OK);
    }

    @Operation(
            summary = "Авторизация пользователя",
            description =
                    "Данный метод привязывает пользователя к текущей сессии. " +
                            "Если пользователь впервые авторизуется на сервисе, регистрирует его"
    )
    @PostMapping("/authorization")
    @SecurityRequirement(name = "api_key")
    public ResponseEntity<?> authorization(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken,

            @RequestBody UserDto userDto) {
        sessionService.authorizeUser(sessionToken, userDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Получить адрес",
            description =
                    "Данный метод выдаёт данные об адресе потенциального заказа для текущей сессии"
    )
    @GetMapping("/address")
    @SecurityRequirement(name = "api_key")
    public ResponseEntity<?> getAddress(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken) {
        return new ResponseEntity<>(sessionService.getAddress(sessionToken), HttpStatus.OK);
    }

    @Operation(
            summary = "Получить способ оплаты",
            description =
                    "Данный метод выдаёт данные о способе оплаты потенциального заказа для текущей сессии"
    )
    @GetMapping("/payment")
    @SecurityRequirement(name = "api_key")
    public ResponseEntity<?> getPayment(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken) {
        return new ResponseEntity<>(sessionService.getPayment(sessionToken), HttpStatus.OK);
    }

    @Operation(
            summary = "Задать адрес",
            description =
                    "Данный метод задаёт возможный адрес для заказа по ID. Адрес перед этим уже должен быть " +
                            "в системе"
    )
    @GetMapping("/address/{addressId}")
    @SecurityRequirement(name = "api_key")
    public ResponseEntity<?> setAddress(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken,

            @Parameter(description = "ID адреса")
            @PathVariable("addressId") String addressId) {
        sessionService.setAddress(sessionToken, addressId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Задать способ оплаты",
            description =
                    "Данный метод задаёт возможный способ оплаты для заказа по ID. Способ оплаты перед этим уже " +
                            "должен быть в системе"
    )
    @GetMapping("/payment/{paymentId}")
    @SecurityRequirement(name = "api_key")
    public ResponseEntity<?> setPayment(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String sessionToken,

            @Parameter(description = "ID способа оплаты")
            @PathVariable("paymentId") String paymentId) {
        sessionService.setPayment(sessionToken, paymentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}