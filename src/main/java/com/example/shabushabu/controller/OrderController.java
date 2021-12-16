package com.example.shabushabu.controller;

import com.example.shabushabu.OrderDetailView;
import com.example.shabushabu.pojo.Order;
import com.example.shabushabu.pojo.Orders;
import com.example.shabushabu.repository.OrderService;
import com.example.shabushabu.test.ServeOrder;
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
    private OrderService orderService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    protected Orders orders = new Orders();

    @RequestMapping(value = "/orders",method = RequestMethod.GET)
    public ResponseEntity<?> getOrders(){
        ArrayList<Order> o = (ArrayList<Order>) orderService.getOrder();
        orders.model = o;
        return ResponseEntity.ok(orders);
    }

    @RequestMapping(value = "/sendOrder", method = RequestMethod.POST)
    public boolean sendOrder(@RequestBody MultiValueMap<String, String> n) {
        Map<String, String> d = n.toSingleValueMap();
        System.out.println(d.get("0"));
        System.out.println(d);
        Integer size = Integer.parseInt(d.get("size"));

        ArrayList<ServeOrder> arrList = new ArrayList<>();
        for (int i=0;i<size;i++) {
            String id = d.get(i+"_id");
            String name = d.get(i+"_name");
            Double count = Double.parseDouble(d.get(i+"_count"));
            arrList.add(new ServeOrder(id, name, count));
        }
        System.out.println(arrList);
//        Double tableNumber = Double.parseDouble(d.get("tableNumber"));
//        Double n2 = Double.parseDouble(d.get("n2"));
        rabbitTemplate.convertAndSend("ShabuOrder", "addOrder", "");
        return true;
    }
}
