package com.example.samokatclient.services;

import com.example.samokatclient.DTO.order.NewStatusDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class StatusService {
    private final CurrentOrderService currentOrderService;

    public void newStatusHandler(NewStatusDto newStatusDto) {
        if (Objects.equals(newStatusDto.getStatus(), "CANCELED")) {
            currentOrderService.orderCanceler(newStatusDto.getOrder_id());
        } else {
            currentOrderService.setNewStatus(newStatusDto);
        }
    }

}
