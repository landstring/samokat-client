package com.example.samokatclient.services;

import com.example.samokatclient.DTO.order.AddressDto;
import com.example.samokatclient.DTO.order.PaymentDto;
import com.example.samokatclient.DTO.session.UserDto;
import com.example.samokatclient.entities.session.Cart;
import com.example.samokatclient.entities.session.Session;
import com.example.samokatclient.entities.user.Address;
import com.example.samokatclient.entities.user.Payment;
import com.example.samokatclient.entities.user.User;
import com.example.samokatclient.exceptions.order.AddressNotFoundException;
import com.example.samokatclient.exceptions.order.PaymentNotFoundException;
import com.example.samokatclient.exceptions.session.*;
import com.example.samokatclient.mappers.AddressMapper;
import com.example.samokatclient.mappers.PaymentMapper;
import com.example.samokatclient.mappers.UserMapper;
import com.example.samokatclient.repositories.session.SessionRepository;
import com.example.samokatclient.repositories.user.AddressRepository;
import com.example.samokatclient.repositories.user.PaymentRepository;
import com.example.samokatclient.repositories.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PaymentRepository paymentRepository;
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;
    private final PaymentMapper paymentMapper;

    public String createSession() {
        String sessionToken;
        do {
            sessionToken = UUID.randomUUID().toString();

        } while (sessionRepository.existsById(sessionToken));
        Session session = Session.builder()
                .id(sessionToken)
                .cart(createCart())
                .build();
        sessionRepository.save(session);
        return sessionToken;
    }

    public void authorizeUser(String sessionToken, UserDto userDto) {
        Session session = getSession(sessionToken);
        if (!isValidPhoneNumber(userDto.getId())) {
            throw new InvalidPhoneNumberException("Номер телефона некорректный");
        }
        if (session.getUser() != null) {
            throw new UserIsAlreadyAuthorized("Пользователь уже авторизован");
        }
        Optional<User> optionalUser = userRepository.findById(userDto.getId());
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
        } else{
            User user = userMapper.fromDto(userDto);
        }
        User user = userRepository.findById(userDto.getId())
                .orElseGet(() -> userMapper.fromDto(userDto));
        session.setUser(user);
        sessionRepository.save(session);
    }

    public AddressDto getAddress(String sessionToken) {
        Session session = getSession(sessionToken);
        Address address = session.getAddress();
        if (address == null) {
            throw new AddressNotFoundForSessionException("Адрес не указан для данной сессии");
        }
        return addressMapper.toDto(address);
    }

    public PaymentDto getPayment(String sessionToken) {
        Session session = getSession(sessionToken);
        Payment payment = session.getPayment();
        if (payment == null) {
            throw new PaymentNotFoundForSessionException("Способ оплаты не указан для данной сессии");
        }
        return paymentMapper.toDto(payment);
    }

    public void setAddress(String sessionToken, String addressId) {
        Session session = getSession(sessionToken);
        Address address = addressRepository.findById(addressId).orElseThrow(
                () -> new AddressNotFoundException("Адрес не найден")
        );
        session.setAddress(address);
        sessionRepository.save(session);
    }

    public void setPayment(String sessionToken, String paymentId) {
        Session session = getSession(sessionToken);
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(
                () -> new PaymentNotFoundException("Способ оплаты не найден")
        );
        session.setPayment(payment);
        sessionRepository.save(session);
    }

    private Cart createCart() {
        return Cart.builder()
                .products(new HashMap<>())
                .build();
    }

    private Session getSession(String sessionToken) {
        return sessionRepository.findById(sessionToken).orElseThrow(
                () -> new InvalidTokenException("Неверный ключ сессии")
        );
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }
        String regex = "^\\+7\\s\\(\\d{3}\\)\\s\\d{3}-\\d{2}-\\d{2}$";
        return phoneNumber.matches(regex);
    }
}
