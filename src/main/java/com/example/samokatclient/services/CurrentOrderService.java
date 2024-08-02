package com.example.samokatclient.services;

import com.example.samokatclient.DTO.cart.CartDto;
import com.example.samokatclient.DTO.currentOrder.CurrentOrderClientDto;
import com.example.samokatclient.DTO.currentOrder.NewOrderDto;
import com.example.samokatclient.DTO.currentOrder.NewStatusDto;
import com.example.samokatclient.DTO.payment.PaymentInfoDto;
import com.example.samokatclient.entities.currentOrder.CurrentOrderClient;
import com.example.samokatclient.entities.session.Cart;
import com.example.samokatclient.entities.session.CartItem;
import com.example.samokatclient.entities.session.Session;
import com.example.samokatclient.entities.user.Order;
import com.example.samokatclient.enums.CurrentOrderStatus;
import com.example.samokatclient.enums.OrderStatus;
import com.example.samokatclient.exceptions.cart.CartIsEmptyException;
import com.example.samokatclient.exceptions.order.CurrentOrderNotFoundException;
import com.example.samokatclient.exceptions.payment.BadConnectionToPaymentException;
import com.example.samokatclient.exceptions.session.AddressNotFoundForSessionException;
import com.example.samokatclient.exceptions.session.InvalidTokenException;
import com.example.samokatclient.exceptions.session.PaymentNotFoundForSessionException;
import com.example.samokatclient.exceptions.session.UserIsNotAuthorizedException;
import com.example.samokatclient.mappers.CartMapper;
import com.example.samokatclient.mappers.CurrentOrderClientMapper;
import com.example.samokatclient.mappers.PaymentMapper;
import com.example.samokatclient.repositories.currentOrder.CurrentOrderClientRepository;
import com.example.samokatclient.repositories.session.SessionRepository;
import com.example.samokatclient.repositories.user.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CurrentOrderService {
    @Value("${webhook-uri}")
    private String webHookUri;
    private final SessionRepository sessionRepository;
    private final CurrentOrderClientRepository currentOrderClientRepository;
    private final OrderRepository orderRepository;
    private final HttpClient paymentHttpClient;
    private final CurrentOrderClientMapper currentOrderClientMapper;
    private final CartMapper cartMapper;
    private final PaymentMapper paymentMapper;
    private final KafkaTemplate<String, NewStatusDto> statusKafkaTemplate;
    private final KafkaTemplate<String, NewOrderDto> orderKafkaTemplate;

    public String createOrder(String sessionToken) {
        String orderToken = createOrderToken();
        Session session = getSession(sessionToken);
        checkOrderValid(session);
        PaymentInfoDto paymentInfoDto = createPaymentInfoDto(session);
        String paymentCode = initPayment(paymentInfoDto);
        LocalDateTime orderDateTime = LocalDateTime.now();
        createCurrentOrder(orderToken, session, orderDateTime, paymentCode);
        orderKafkaTemplate.send(
                "newOrder",
                createNewOrderDto(session, orderToken, paymentCode, orderDateTime));
        clearCart(session);
        return paymentCode;
    }

    public void cancelOrder(String sessionToken, String orderId) {
        Session session = getSession(sessionToken);
        CurrentOrderClient currentOrderClient = currentOrderClientRepository.findById(orderId).orElseThrow(
                () -> new CurrentOrderNotFoundException("Текущий заказ не найден")
        );
        if (!Objects.equals(currentOrderClient.getUserId(), session.getUser().getId())) {
            throw new CurrentOrderNotFoundException("Текущий заказ не найден");
        }
        NewStatusDto newStatusDto = NewStatusDto.builder()
                .orderId(currentOrderClient.getId())
                .status(CurrentOrderStatus.CANCELED)
                .build();
        statusKafkaTemplate.send("newStatus", newStatusDto);
    }

    public List<CurrentOrderClientDto> getCurrentOrders(String sessionToken) {
        Session session = getSession(sessionToken);
        List<String> ordersId = orderRepository
                .findAllByUserIdAndStatus(session.getUser().getId(), OrderStatus.PROCESSING)
                .stream()
                .map(Order::getId)
                .toList();
        return currentOrderClientRepository
                .findAllCurrentOrderClientsByUserId(ordersId)
                .stream()
                .map(currentOrderClientMapper::toDto)
                .toList();
    }

    private String createOrderToken() {
        String orderToken;
        Optional<Order> optionalOrder;
        do {
            orderToken = UUID.randomUUID().toString();
            optionalOrder = orderRepository.findById(orderToken);

        } while (optionalOrder.isPresent());
        return orderToken;
    }

    private String initPayment(PaymentInfoDto paymentInfoDto) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(paymentInfoDto);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8083/api/init"))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> response = paymentHttpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200){
                throw new BadConnectionToPaymentException("Ошибка на стороне платёжного провайдера");
            }
            return response.body();
        } catch (Exception ex) {
            throw new BadConnectionToPaymentException("Ошибка на стороне платёжного провайдера");
        }
    }

    private Session getSession(String sessionToken) {
        return sessionRepository.findById(sessionToken).orElseThrow(
                () -> new InvalidTokenException("Неверный ключ сессии")
        );
    }

    private void checkOrderValid(Session session) {
        if (session.getUser() == null) {
            throw new UserIsNotAuthorizedException("Неудачная попытка заказа: пользователь не авторизован");
        }
        if (session.getPayment() == null) {
            throw new PaymentNotFoundForSessionException("Неудачная попытка заказа: способ оплаты не указан");
        }
        if (session.getAddress() == null) {
            throw new AddressNotFoundForSessionException("Неудачная попытка заказа: адрес доставки не указан");
        }
        if (session.getCart().getProducts().isEmpty()) {
            throw new CartIsEmptyException("Неудачная попытка заказа: корзина пуста");
        }
    }

    private PaymentInfoDto createPaymentInfoDto(Session session) {
        return PaymentInfoDto.builder()
                .paymentDto(paymentMapper.toDto(session.getPayment()))
                .totalPrice(cartMapper.cartToDto(session.getCart()).getTotalPrice())
                .uri(webHookUri)
                .build();
    }

    private NewOrderDto createNewOrderDto(Session session,
                                          String orderToken,
                                          String paymentCode,
                                          LocalDateTime orderDateTime) {
        CartDto cartDto = cartMapper.cartToDto(session.getCart());
        return NewOrderDto.builder()
                .id(orderToken)
                .orderCartItemList(cartMapper.toListOrderCartItem(cartDto))
                .totalPrice(cartDto.getTotalPrice())
                .userId(session.getUser().getId())
                .addressId(session.getAddress().getId())
                .paymentId(session.getPayment().getId())
                .orderDateTime(orderDateTime)
                .paymentCode(paymentCode)
                .status(CurrentOrderStatus.CREATED)
                .build();
    }

    private void createCurrentOrder(String orderToken,
                                    Session session,
                                    LocalDateTime orderDateTime,
                                    String paymentCode) {
        CurrentOrderClient currentOrderClient = CurrentOrderClient.builder()
                .id(orderToken)
                .cart(session.getCart())
                .userId(session.getUser().getId())
                .address(session.getAddress())
                .payment(session.getPayment())
                .orderDateTime(orderDateTime)
                .paymentCode(paymentCode)
                .status(CurrentOrderStatus.CREATED)
                .build();
        currentOrderClientRepository.save(currentOrderClient);
    }

    private void clearCart(Session session) {
        session.setCart(Cart.builder()
                .products(new HashMap<Long, CartItem>())
                .build());
        sessionRepository.save(session);
    }
}
