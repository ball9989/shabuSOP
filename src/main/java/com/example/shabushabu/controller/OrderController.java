package com.example.shabushabu.controller;

import com.example.shabushabu.pojo.Order;
import com.example.shabushabu.pojo.ServeOrder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/sendOrder", method = RequestMethod.POST)
    public boolean sendOrder(@RequestBody MultiValueMap<String, String> n) {
        Map<String, String> d = n.toSingleValueMap();
        Integer size = Integer.parseInt(d.get("size"));
        String tableNo = d.get("tableNo");

        ArrayList<ServeOrder> arrList = new ArrayList<>();
        for (int i=0;i<size;i++) {
            String id = d.get(i+"_id");
            String name = d.get(i+"_name");
            Double count = Double.parseDouble(d.get(i+"_count"));
            arrList.add(new ServeOrder(id, name, count));
        }
        System.out.println(arrList);
        rabbitTemplate.convertAndSend("ShabuOrder", "addOrder", new Order("", tableNo, arrList));
        return true;
    }
}
