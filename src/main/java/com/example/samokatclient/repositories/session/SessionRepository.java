package com.example.samokatclient.repositories.session;

import com.example.samokatclient.entities.session.Session;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
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

    private Optional<Session> getSession(String key) {
        return Optional.ofNullable(redisTemplate.opsForHash().get(HASH_KEY, key))
                .map(object -> (Session) object);
    }

    private void putSession(Session session) {
        redisTemplate.opsForHash().put(HASH_KEY, session.getId(), session);
    }

    private boolean hasKey(String key) {
        return redisTemplate.opsForHash().hasKey(HASH_KEY, key);
    }

}
