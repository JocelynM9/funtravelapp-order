package com.funtravelapp.order.repository;

import com.funtravelapp.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findByOrderId(Integer i);
}
