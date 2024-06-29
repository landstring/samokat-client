package com.example.samokatclient.mappers;

import com.example.samokatclient.DTO.details.AddressDto;
import com.example.samokatclient.DTO.details.OrderDto;
import com.example.samokatclient.DTO.details.PaymentDto;
import com.example.samokatclient.entities.user.Address;
import com.example.samokatclient.entities.user.Order;
import com.example.samokatclient.entities.user.Payment;
import com.example.samokatclient.exceptions.order.AddressNotFoundException;
import com.example.samokatclient.repositories.user.AddressRepository;
import com.example.samokatclient.repositories.user.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class OrderMapper {
    private final AddressRepository addressRepository;
    private final PaymentRepository paymentRepository;
    private final CartMapper cartMapper;

    public OrderDto toDto(Order order){
        OrderDto orderDto = new OrderDto();
        orderDto.cart = cartMapper.listOrderCartItemToDto(order.getCartItemList());
        Optional<Address> optionalAddress = addressRepository.findById(order.getAddress_id());
        if (optionalAddress.isPresent()){
            orderDto.address = AddressMapper.toDto(optionalAddress.get());
        }
        else{
            throw new AddressNotFoundException();
        }
        Optional<Payment> optionalPayment = paymentRepository.findById(order.getPayment_id());
        if (optionalPayment.isPresent()){
            orderDto.payment = PaymentMapper.toDto(optionalPayment.get());
        }
        else{
            throw new AddressNotFoundException();
        }
        orderDto.orderDateTime = order.getOrderDateTime();
        return orderDto;
    }
}
