package com.funtravelapp.order.service;

import com.funtravelapp.order.dto.CreateOrderDTO;

public interface KafkaService {

    public void consumeCart(CreateOrderDTO dto);

    public void consumeUpdateStatusOrder();
}
