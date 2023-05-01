package com.funtravelapp.order.repository;

import com.funtravelapp.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findByOrderId(Integer i);

    @Query("SELECT o from Order o WHERE o.customerId= ?1")
    List<Order> findAllByCustomerId(int customerId);

    @Query("SELECT o from Order o WHERE o.sellerId= ?1")
    List<Order> findAllBySellerId(int sellerId);
}
