package com.example.samokatclient;

import com.example.samokatclient.DTO.currentOrder.NewStatusDto;
import com.example.samokatclient.enums.CurrentOrderStatus;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class SamokatClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SamokatClientApplication.class, args);

    }

}
