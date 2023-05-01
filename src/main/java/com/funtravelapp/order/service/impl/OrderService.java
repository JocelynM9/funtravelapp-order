package com.funtravelapp.order.service.impl;

import com.funtravelapp.order.dto.OrderDTO;
import com.funtravelapp.order.model.Order;
import com.funtravelapp.order.repository.OrderRepository;
import com.funtravelapp.order.service.IOrderService;
import com.funtravelapp.order.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements IOrderService {
    @Autowired
    OrderRepository repository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    KafkaService kafkaService;


    @Override
    public void insert(OrderDTO order) {

        Order aOrder = Order.builder()
                .orderId(0)
                .packageId()
                .customerId()
                .sellerId()
                .email(order.getEmail())
                .status("PENDING")
                .build();

        repository.save(aOrder);
    }

    @Override
    public void updateStatusOrder(String status) {

    }


    @Override
    public Order findTheOrder(Order order) {
        return repository.findByOrderId(order.getOrderId());
    }

    @Override
    public ResponseEntity<?> allOrdersByCustomerId(int customerId) {
        return new ResponseEntity<>(repository.findAllByCustomerId(customerId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> allOrdersBySellerId(int sellerId) {
        return new ResponseEntity<>(repository.findAllBySellerId(sellerId), HttpStatus.OK);
    }


}
