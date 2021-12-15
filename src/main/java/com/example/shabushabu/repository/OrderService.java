package com.example.shabushabu.repository;

import com.example.shabushabu.pojo.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;
    public OrderService(OrderRepository repository){this.repository = repository;}

    public List<Menu> getOrder(){return repository.findAll();}
}
