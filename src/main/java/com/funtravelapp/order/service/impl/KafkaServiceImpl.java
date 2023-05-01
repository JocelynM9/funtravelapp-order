package com.funtravelapp.order.service.impl;

import com.funtravelapp.order.dto.CreateOrderDTO;
import com.funtravelapp.order.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaServiceImpl implements KafkaService {

    @Autowired
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaServiceImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void consumeCart(CreateOrderDTO dto) {
        System.out.println("Received new order : " + dto);
    }

    @Override
    public void consumeUpdateStatusOrder() {

    }
}
