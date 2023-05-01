package com.funtravelapp.order.service;

import com.funtravelapp.order.dto.OrderDTO;
import com.funtravelapp.order.model.Order;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IOrderService {
    void insert(OrderDTO order);
    void updateStatusOrder(String status);
    Order findTheOrder(Order order);
    ResponseEntity<?> allOrdersByCustomerId(int customerId);
    ResponseEntity<?> allOrdersBySellerId(int sellerId);
}
