package com.funtravelapp.order.service;

import com.funtravelapp.order.dto.OrderDTO;
import com.funtravelapp.order.model.Order;
import com.funtravelapp.order.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface IOrderService {
    void insert(String order);
    void updateStatusOrder(String data);
    List<Order> confirmOrder(String authorizationHeader, Map<String, Boolean> roles, User user, String chainingId) throws Exception;
    List<Order> readByUserId(String authorizationHeader, Map<String, Boolean> roles, User user);
    List<Order> readByChainingId(String authorizationHeader, Map<String, Boolean> roles, User user, String chainingId);
}
