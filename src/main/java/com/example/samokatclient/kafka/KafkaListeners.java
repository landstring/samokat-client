package com.example.samokatclient.kafka;

import com.example.samokatclient.DTO.currentOrder.NewStatusDto;
import com.example.samokatclient.services.StatusService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class KafkaListeners {
    private final StatusService statusService;

    @KafkaListener(
            topics = "newStatusHandler",
            groupId = "newStatusGroupId",
            containerFactory = "statusFactory"
    )
    void newStatusListener(ConsumerRecord<String, NewStatusDto> message) {
        log.info("Пришло сообщение в Kafka о новом статусе заказа: {}", message.value());
        statusService.newStatusHandler(message.value());
    }

}
