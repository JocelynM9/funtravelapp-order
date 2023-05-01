package com.funtravelapp.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CreatePaymentDTO {
    @JsonProperty("chaining_id")
    private String chainingId;

    @JsonProperty("customer_acc")
    private String customerAcc;

    @JsonProperty("seller_acc")
    private String sellerAcc;

    @JsonProperty("amount")
    private BigDecimal amount;
}
