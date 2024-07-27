package com.example.samokatclient.mappers;

import com.example.samokatclient.DTO.order.OrderDto;
import com.example.samokatclient.entities.user.Address;
import com.example.samokatclient.entities.user.Order;
import com.example.samokatclient.entities.user.Payment;
import com.example.samokatclient.exceptions.order.AddressNotFoundException;
import com.example.samokatclient.exceptions.order.PaymentNotFoundException;
import com.example.samokatclient.repositories.user.AddressRepository;
import com.example.samokatclient.repositories.user.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderMapper {

    private final AddressRepository addressRepository;
    private final PaymentRepository paymentRepository;
    private final CartMapper cartMapper;
    private final AddressMapper addressMapper;
    private final PaymentMapper paymentMapper;

    public OrderDto toDto(Order order) {

        Address optionalAddress = addressRepository.findById(order.getAddress_id()).orElseThrow(
                () -> new AddressNotFoundException("There is no such address")
        );

        Payment optionalPayment = paymentRepository.findById(order.getPayment_id()).orElseThrow(
                () -> new PaymentNotFoundException("There is no such payment")
        );

        return OrderDto.builder()
                .id(order.getId())
                .cartDto(cartMapper.listOrderCartItemToDto(order.getOrderCartItemList()))
                .totalPrice(order.getTotalPrice())
                .addressDto(addressMapper.toDto(optionalAddress))
                .paymentDto(paymentMapper.toDto(optionalPayment))
                .orderDateTime(order.getOrderDateTime())
                .status(order.getStatus())
                .build();
    }
}