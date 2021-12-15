package com.example.shabushabu.controller;

import com.example.shabushabu.pojo.Menu;
import com.example.shabushabu.pojo.Menus;
import com.example.shabushabu.repository.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    protected Menus orders = new Menus();

    @RequestMapping(value = "/orders",method = RequestMethod.GET)
    public ResponseEntity<?> getOrders(){
        ArrayList<Menu> o = (ArrayList<Menu>) orderService.getOrder();
        orders.model = o;
        return ResponseEntity.ok(orders);

    }
}
