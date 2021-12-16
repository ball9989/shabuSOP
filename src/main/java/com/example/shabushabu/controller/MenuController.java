package com.example.shabushabu.controller;

import com.example.shabushabu.pojo.Menu;
import com.example.shabushabu.pojo.Menus;
import com.example.shabushabu.pojo.Order;
import com.example.shabushabu.pojo.Orders;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class MenuController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    protected Menus menu = new Menus();

    @RequestMapping(value = "/menu",method = RequestMethod.GET)
    public ResponseEntity<?> getMenu(){
        Object obj = rabbitTemplate.convertSendAndReceive("Direct","test","get Menu Data");
        ArrayList<Menu> menulist = (ArrayList<Menu>) obj;
        menu.model = menulist;

        return ResponseEntity.ok(menu);
    };
}
