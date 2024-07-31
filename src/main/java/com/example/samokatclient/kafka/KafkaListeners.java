package com.example.samokatclient.kafka;

import com.example.samokatclient.DTO.currentOrder.NewStatusDto;
import com.example.samokatclient.services.StatusService;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KafkaListeners {
    private final StatusService statusService;

    @KafkaListener(
            topics = "newStatus",
            groupId = "newStatusGroupId",
            containerFactory = "statusFactory"
    )
    void newStatusListener(ConsumerRecord<String, NewStatusDto> message) {
        statusService.newStatusHandler(message.value());
    }

}
