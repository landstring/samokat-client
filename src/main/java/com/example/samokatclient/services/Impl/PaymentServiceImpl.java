package com.example.samokatclient.services.Impl;

import com.example.samokatclient.DTO.order.PaymentDto;
import com.example.samokatclient.entities.session.Session;
import com.example.samokatclient.entities.user.Payment;
import com.example.samokatclient.entities.user.User;
import com.example.samokatclient.exceptions.order.PaymentNotFoundException;
import com.example.samokatclient.exceptions.session.InvalidTokenException;
import com.example.samokatclient.exceptions.session.UserIsNotAuthorizedException;
import com.example.samokatclient.mappers.PaymentMapper;
import com.example.samokatclient.repositories.session.SessionRepository;
import com.example.samokatclient.repositories.user.PaymentRepository;
import com.example.samokatclient.services.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final SessionRepository sessionRepository;
    private final PaymentMapper paymentMapper;

    public Payment getPaymentById(String paymentId) {
        return paymentRepository.findById(paymentId).orElseThrow(
                () -> new PaymentNotFoundException("Такого способа оплаты не существует")
        );
    }

    @Override
    public List<PaymentDto> getUserPayments(String sessionToken) {
        User user = getSessionUser(sessionToken);
        return paymentRepository
                .findByUserId(user.getId())
                .stream()
                .map(paymentMapper::toDto)
                .toList();
    }

    @Override
    public PaymentDto getUserPayment(String sessionToken, String paymentId) {
        User user = getSessionUser(sessionToken);
        Payment payment = paymentRepository.findByIdAndUserId(paymentId, user.getId()).orElseThrow(
                () -> new PaymentNotFoundException("Такого способа оплаты не существует, или он создан другим пользователем")
        );
        return paymentMapper.toDto(payment);
    }

    @Override
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

    private User getSessionUser(String sessionToken) {
        Session session = sessionRepository.findById(sessionToken).orElseThrow(
                () -> new InvalidTokenException("Неверный ключ сессии")
        );
        if (session.getUser() == null) {
            throw new UserIsNotAuthorizedException("Пользователь не авторизован");
        }
        return session.getUser();
    }
}
