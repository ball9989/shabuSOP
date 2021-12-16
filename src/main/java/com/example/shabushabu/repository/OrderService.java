package com.example.shabushabu.repository;

import com.example.shabushabu.pojo.Menu;
import com.example.shabushabu.pojo.Order;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRespository respository;
    public OrderService(OrderRespository repository){
        this.respository = repository;
    }


    @RabbitListener(queues = "AddOrderQueue")
    public void addOrderQueue(Order order) {
        System.out.println("consumer"+"table number "+ order.getTableNo() + " " + order.getOrders());
        this.respository.save(order);
    }
    @RabbitListener(queues = "getOrderQueue")
    public List<Order> getOrder() {
        return respository.findAll();
    }
}
