package com.example.samokatclient.services;

import com.example.samokatclient.DTO.order.CurrentOrderDto;
import com.example.samokatclient.DTO.order.NewOrderDto;
import com.example.samokatclient.exceptions.order.CurrentOrderNotFoundException;
import com.example.samokatclient.mappers.CartMapper;
import com.example.samokatclient.mappers.CurrentOrderMapper;
import com.example.samokatclient.redis.CurrentOrder;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CurrentOrderService {
    private final static String HASH_KEY = "CurrentOrder";
    private final RedisTemplate redisTemplate;
    private final CurrentOrderMapper currentOrderMapper;
    private final CartMapper cartMapper;
    private final KafkaTemplate<String, NewOrderDto> kafkaTemplate;
    private final SessionService sessionService;

    public void createOrder(String sessionToken){
        String orderToken = UUID.randomUUID().toString();
        NewOrderDto newOrderDto = new NewOrderDto(
                orderToken,
                cartMapper.toListOrderCartItem(sessionService.getCart(sessionToken)),
                sessionService.getCart(sessionToken).totalCost,
                sessionService.getSessionUser(sessionToken).phone_number,
                sessionService.getAddress(sessionToken).id,
                sessionService.getPayment(sessionToken).id,
                LocalDateTime.now(),
                "CREATED"
        );
        CurrentOrderDto currentOrderDto = new CurrentOrderDto();
        currentOrderDto.id = orderToken;

        kafkaTemplate.send("newOrder",  newOrderDto);
    }

    public CurrentOrderDto getCurrentOrderByKey(String currentOrder_id){
        CurrentOrder currentOrder = (CurrentOrder) redisTemplate.opsForHash().get(HASH_KEY, currentOrder_id);
        if (currentOrder == null){
            throw new CurrentOrderNotFoundException();
        }
        return currentOrderMapper.toDto(currentOrder);
    }

    public void addCurrentOrder(CurrentOrderDto currentOrderDto, String orderToken){
        CurrentOrder currentOrder = currentOrderMapper.fromDto(currentOrderDto);
        redisTemplate.opsForHash().put(HASH_KEY, orderToken, currentOrder);
    }


}

// TODO: 02.07.2024 Реализовать логику отмены заказа