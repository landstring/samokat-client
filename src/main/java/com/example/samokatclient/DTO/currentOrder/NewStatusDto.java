package com.example.samokatclient.DTO.currentOrder;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewStatusDto {
    String orderId;
    String status;
}
