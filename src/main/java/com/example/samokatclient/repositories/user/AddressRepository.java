package com.example.samokatclient.repositories.user;

import com.example.samokatclient.entities.user.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends MongoRepository<Address, String> {
    Optional<Address> findByIdAndUserId(String id, String userId);

    List<Address> findByUserId(String user_id);
}
