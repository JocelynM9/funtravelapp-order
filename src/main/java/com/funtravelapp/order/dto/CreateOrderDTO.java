package com.funtravelapp.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDTO {

    private Object data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class OrderData{
        private Integer customerId;

        private Integer sellerId;

        private Integer packageId;
    }

}
