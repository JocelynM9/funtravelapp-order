package com.funtravelapp.order.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.funtravelapp.order.dto.CreateOrderDTO;
import com.funtravelapp.order.dto.CreatePaymentDTO;
import com.funtravelapp.order.dto.UpdateStatusDTO;
import com.funtravelapp.order.kafka.KafkaTopicsConfig;
import com.funtravelapp.order.model.Order;
import com.funtravelapp.order.model.User;
import com.funtravelapp.order.repository.OrderRepository;
import com.funtravelapp.order.service.IOrderService;
import com.funtravelapp.order.service.OrderStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderService implements IOrderService {
    @Autowired
    OrderRepository repository;
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    KafkaTopicsConfig kafkaTopicConfig;

    @Override
    public void insert(String data) {
        CreateOrderDTO msg;
        try {
            System.out.println("\nData received: " + data);
            ObjectMapper mapper = new ObjectMapper();

            msg = mapper.readValue(data, CreateOrderDTO.class);
            if (msg.getData().isEmpty()) {
                throw new Exception("No orders received");
            }

            List<Order> orders = new ArrayList<>();
            Order aOrder;
            String uid = UUID.randomUUID().toString();
            for (CreateOrderDTO.OrderData pkg :
                    msg.getData()) {
                System.out.println(pkg);
                aOrder = Order.builder()
                        .packageId(pkg.getPackageId())
                        .customerId(pkg.getCustomerId())
                        .sellerId(pkg.getSellerId())
                        .chainingId(uid)
                        .customerEmail(pkg.getCustomerEmail())
                        .sellerEmail(pkg.getSellerEmail())
                        .price(pkg.getPrice())
                        .status("PENDING")
                        .build();
                orders.add(aOrder);
            }
            System.out.println(uid);
            repository.saveAll(orders);
            String sendMsg;
            for (Order order :
                    orders) {
                CreatePaymentDTO createPaymentDTO = CreatePaymentDTO.builder()
                        .chainingId(uid)
                        .customerId(order.getCustomerId())
                        .sellerId(order.getSellerId())
                        .amount(order.getPrice())
                        .build();
                 sendMsg = mapper.writeValueAsString(createPaymentDTO);
                kafkaTemplate.send(kafkaTopicConfig.createPayment().name(), sendMsg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void updateStatusOrder(String data) {
        UpdateStatusDTO msg;
        System.out.println("\nData received: " + data);
        try {
            ObjectMapper mapper = new ObjectMapper();

            msg = mapper.readValue(data, UpdateStatusDTO.class);

            if (!msg.getStatus().equalsIgnoreCase(OrderStatusEnum.WAITING_FOR_CONFIRMATION.toString())) {
                throw new Exception("Payment failed");
            }

            int result = repository.updateOrderStatusByChainingId(msg.getStatus(), msg.getCustomerAcc(), msg.getSellerAcc(), msg.getChainingId());
            if (result == 0) {
                throw new Exception("Failed to update order status");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public List<Order> confirmOrder(String authorizationHeader, Map<String, Boolean> roles, User user, String chainingId) throws Exception {
        List<Order> orders = repository.findByChainingIdAndSellerId(chainingId, user.getId());
        if (orders.isEmpty()) {
            throw new Exception("Orders not found");
        }

        Integer sellerId = orders.get(0).getSellerId();
        if (!sellerId.equals(user.getId())) {
            throw new Exception("Unauthorized user");
        }

        String currStatus = orders.get(0).getStatus();
        if (!currStatus.equalsIgnoreCase(OrderStatusEnum.WAITING_FOR_CONFIRMATION.toString())) {
            throw new Exception("Current order status is: " + currStatus);
        }

        int res = repository.updateOrderStatus(OrderStatusEnum.SUCCESS.toString(), chainingId);
        if (res == 0) {
            throw new Exception("Transaction cannot be processed");
        }

        for (Order order :
                orders) {
            order.setStatus(OrderStatusEnum.SUCCESS.toString());
        }

        return orders;
    }

    @Override
    public List<Order> readByUserId(String authorizationHeader, Map<String, Boolean> roles, User user) {
        List<Order> orders;
        System.out.println(user);
        if (user.getRole().equalsIgnoreCase("customer")) {
            orders = repository.findByCustomerId(user.getId());
        } else {
            orders = repository.findBySellerId(user.getId());
        }
        return orders;
    }

    @Override
    public List<Order> readByChainingId(String authorizationHeader, Map<String, Boolean> roles, User user, String chainingId) {
        List<Order> orders;

        if (user.getRole().equalsIgnoreCase("customer")) {
            orders = repository.findByCustomerIdAndChainingId(user.getId(), chainingId);
        } else {
            orders = repository.findBySellerIdAndChainingId(user.getId(), chainingId);
        }
        return orders;
    }
}
