package com.example.shabushabu.repository;


import com.example.shabushabu.pojo.Order;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    @Autowired
    private OrderRepository repository;
    public MenuService(OrderRepository repository){
        this.repository = repository;
    }

    @RabbitListener(queues = "AddProductQueue")
    public List<Order> getMenu() {
        return repository.findAll();
    }

}
