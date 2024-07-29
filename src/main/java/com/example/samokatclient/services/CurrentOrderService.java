package com.example.samokatclient.services;

import com.example.samokatclient.DTO.order.NewOrderDto;
import com.example.samokatclient.DTO.order.NewStatusDto;
import com.example.samokatclient.DTO.order.OrderDto;
import com.example.samokatclient.DTO.payment.PaymentInfoDto;
import com.example.samokatclient.DTO.session.UserDto;
import com.example.samokatclient.entities.user.Order;
import com.example.samokatclient.exceptions.cart.CartIsEmptyException;
import com.example.samokatclient.exceptions.order.CurrentOrderNotFoundException;
import com.example.samokatclient.exceptions.payment.BadConnectionToPaymentException;
import com.example.samokatclient.mappers.CartMapper;
import com.example.samokatclient.redis.CurrentOrder;
import com.example.samokatclient.repositories.user.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CurrentOrderService {
    private final static String HASH_KEY = "CurrentOrderClient";
    private final RedisTemplate<String, Object> redisTemplate;
    private final KafkaTemplate<String, NewStatusDto> statusKafkaTemplate;
    private final CartMapper cartMapper;
    private final KafkaTemplate<String, NewOrderDto> orderKafkaTemplate;
    private final SessionService sessionService;
    private final OrderRepository orderRepository;

    public String createOrder(String sessionToken) {
        String orderToken = generateOrderToken();
        if (sessionService.getCart(sessionToken).getCartItemList().isEmpty()) {
            throw new CartIsEmptyException();
        }
        PaymentInfoDto paymentInfoDto = PaymentInfoDto.builder()
                .card_number(sessionService.getPayment(sessionToken).getCard_number())
                .expiration_date(sessionService.getPayment(sessionToken).getExpiration_date())
                .cvc(sessionService.getPayment(sessionToken).getCvc())
                .totalPrice(sessionService.getCart(sessionToken).getTotalPrice())
                .url("http://localhost:8082/api")
                .build();
        String payment_code = initPayment(paymentInfoDto);
        NewOrderDto newOrderDto = NewOrderDto.builder()
                .id(orderToken)
                .orderCartItemList(cartMapper.toListOrderCartItem(sessionService.getCart(sessionToken)))
                .totalPrice(sessionService.getCart(sessionToken).getTotalPrice())
                .user_id(sessionService.getSessionUser(sessionToken).getPhone_number())
                .address_id(sessionService.getAddress(sessionToken).getId())
                .payment_id(sessionService.getPayment(sessionToken).getId())
                .orderDateTime(LocalDateTime.now())
                .payment_code(payment_code)
                .status("CREATED")
                .build();
        CurrentOrder currentOrder = new CurrentOrder(orderToken, "CREATED");
        putCurrentOrder(currentOrder);
        orderKafkaTemplate.send("newOrder", newOrderDto);
        sessionService.clearCart(sessionToken);
        return payment_code;
    }

    public void cancelOrder(String sessionToken, String orderId) {
        OrderDto orderDto = sessionService.getUserOrderById(sessionToken, orderId);
        CurrentOrder currentOrder = getCurrentOrder(orderId);
        if (currentOrder == null && orderDto == null){
            throw new CurrentOrderNotFoundException();
        }
        NewStatusDto newStatusDto = NewStatusDto.builder()
                .order_id(orderDto.getId())
                .status("CANCELED")
                .build();
        statusKafkaTemplate.send("newStatus", newStatusDto);
    }


    public void setNewStatus(NewStatusDto newStatusDto) {
        CurrentOrder currentOrder = getCurrentOrder(newStatusDto.getOrder_id());
        currentOrder.setStatus(newStatusDto.getStatus());
        putCurrentOrder(currentOrder);
    }

    public void orderCanceler(String orderId) {
        deleteCurrentOrder(orderId);
    }

    public List<OrderDto> getCurrentOrders(String sessionToken) {
        return sessionService.getUserCurrentOrders(sessionToken);
    }

    private String generateOrderToken() {
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
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8083/api/init"))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception ex) {
            throw new BadConnectionToPaymentException();
        }
    }

    private CurrentOrder getCurrentOrder(String orderId) {
        return (CurrentOrder) redisTemplate.opsForHash().get(HASH_KEY, orderId);
    }

    private void putCurrentOrder(CurrentOrder currentOrder) {
        redisTemplate.opsForHash().put(HASH_KEY, currentOrder.getId(), currentOrder);
    }

    private void deleteCurrentOrder(String orderId) {
        redisTemplate.opsForHash().delete(HASH_KEY, orderId);
    }


}
