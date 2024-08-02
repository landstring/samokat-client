package com.example.samokatclient.repositories.currentOrder;

import com.example.samokatclient.entities.currentOrder.CurrentOrderClient;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CurrentOrderClientRepository {
    private static final String HASH_KEY = "CurrentOrderClient";
    private final RedisTemplate<String, Object> redisTemplate;

    public Optional<CurrentOrderClient> findById(String id) {
        return getCurrentOrderClient(id);
    }

    public List<CurrentOrderClient> findAllCurrentOrderClientsByUserId(List<String> hashKeys) {
        return getCurrentOrderClientList(hashKeys);
    }

    public void save(CurrentOrderClient currentOrderClient) {
        putCurrentOrderClient(currentOrderClient);
    }

    public void delete(String id) {
        deleteCurrentOrderClient(id);
    }

    private Optional<CurrentOrderClient> getCurrentOrderClient(String key) {
        return Optional.ofNullable(redisTemplate.opsForHash().get(HASH_KEY, key))
                .map(object -> (CurrentOrderClient) object);
    }

    private List<CurrentOrderClient> getCurrentOrderClientList(List<String> hashKeys){
        return hashKeys.stream()
                .map(hashKey -> redisTemplate.opsForHash().get(HASH_KEY, hashKey))
                .map(object -> (CurrentOrderClient) object)
                .toList();
    }

    private void putCurrentOrderClient(CurrentOrderClient currentOrderClient) {
        redisTemplate.opsForHash().put(HASH_KEY, currentOrderClient.getId(), currentOrderClient);
    }

    private void deleteCurrentOrderClient(String key) {
        redisTemplate.opsForHash().delete(HASH_KEY, key);
    }
}