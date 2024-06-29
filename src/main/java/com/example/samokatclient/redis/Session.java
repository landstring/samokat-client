package com.example.samokatclient.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("Session")
@Getter
@Setter
public class Session {
    @Id
    private String id;
    private String user_id;
    private String cartToken;

    public Session(String sessionToken, String cartToken){
        this.id = sessionToken;
        this.cartToken = cartToken;
        this.user_id = null;
    }

    public Session(String sessionToken, String user_id, String cartToken){
        this.id = sessionToken;
        this.user_id = user_id;
        this.cartToken = cartToken;
    }
}

// TODO: 30.06.2024 Добавить список токенов актуальных заказов и создать отдельный словарь для актуальных заказов 
