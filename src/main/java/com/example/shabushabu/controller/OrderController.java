package com.example.shabushabu.controller;

import com.example.shabushabu.pojo.Menu;
import com.example.shabushabu.pojo.Order;
import com.example.shabushabu.pojo.Orders;
import com.example.shabushabu.pojo.ServeOrder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class OrderController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    protected Orders orders = new Orders();

    @RequestMapping(value = "/sendOrder", method = RequestMethod.POST)
    public boolean sendOrder(@RequestBody MultiValueMap<String, String> n) {
        Map<String, String> d = n.toSingleValueMap();
        Integer size = Integer.parseInt(d.get("size"));
        Integer tableNo = Integer.parseInt(d.get("tableNo"));
        Double totalPrice = Double.parseDouble(d.get("totalPrice"));
        String status = d.get("status");

        ArrayList<ServeOrder> arrList = new ArrayList<>();
        for (int i=0;i<size;i++) {
            String id = d.get(i+"_id");
            String name = d.get(i+"_name");
            Integer count = Integer.parseInt(d.get(i+"_count"));
            Double price = Double.parseDouble(d.get(i+"_price"));
            arrList.add(new ServeOrder(id, name, count, price));
        }
        System.out.println(arrList);
        rabbitTemplate.convertAndSend("ShabuOrder", "addOrder", new Order(null, tableNo, totalPrice, status,arrList));
        return true;
    }

    @RequestMapping(value = "/orders",method = RequestMethod.GET)
    public ResponseEntity<?> getMenu(){
        Object obj = rabbitTemplate.convertSendAndReceive("ShabuOrder","getOrder","");
        ArrayList<Order> orderList = (ArrayList<Order>) obj;
        this.orders.model = orderList;

        return ResponseEntity.ok(orders);
    };
}
