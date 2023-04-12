package com.funtravelapp.order.service;

import com.funtravelapp.order.model.Order;
import com.funtravelapp.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements IOrderService{
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
        return null;
    }

    @Override
    public List<Order> allOrdersByUserId() {
        return null;
    }
}
