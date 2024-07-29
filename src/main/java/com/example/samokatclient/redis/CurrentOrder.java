package com.example.samokatclient.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("CurrentOrderClient")
@AllArgsConstructor
@Getter
@Setter
public class CurrentOrder implements Serializable {
    private String id;
    private String status;
}