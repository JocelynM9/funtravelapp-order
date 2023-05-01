package com.funtravelapp.order.controller;

import com.funtravelapp.order.middleware.RoleService;
import com.funtravelapp.order.responseMapper.ResponseMapper;
import com.funtravelapp.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    IOrderService orderService;
    @Autowired
    RoleService roleService;

//    @KafkaListener(
//            topics = "CreateOrder",
//            groupId = "CreateOrder-1"
//    )
    @PostMapping("/create")
    public void addNewOrder(@RequestBody String data){
        orderService.insert(data);
    }

    @KafkaListener(
            topics = "UpdateStatusOrder",
            groupId = "UpdateStatusOrder-1"
    )
    public void updateStatusOrder(String data){
        orderService.updateStatusOrder(data);
    }

    @GetMapping("")
    public ResponseEntity<?> readByUserId(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){
        try{
            return ResponseMapper.ok(null, orderService.readByUserId(authorizationHeader, this.roleService.getCustomerAndSeller(), null));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseMapper.badRequest(e.getMessage(), null);
        }
    }

    @GetMapping("/{chainingId}")
    public ResponseEntity<?> readByChainingId(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @PathVariable("chainingId") String chainingId){
        try{
            return ResponseMapper.ok(null, orderService.readByChainingId(authorizationHeader, this.roleService.getCustomerAndSeller(), null, chainingId));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseMapper.badRequest(e.getMessage(), null);
        }
    }

    @PostMapping("/confirm-order/{chainingId}")
    public ResponseEntity<?> confirmOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @PathVariable("chainingId") String chainingId){
        try{
            return ResponseMapper.ok(null, orderService.confirmOrder(authorizationHeader, this.roleService.getSeller(), null, chainingId));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseMapper.badRequest(e.getMessage(), null);
        }
    }
}
