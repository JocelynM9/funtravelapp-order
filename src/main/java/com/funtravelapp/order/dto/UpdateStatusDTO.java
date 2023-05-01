package com.funtravelapp.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateStatusDTO {

    @JsonProperty("chaining_id")
    private String chainingId;

    @JsonProperty("customer_acc")
    private String customerAcc;

    @JsonProperty("seller_acc")
    private String sellerAcc;

    @JsonProperty("status")
    private String status;
}
