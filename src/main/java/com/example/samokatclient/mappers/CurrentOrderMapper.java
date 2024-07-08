package com.example.samokatclient.mappers;

import com.example.samokatclient.DTO.order.CurrentOrderDto;
import com.example.samokatclient.redis.CurrentOrder;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class CurrentOrderMapper {
    private final CartMapper cartMapper;
    public CurrentOrderDto toDto(CurrentOrder currentOrder){
        return new CurrentOrderDto(
                currentOrder.getId(),
                cartMapper.listOrderCartItemToDto(currentOrder.getOrderCartItemList()),
                currentOrder.getAddress_id(),
                currentOrder.getPayment_id(),
                currentOrder.getOrderDateTime(),
                currentOrder.getStatus()
            );
    }

    public CurrentOrder fromDto(CurrentOrderDto currentOrderDto){
        CurrentOrder currentOrder = new CurrentOrder();
        currentOrder.setId(currentOrderDto.id);
        currentOrder.setOrderCartItemList(cartMapper.toListOrderCartItem(currentOrderDto.cartDto));
        currentOrder.setAddress_id(currentOrderDto.address_id);
        currentOrder.setPayment_id(currentOrderDto.payment_id);
        currentOrder.setOrderDateTime(currentOrderDto.orderDateTime);
        currentOrder.setStatus(currentOrderDto.status);
        return currentOrder;
    }
}
