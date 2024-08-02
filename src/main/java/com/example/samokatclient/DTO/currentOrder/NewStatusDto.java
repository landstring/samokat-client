package com.example.samokatclient.DTO.currentOrder;

import com.example.samokatclient.enums.CurrentOrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewStatusDto {

    @JsonProperty("order_id")
    String orderId;

    CurrentOrderStatus status;
}
