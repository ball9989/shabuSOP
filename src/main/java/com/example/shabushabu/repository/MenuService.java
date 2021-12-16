package com.example.shabushabu.repository;


import com.example.shabushabu.pojo.Menu;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    @Autowired
    private MenuRespository repository;
    public MenuService(MenuRespository repository){
        this.repository = repository;
    }

    @RabbitListener(queues = "AddProductQueue")
    public List<Menu> getMenu() {
        return repository.findAll();
    }

}
