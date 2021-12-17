package com.example.shabushabu.repository;


import com.example.shabushabu.pojo.Menu;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {
    @Autowired
    private MenuRespository repository;

    public MenuService(MenuRespository repository){

        this.repository = repository;
    }

    public void updateMat(String id, Integer count) {
        List<Menu> mats = repository.findAll();
        for (int i=0;i<mats.size();i++) {
            if (mats.get(i).get_id().equals(id)) {
                Integer matLeft = mats.get(i).getMats_left();
                mats.get(i).setMats_left(matLeft - count);
                this.repository.save(mats.get(i));
            }
        }
    }

    @RabbitListener(queues = "GetMenuQueue")
    public List<Menu> getMenu() {
        return repository.findAll();
    }

    @RabbitListener(queues = "AddMenuQueue")
    public void addMenu(Menu menu){
        System.out.println("addNewMenu : "+menu.getName());
        this.repository.save(menu);
    }

    @RabbitListener(queues = "EditMenuQueue")
    public void editMenu(Menu menu){
        System.out.println("editMenu : "+menu.getName());
        this.repository.save(menu);
    }

    @RabbitListener(queues = "DeleteMenuQueue")
    public void deleteMenu(String _id){
        System.out.println("Delete :"+_id);
        this.repository.deleteById(_id);
    }

}
