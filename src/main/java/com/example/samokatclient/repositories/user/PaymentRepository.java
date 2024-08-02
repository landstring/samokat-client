package com.example.samokatclient.repositories.user;

import com.example.samokatclient.entities.user.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends MongoRepository<Payment, String> {
    Optional<Payment> findByIdAndUserId(String id, String userId);

    List<Payment> findByUserId(String userId);
}
