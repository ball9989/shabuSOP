package com.example.shabushabu.controller;

import com.example.shabushabu.pojo.Order;
import com.example.shabushabu.pojo.Orders;
import com.example.shabushabu.repository.OrderService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    protected Orders orders = new Orders();
    protected Orders menu = new Orders();

    @RequestMapping(value = "/orders",method = RequestMethod.GET)
    public ResponseEntity<?> getOrders(){
        ArrayList<Order> o = (ArrayList<Order>) orderService.getOrder();
        orders.model = o;
        return ResponseEntity.ok(orders);

    }

    @RequestMapping(value = "/menu",method = RequestMethod.GET)
    public ResponseEntity<?> getMenu(){
        Object obj = rabbitTemplate.convertSendAndReceive("Direct","test","get Menu Data");
        ArrayList<Order> menulist = (ArrayList<Order>) obj;
        menu.model = menulist;

        return ResponseEntity.ok(menu);
    };

}
