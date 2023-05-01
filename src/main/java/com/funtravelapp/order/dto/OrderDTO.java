package com.funtravelapp.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class OrderDTO {

    @JsonProperty("package_id")
    private Integer packageId;

    @JsonProperty("customer_id")
    private Integer customerId;

    @JsonProperty("seller_id")
    private Integer sellerId;

    @JsonProperty("email")
    private String email;

}
