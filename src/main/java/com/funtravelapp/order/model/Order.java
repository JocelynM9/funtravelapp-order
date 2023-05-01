package com.funtravelapp.order.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "order")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    @Column(name = "package_id")
    private Integer packageId;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "seller_id")
    private Integer sellerId;

    @Column(name = "chaining_id")
    private String chainingId;

    @Column(name = "customer_acc")
    private String customerAcc;

    @Column(name = "seller_acc")
    private String sellerAcc;

    @Column(name = "email")
    private String email;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "status")
    private String status;


}
