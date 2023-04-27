package com.funtravelapp.order.service;

import com.funtravelapp.order.model.Order;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IOrderService {
    void insert(Order order);
    void update(Order order);
    Order findTheOrder(Order order);
    ResponseEntity<?> allOrdersByUserId(int userId);
}
