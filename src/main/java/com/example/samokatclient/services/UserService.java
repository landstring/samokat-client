package com.example.samokatclient.services;

import com.example.samokatclient.DTO.details.AddressDto;
import com.example.samokatclient.DTO.details.OrderDto;
import com.example.samokatclient.DTO.details.PaymentDto;
import com.example.samokatclient.entities.user.Address;
import com.example.samokatclient.entities.user.Payment;
import com.example.samokatclient.mappers.AddressMapper;
import com.example.samokatclient.mappers.OrderMapper;
import com.example.samokatclient.mappers.PaymentMapper;
import com.example.samokatclient.repositories.user.AddressRepository;
import com.example.samokatclient.repositories.user.OrderRepository;
import com.example.samokatclient.repositories.user.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final PaymentRepository paymentRepository;
    private final OrderMapper orderMapper;

    public List<OrderDto> getUserOrders(String user_id){
        return orderRepository.findByUserId(user_id).stream().map(orderMapper::toDto).toList();
    }

    public List<AddressDto> getUserAddress(String user_id){
        return addressRepository.findByUserId(user_id).stream().map(AddressMapper::toDto).toList();
    }

    public List<PaymentDto> getUserPayment(String user_id){
        return paymentRepository.findByUserId(user_id).stream().map(PaymentMapper::toDto).toList();
    }

    public void createNewAddress(String user_id, AddressDto addressDto){
        Address address = AddressMapper.fromDto(addressDto);
        address.setUser_id(user_id);
        addressRepository.save(address);
    }

    public void createNewPayment(String user_id, PaymentDto paymentDto){
        Payment payment = PaymentMapper.fromDto(paymentDto);
        payment.setUser_id(user_id);
        paymentRepository.save(payment);
    }
}
