package com.example.shabushabu.controller;

import com.example.shabushabu.pojo.Menu;
import com.example.shabushabu.pojo.Menus;
import com.example.shabushabu.pojo.Order;
import com.example.shabushabu.repository.MenuService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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
public class MenuController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    protected Menus menu = new Menus();
    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "/menu",method = RequestMethod.GET)
    public ResponseEntity<?> getMenu(){
        Object obj = rabbitTemplate.convertSendAndReceive("ShabuMenu","getMenu","get Menu Data");
        ArrayList<Menu> menulist = (ArrayList<Menu>) obj;
        menu.model = menulist;

        return ResponseEntity.ok(menu);
    };

    @RequestMapping(value = "/addMenu", method = RequestMethod.POST)
    public boolean addMenu(@RequestBody MultiValueMap<String, String> menu) {
        Map<String, String> d = menu.toSingleValueMap();
        String name = d.get("name");
        Integer mats_left = Integer.parseInt(d.get("mats_left"));
        Integer mats_cost = Integer.parseInt(d.get("mats_cost"));
        String detail = d.get("detail");
        String image = d.get("image");
        Integer price = Integer.parseInt(d.get("price"));
        System.out.println(name+mats_left+mats_cost+image+price);
        rabbitTemplate.convertAndSend("ShabuMenu", "addMenu", new Menu(null,name,image,detail,price,mats_left,mats_cost));
        return true;
    }

    @RequestMapping(value = "/editMenu", method = RequestMethod.POST)
    public boolean editMenu(@RequestBody MultiValueMap<String, String> menu) {
        Map<String, String> d = menu.toSingleValueMap();
        String _id = d.get("_id");
        String name = d.get("name");
        Integer mats_left = Integer.parseInt(d.get("mats_left"));
        Integer mats_cost = Integer.parseInt(d.get("mats_cost"));
        String detail = d.get("detail");
        String image = d.get("image");
        Integer price = Integer.parseInt(d.get("price"));
        System.out.println("Edit controller :"+name+mats_left+mats_cost+image+price);
        rabbitTemplate.convertAndSend("ShabuMenu", "editMenu", new Menu(_id,name,image,detail,price,mats_left,mats_cost));
        return true;
    }

    @RequestMapping(value = "/deleteMenu", method = RequestMethod.POST)
    public boolean deleteMenu(@RequestBody MultiValueMap<String, String> menu){
        Map<String, String> d = menu.toSingleValueMap();
        String _id = d.get("_id");
        rabbitTemplate.convertAndSend("ShabuMenu","deleteMenu", _id);
        return true;
    }

    @RequestMapping(value = "/updateMat", method = RequestMethod.POST)
    public boolean updateMat(@RequestBody MultiValueMap<String, String> keyMat) {
        Map<String, String> d = keyMat.toSingleValueMap();
        Integer size = Integer.parseInt(d.get("size"));
        for (int i=0;i<size;i++) {
            menuService.updateMat(d.get(i+"_id"), Integer.parseInt(d.get(i+"_count")));
        }
        return true;
    }
}
