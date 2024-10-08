package com.example.samokatclient.services.Impl;

import com.example.samokatclient.DTO.currentOrder.NewStatusDto;
import com.example.samokatclient.entities.currentOrder.CurrentOrderClient;
import com.example.samokatclient.enums.CurrentOrderStatus;
import com.example.samokatclient.exceptions.order.CurrentOrderNotFoundException;
import com.example.samokatclient.repositories.currentOrder.CurrentOrderClientRepository;
import com.example.samokatclient.services.StatusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StatusServiceImpl implements StatusService {
    private final CurrentOrderClientRepository currentOrderClientRepository;

    @Override
    public void newStatusHandler(NewStatusDto newStatusDto) {
        if (newStatusDto.getStatus() == CurrentOrderStatus.CANCELED) {
            cancelOrder(newStatusDto.getOrderId());
        } else {
            setStatus(newStatusDto);
        }
    }

    private void setStatus(NewStatusDto newStatusDto) {
        CurrentOrderClient currentOrderClient = getCurrentOrderClient(newStatusDto.getOrderId());
        currentOrderClient.setStatus(newStatusDto.getStatus());
        currentOrderClientRepository.save(currentOrderClient);
    }

    private void cancelOrder(String orderId) {
        CurrentOrderClient currentOrderClient = getCurrentOrderClient(orderId);
        currentOrderClientRepository.delete(currentOrderClient.getId());
    }

    private CurrentOrderClient getCurrentOrderClient(String orderId) {
        return currentOrderClientRepository.findById(orderId).orElseThrow(
                () -> new CurrentOrderNotFoundException("Такого текущего заказа не существует")
        );
    }

}
