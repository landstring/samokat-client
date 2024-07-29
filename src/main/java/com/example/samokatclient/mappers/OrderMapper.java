package com.example.samokatclient.mappers;

import com.example.samokatclient.DTO.order.OrderDto;
import com.example.samokatclient.entities.user.Address;
import com.example.samokatclient.entities.user.Order;
import com.example.samokatclient.entities.user.Payment;
import com.example.samokatclient.entities.user.SamokatUser;
import com.example.samokatclient.exceptions.order.AddressNotFoundException;
import com.example.samokatclient.exceptions.order.PaymentNotFoundException;
import com.example.samokatclient.exceptions.user.UserNotFoundException;
import com.example.samokatclient.repositories.user.AddressRepository;
import com.example.samokatclient.repositories.user.PaymentRepository;
import com.example.samokatclient.repositories.user.UserRepository;
import com.example.samokatclient.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderMapper {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PaymentRepository paymentRepository;
    private final CartMapper cartMapper;
    private final AddressMapper addressMapper;
    private final PaymentMapper paymentMapper;

    public OrderDto toDto(Order order) {

        SamokatUser samokatUser = userRepository.findById(order.getUserId()).orElseThrow(
                UserNotFoundException::new
        );

        Address address = addressRepository.findById(order.getAddress_id()).orElseThrow(
                AddressNotFoundException::new
        );

        Payment payment = paymentRepository.findById(order.getPayment_id()).orElseThrow(
                PaymentNotFoundException::new
        );

        return OrderDto.builder()
                .id(order.getId())
                .cartDto(cartMapper.listOrderCartItemToDto(order.getOrderCartItemList()))
                .totalPrice(order.getTotalPrice())
                .samokatUser(samokatUser)
                .addressDto(addressMapper.toDto(address))
                .paymentDto(paymentMapper.toDto(payment))
                .orderDateTime(order.getOrderDateTime())
                .status(order.getStatus())
                .build();
    }
}