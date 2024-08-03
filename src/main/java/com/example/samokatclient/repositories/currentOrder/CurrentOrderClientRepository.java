package com.example.samokatclient.repositories.currentOrder;

import com.example.samokatclient.entities.currentOrder.CurrentOrderClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
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

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
    private Optional<CurrentOrderClient> getCurrentOrderClient(String key) {
        log.info("Redis выполняет операцию get - HASH_KEY: {}, key: {}", HASH_KEY, key);
        return Optional.ofNullable(redisTemplate.opsForHash().get(HASH_KEY, key))
                .map(object -> (CurrentOrderClient) object);
    }

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
    private List<CurrentOrderClient> getCurrentOrderClientList(List<String> hashKeys){
        log.info("Redis выполняет операцию get - HASH_KEY: {}, keys: {}", HASH_KEY, hashKeys);
        return hashKeys.stream()
                .map(hashKey -> redisTemplate.opsForHash().get(HASH_KEY, hashKey))
                .map(object -> (CurrentOrderClient) object)
                .toList();
    }

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
    private void putCurrentOrderClient(CurrentOrderClient currentOrderClient) {
        log.info("Redis выполняет операцию put - HASH_KEY: {}, key: {}", HASH_KEY, currentOrderClient.getId());
        redisTemplate.opsForHash().put(HASH_KEY, currentOrderClient.getId(), currentOrderClient);
    }

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
    private void deleteCurrentOrderClient(String key) {
        log.info("Redis выполняет операцию delete - HASH_KEY: {}, key: {}", HASH_KEY, key);
        redisTemplate.opsForHash().delete(HASH_KEY, key);
    }
}