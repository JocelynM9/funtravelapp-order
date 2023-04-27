package com.funtravelapp.order.service.impl;

import com.funtravelapp.order.model.Order;
import com.funtravelapp.order.repository.OrderRepository;
import com.funtravelapp.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements IOrderService {
    @Autowired
    OrderRepository repository;


    @Override
    public void insert(Order order) {
        repository.save(order);
    }

    @Override
    public void update(Order order) {
        repository.save(order);
    }

    @Override
    public Order findTheOrder(Order order) {
        return repository.findByOrderId(order.getOrderId());
    }

    @Override
    public ResponseEntity<?> allOrdersByUserId(int userId) {
        return new ResponseEntity<>(repository.findAllByUserId(userId), HttpStatus.OK);
    }

}
