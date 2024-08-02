package com.example.samokatclient.DTO.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonPropertyOrder({"id", "card_number", "expiration_date", "cvc"})
public class PaymentDto {
    String id;

    @JsonProperty("card_number")
    String cardNumber;

    @JsonProperty("expiration_date")
    String expirationDate;

    Integer cvc;
}