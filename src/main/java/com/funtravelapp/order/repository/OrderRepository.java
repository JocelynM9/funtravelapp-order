package com.funtravelapp.order.repository;

import com.funtravelapp.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findByOrderId(Integer i);

    @Query("SELECT o from Order o WHERE o.userId= ?1")
    List<Order> findAllByUserId(int userId);
}
