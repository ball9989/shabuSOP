package com.example.shabushabu.manager;

import com.example.shabushabu.pojo.Menu;
import com.example.shabushabu.pojo.Menus;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.web.reactive.function.client.WebClient;

public class MenuData extends VerticalLayout {
    private Menus menu;
    public MenuData(){
        getMenu();
        H2 title = new H2("เมนู");
        Grid<Menu> grid = new Grid<>(Menu.class, false);
        grid.addColumn(Menu::getName).setHeader("ชื่ออาหาร");
        grid.addColumn(Menu::getPrice).setHeader("ราคา (บาท)");
        grid.addColumn(Menu::getDetail).setHeader("รายละเอียด");
        grid.setItems(menu.model);
        add(title,grid);
        setWidth("100%");
        setVisible(false);
    }

    public void getMenu(){
        Menus out = WebClient.create().get()
                .uri("http://localhost:8080/menu")
                .retrieve()
                .bodyToMono(Menus.class)
                .block();
        menu = out;
        System.out.println("getMenu"+out.model);
    }
}
