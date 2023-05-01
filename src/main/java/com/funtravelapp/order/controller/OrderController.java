package com.funtravelapp.order.controller;

import com.funtravelapp.order.dto.OrderDTO;
import com.funtravelapp.order.model.Order;
import com.funtravelapp.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    IOrderService orderService;

    @PostMapping("/new")
    public ResponseEntity<String> addNewOrder(@RequestBody OrderDTO orderDTO){

        orderService.insert(orderDTO);
        return new ResponseEntity<>("New Order has been created!", HttpStatus.OK);
    }

    @GetMapping("/getOrder")
    public @ResponseBody Order getOrder(@RequestParam("orderId") int id){
        Order order = new Order();
        order.setOrderId(id);
        return orderService.findTheOrder(order);
    }

    @GetMapping("/getOrderByCustomer")
    public ResponseEntity<?> getOrderByCustomer(@RequestParam("customerId") Integer id){
        return orderService.allOrdersByCustomerId(id);
    }

    @GetMapping("/getOrderBySeller")
    public ResponseEntity<?> getOrderBySeller(@RequestParam("sellerId") Integer id){
        return orderService.allOrdersBySellerId(id);
    }

//    @PutMapping("/update")
//    public ResponseEntity<String> updateStatusOrder(@RequestParam("order_id") int orderId,
//                                              @RequestParam("status") String status){
//
//        return ;
//    }


}
