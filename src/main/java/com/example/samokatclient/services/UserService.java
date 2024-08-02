package com.example.samokatclient.services;

import com.example.samokatclient.DTO.order.AddressDto;
import com.example.samokatclient.DTO.order.OrderDto;
import com.example.samokatclient.DTO.order.PaymentDto;
import com.example.samokatclient.entities.session.Session;
import com.example.samokatclient.entities.user.Address;
import com.example.samokatclient.entities.user.Order;
import com.example.samokatclient.entities.user.Payment;
import com.example.samokatclient.entities.user.User;
import com.example.samokatclient.exceptions.order.AddressNotFoundException;
import com.example.samokatclient.exceptions.order.OrderNotFoundException;
import com.example.samokatclient.exceptions.order.PaymentNotFoundException;
import com.example.samokatclient.exceptions.session.InvalidTokenException;
import com.example.samokatclient.exceptions.session.UserIsNotAuthorizedException;
import com.example.samokatclient.mappers.AddressMapper;
import com.example.samokatclient.mappers.OrderMapper;
import com.example.samokatclient.mappers.PaymentMapper;
import com.example.samokatclient.repositories.session.SessionRepository;
import com.example.samokatclient.repositories.user.AddressRepository;
import com.example.samokatclient.repositories.user.OrderRepository;
import com.example.samokatclient.repositories.user.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final OrderRepository orderRepository;
    private final SessionRepository sessionRepository;
    private final AddressRepository addressRepository;
    private final PaymentRepository paymentRepository;
    private final OrderMapper orderMapper;
    private final PaymentMapper paymentMapper;
    private final AddressMapper addressMapper;

    public User getSessionUser(String sessionToken) {
        Session session = sessionRepository.findById(sessionToken).orElseThrow(
                () -> new InvalidTokenException("Неверный ключ сессии")
        );
        if (session.getUser() == null) {
            throw new UserIsNotAuthorizedException("Пользователь не авторизован");
        }
        return session.getUser();
    }

    public List<OrderDto> getUserOrders(String sessionToken) {
        User user = getSessionUser(sessionToken);
        return orderRepository
                .findByUserId(user.getId())
                .stream()
                .map(orderMapper::toDto)
                .toList();
    }

    public OrderDto getOrderById(String sessionToken, String orderId) {
        User user = getSessionUser(sessionToken);
        Order order = orderRepository.findByIdAndUserId(orderId, user.getId()).orElseThrow(
                () -> new OrderNotFoundException("Такого заказа не существует, или он создан другим пользователем")
        );
        return orderMapper.toDto(order);
    }

    public List<AddressDto> getUserAddresses(String sessionToken) {
        User user = getSessionUser(sessionToken);
        return addressRepository
                .findByUserId(user.getId())
                .stream()
                .map(addressMapper::toDto)
                .toList();
    }

    public AddressDto getUserAddress(String sessionToken, String addressId) {
        User user = getSessionUser(sessionToken);
        Address address = addressRepository.findByIdAndUserId(addressId, user.getId()).orElseThrow(
                () -> new AddressNotFoundException("Такого адреса не существует, или он создан другим пользователем")
        );
        return addressMapper.toDto(address);
    }

    public List<PaymentDto> getUserPayments(String sessionToken) {
        User user = getSessionUser(sessionToken);
        return paymentRepository
                .findByUserId(user.getId())
                .stream()
                .map(paymentMapper::toDto)
                .toList();
    }

    public PaymentDto getUserPayment(String sessionToken, String paymentId) {
        User user = getSessionUser(sessionToken);
        Payment payment = paymentRepository.findByIdAndUserId(paymentId, user.getId()).orElseThrow(
                () -> new PaymentNotFoundException("Такого способа оплаты не существует, или он создан другим пользователем")
        );
        return paymentMapper.toDto(payment);
    }

    public void createNewAddress(String sessionToken, AddressDto addressDto) {
        String addressId;
        do {
            addressId = UUID.randomUUID().toString();
        } while (addressRepository.existsById(addressId));
        User user = getSessionUser(sessionToken);
        Address address = addressMapper.fromDto(addressDto);
        address.setId(addressId);
        address.setUserId(user.getId());
        addressRepository.save(address);
    }

    public void createNewPayment(String sessionToken, PaymentDto paymentDto) {
        String paymentId;
        do {
            paymentId = UUID.randomUUID().toString();
        } while (paymentRepository.existsById(paymentId));
        User user = getSessionUser(sessionToken);
        Payment payment = paymentMapper.fromDto(paymentDto);
        payment.setId(paymentId);
        payment.setUserId(user.getId());
        paymentRepository.save(payment);
    }
}