package com.example.samokatclient.repositories.session;

import com.example.samokatclient.entities.session.Session;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class SessionRepository {

    private static final String HASH_KEY = "Session";
    private final RedisTemplate<String, Object> redisTemplate;

    public void save(Session session) {
        putSession(session);
    }

    public Optional<Session> findById(String id) {
        return getSession(id);
    }

    public boolean existsById(String id) {
        return hasKey(id);
    }

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
    private Optional<Session> getSession(String key) {
        log.info("Redis выполняет операцию get - HASH_KEY: {}, key: {}", HASH_KEY, key);
        return Optional.ofNullable(redisTemplate.opsForHash().get(HASH_KEY, key))
                .map(object -> (Session) object);
    }

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
    private void putSession(Session session) {
        log.info("Redis выполняет операцию put - HASH_KEY: {}, key: {}", HASH_KEY, session.getId());
        redisTemplate.opsForHash().put(HASH_KEY, session.getId(), session);
    }

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
    private boolean hasKey(String key) {
        log.info("Redis выполняет операцию hasKey - HASH_KEY: {}, key: {}", HASH_KEY, key);
        return redisTemplate.opsForHash().hasKey(HASH_KEY, key);
    }

}
