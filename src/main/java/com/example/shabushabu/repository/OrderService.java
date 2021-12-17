package com.example.shabushabu.repository;

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

    public void confirmOrder(String _id) {
        List<Order> orders = respository.findAll();
        for (int i=0;i<orders.size();i++) {
            if (orders.get(i).get_id().equals(_id)) {
                orders.get(i).setStatus("pending");
                System.out.println(orders.get(i).getStatus());
                respository.save(orders.get(i));
            }
        }
    }

    @RabbitListener(queues = "AddOrderQueue")
    public void addOrderQueue(Order order) {
        System.out.println("consumer"+"table number "+ order.getTableNo() + " " + order.getOrders());
        this.respository.save(order);
    }
    @RabbitListener(queues = "GetOrderQueue")
    public List<Order> getOrder() {
        return respository.findAll();
    }
}
