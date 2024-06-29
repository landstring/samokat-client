package com.example.samokatclient.entities.user;

import com.example.samokatclient.DTO.details.OrderCartItem;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.List;

@Document
@Data
public class Order {
    @Id
    private String id; //номер телефона

    private List<OrderCartItem> cartItemList;
    private Long totalPrice;
    @Indexed
    private String user_id;
    private String address_id;
    private String payment_id;

    private LocalDateTime orderDateTime;
}
