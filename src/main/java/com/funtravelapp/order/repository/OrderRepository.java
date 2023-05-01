package com.funtravelapp.order.repository;

import com.funtravelapp.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
    @Query("SELECT o from Order o WHERE o.customerId= ?1 AND o.chainingId = ?2")
    List<Order> findAllByCustomerIdAndChainingId(int customerId, String chainingId);
    @Query("SELECT o from Order o WHERE o.sellerId= ?1 AND o.chainingId = ?2")
    List<Order> findAllBySellerIdAndChainingId(int sellerId, String chainingId);
    List<Order> findByChainingId(String chainingId);

    @Modifying
    @Query(
            value = "UPDATE order set status = :status, customer_acc = :customerAcc, seller_acc = :sellerAcc WHERE chaining_id = :chainingId",
            nativeQuery = true
    )
    int updateOrderStatusByChainingId(@Param("status") String status, @Param("customerAcc") String customerAcc, @Param("sellerAcc") String sellerAcc, @Param("chainingId") String chainingId);

    @Modifying
    @Query(
            value = "UPDATE order set status = :status WHERE chaining_id = :chainingId",
            nativeQuery = true
    )
    List<Order> updateOrderStatus(@Param("status") String status, @Param("chainingId") String chainingId);
}
