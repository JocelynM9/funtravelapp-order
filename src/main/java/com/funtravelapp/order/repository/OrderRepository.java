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
    List<Order> findByCustomerId(Integer customerId);

    List<Order> findBySellerId(Integer sellerId);
    List<Order> findByCustomerIdAndChainingId(Integer customerId, String chainingId);
    List<Order> findBySellerIdAndChainingId(Integer sellerId, String chainingId);
    List<Order> findByChainingIdAndSellerId(String chainingId, Integer sellerId);

    @Modifying
    @Query(
            value = "UPDATE orders set status = :status, customer_acc = :customerAcc, seller_acc = :sellerAcc WHERE chaining_id = :chainingId",
            nativeQuery = true
    )
    int updateOrderStatusByChainingId(@Param("status") String status, @Param("customerAcc") String customerAcc, @Param("sellerAcc") String sellerAcc, @Param("chainingId") String chainingId);

    @Modifying
    @Query(
            value = "UPDATE orders set status = :status WHERE chaining_id = :chainingId",
            nativeQuery = true
    )
    int updateOrderStatus(@Param("status") String status, @Param("chainingId") String chainingId);
}
