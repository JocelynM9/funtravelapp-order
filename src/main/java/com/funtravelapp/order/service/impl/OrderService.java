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
import com.funtravelapp.order.service.KafkaService;
import com.funtravelapp.order.service.OrderStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderService implements IOrderService {
    @Autowired
    OrderRepository repository;
    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    KafkaTopicsConfig kafkaTopicConfig;
    @Autowired
    KafkaService kafkaService;


    @Override
    public void insert(String data) {
        CreateOrderDTO msg;
        try {
            System.out.println("\nData received: " + data);
            ObjectMapper mapper = new ObjectMapper();

            msg = mapper.readValue(data, CreateOrderDTO.class);
            if (msg.getData().isEmpty()){
                throw new Exception("No orders received");
            }

            List<Order> orders = new ArrayList<>();
            Order aOrder;
            String uid = UUID.randomUUID().toString();
            BigDecimal totalPrice = new BigDecimal("0");
            for (CreateOrderDTO.OrderData pkg :
                    msg.getData()) {
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
                totalPrice = totalPrice.add(pkg.getPrice());
            }
            repository.saveAll(orders);
            CreatePaymentDTO createPaymentDTO = CreatePaymentDTO.builder()
                    .chainingId(uid)
                    .customerId(orders.get(0).getCustomerId())
                    .sellerId(orders.get(0).getSellerId())
                    .amount(totalPrice)
                    .build();
            String sendMsg = mapper.writeValueAsString(createPaymentDTO);
            kafkaTemplate.send(kafkaTopicConfig.createPayment().name(), sendMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void updateStatusOrder(String data) {
        UpdateStatusDTO msg;

        try {
            ObjectMapper mapper = new ObjectMapper();

            msg = mapper.readValue(data, UpdateStatusDTO.class);

            if (!msg.getStatus().equalsIgnoreCase(OrderStatusEnum.WAITING_FOR_CONFIRMATION.toString())){
                throw new Exception("Payment failed");
            }

            int result = repository.updateOrderStatusByChainingId(msg.getStatus(), msg.getCustomerAcc(), msg.getSellerAcc(), msg.getChainingId());
            if (result == 0){
                throw new Exception("Failed to update order status");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public List<Order> confirmOrder(String authorizationHeader, Map<String, Boolean> roles, User user, String chainingId) throws Exception {
        List<Order> orders = repository.findByChainingId(chainingId);
        if (orders.isEmpty()){
            throw new Exception("Orders not found");
        }

        Integer sellerId = orders.get(0).getSellerId();
        if (sellerId.equals(user.getId())){
            throw new Exception("Unauthorized user");
        }

        String currStatus = orders.get(0).getStatus();
        if (!currStatus.equalsIgnoreCase(OrderStatusEnum.WAITING_FOR_CONFIRMATION.toString())){
            throw new Exception("Current order status is: " + currStatus);
        }

        return repository.updateOrderStatus(OrderStatusEnum.SUCCESS.toString(), chainingId);
    }

    @Override
    public List<Order> readByUserId(String authorizationHeader, Map<String, Boolean> roles, User user) {
        List<Order> orders;

        if (user.getRole().equalsIgnoreCase("customer")){
            orders = repository.findAllByCustomerId(user.getId());
        }else {
            orders = repository.findAllBySellerId(user.getId());
        }
        return orders;
    }

    @Override
    public List<Order> readByChainingId(String authorizationHeader, Map<String, Boolean> roles, User user, String chainingId) {
        List<Order> orders;

        if (user.getRole().equalsIgnoreCase("customer")){
            orders = repository.findAllByCustomerIdAndChainingId(user.getId(), chainingId);
        }else {
            orders = repository.findAllBySellerIdAndChainingId(user.getId(), chainingId);
        }
        return orders;
    }
}
