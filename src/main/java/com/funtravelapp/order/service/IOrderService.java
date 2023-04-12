package com.funtravelapp.order.service;

import com.funtravelapp.order.model.Order;

import java.util.List;

public interface IOrderService {
    void insert(Order order);
    void update(Order order);
    Order findTheOrder(Order order);
    List<Order> allOrdersByUserId();
}
