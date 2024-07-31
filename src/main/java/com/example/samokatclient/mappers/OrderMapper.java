package com.example.samokatclient.mappers;

import com.example.samokatclient.DTO.order.OrderDto;
import com.example.samokatclient.entities.user.Address;
import com.example.samokatclient.entities.user.Order;
import com.example.samokatclient.entities.user.Payment;
import com.example.samokatclient.entities.user.User;
import com.example.samokatclient.exceptions.order.AddressNotFoundException;
import com.example.samokatclient.exceptions.order.PaymentNotFoundException;
import com.example.samokatclient.repositories.user.AddressRepository;
import com.example.samokatclient.repositories.user.PaymentRepository;
import com.example.samokatclient.repositories.user.UserRepository;
import com.example.samokatclient.services.AddressService;
import com.example.samokatclient.services.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderMapper {
    private final PaymentService paymentService;
    private final AddressService addressService;
    private final CartMapper cartMapper;
    private final AddressMapper addressMapper;
    private final PaymentMapper paymentMapper;

    public OrderDto toDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .cartDto(cartMapper.listOrderCartItemToDto(order.getOrderCartItemList()))
                .addressDto(addressMapper.toDto(addressService.getAddressById(order.getAddressId())))
                .paymentDto(paymentMapper.toDto(paymentService.getPaymentById(order.getPaymentId())))
                .orderDateTime(order.getOrderDateTime())
                .status(order.getStatus())
                .build();
    }
}
