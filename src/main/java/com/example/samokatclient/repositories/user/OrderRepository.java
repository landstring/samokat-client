package com.example.samokatclient.repositories.user;

import com.example.samokatclient.entities.user.Order;
import com.example.samokatclient.enums.OrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {
    Optional<Order> findByIdAndUserId(String id, String userId);

    List<Order> findByUserId(String userId);

    List<Order> findAllByUserIdAndStatus(String userId, OrderStatus orderStatus);
}
