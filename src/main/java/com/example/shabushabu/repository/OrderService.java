package com.example.shabushabu.repository;

import com.example.shabushabu.pojo.Order;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderService {


    @RabbitListener(queues = "AddOrderQueue")
    public void addBadSentence(Order order) {
        System.out.println("consumer"+"table number "+ order.getTableNo() + " " + order.getOrders());
    }
}
