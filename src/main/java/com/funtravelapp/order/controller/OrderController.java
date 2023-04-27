package com.funtravelapp.order.controller;

import com.funtravelapp.order.dto.OrderDTO;
import com.funtravelapp.order.model.Order;
import com.funtravelapp.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    IOrderService orderService;

    @PostMapping("/add")
    public ResponseEntity<String> addNewOrder(@RequestBody OrderDTO orderDTO){
        Order order = new Order();
        order.setOrderId(0);
        order.setPackageId(orderDTO.getPackageId());
        order.setUserId(orderDTO.getUserId());
        orderService.insert(order);
        return new ResponseEntity<>("New Order has been created!", HttpStatus.OK);
    }

    @GetMapping("/getOrder")
    public @ResponseBody Order getOrder(@RequestParam("orderId") int id){
        Order order = new Order();
        order.setOrderId(id);
        return orderService.findTheOrder(order);
    }

    @GetMapping("/getOrderByUser")
    public ResponseEntity<?> getOrderByUser(@RequestParam("userId") int id){
        return orderService.allOrdersByUserId(id);
    }

//    @PutMapping("/update")
//    public ResponseEntity<String> updateOrder(@RequestParam("order_id") int orderId,
//                                              @RequestParam("status") String status){
//
//        return ;
//    }


}
