package com.example.samokatclient.repositories.currentOrder;

import com.example.samokatclient.entities.currentOrder.CurrentOrderClient;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CurrentOrderClientRepository extends CrudRepository<CurrentOrderClient, String> {
    List<CurrentOrderClient> findCurrentOrderClientsByUserId(String userId);
}
