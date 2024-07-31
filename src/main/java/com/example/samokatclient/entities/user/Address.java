package com.example.samokatclient.entities.user;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Address implements Serializable {
    @Id
    String id;
    String city;
    String home;
    String apartment;
    String entrance;
    Integer plate;
    @Indexed
    String userId;
}