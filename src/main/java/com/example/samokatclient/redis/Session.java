package com.example.samokatclient.redis;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@RedisHash("Session")
@Getter
@Setter
@Builder
public class Session implements Serializable {
    @Id
    private String id;
    private String user_id;
    private String cartToken;
    private String address_id;
    private String payment_id;
    private List<String> currentOrdersId;
}