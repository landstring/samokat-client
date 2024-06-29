package com.example.samokatclient.controllers;

import com.example.samokatclient.DTO.cart.CartDto;
import com.example.samokatclient.services.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final SessionService sessionService;
    @GetMapping("/")
    private ResponseEntity<?> getCart(@RequestHeader("Authorization") String token){
        return new ResponseEntity<>(sessionService.getCart(token), HttpStatus.OK);
    }

    @GetMapping("/add/{product_id}")
    private ResponseEntity<?> addToCart(@RequestHeader("Authorization") String token,
                                        @PathVariable("product_id") Long product_id){
        sessionService.addToCart(token, product_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/delete/{product_id}")
    private ResponseEntity<?> deleteFromCart(@RequestHeader("Authorization") String token,
                                             @PathVariable("product_id") Long product_id){
        sessionService.deleteFromCart(token, product_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
