package com.example.samokatclient.mappers;

import com.example.samokatclient.DTO.currentOrder.CurrentOrderClientDto;
import com.example.samokatclient.entities.currentOrder.CurrentOrderClient;
import lombok.AllArgsConstructor;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CurrentOrderClientMapper {

    private final CartMapper cartMapper;
    private final AddressMapper addressMapper;
    private final PaymentMapper paymentMapper;

    public CurrentOrderClientDto toDto(CurrentOrderClient currentOrderClient){
        return CurrentOrderClientDto.builder()
                .id(currentOrderClient.getId())
                .cartDto(cartMapper.cartToDto(currentOrderClient.getCart()))
                .addressDto(addressMapper.toDto(currentOrderClient.getAddress()))
                .paymentDto(paymentMapper.toDto(currentOrderClient.getPayment()))
                .orderDateTime(currentOrderClient.getOrderDateTime())
                .paymentCode(currentOrderClient.getPaymentCode())
                .status(currentOrderClient.getStatus())
                .build();
    }
}
