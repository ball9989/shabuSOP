package com.example.shabushabu.manager;

import com.example.shabushabu.OrderCardView;
import com.example.shabushabu.pojo.Order;
import com.example.shabushabu.pojo.Orders;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

public class MenuData extends VerticalLayout {
    private Orders orders;
    public MenuData(){
        getOrders();
        H2 title = new H2("เมนู");
        Grid<Order> grid = new Grid<>(Order.class, false);
        grid.addColumn(Order::getName).setHeader("ชื่ออาหาร");
        grid.addColumn(Order::getPrice).setHeader("ราคา (บาท)");
        grid.addColumn(Order::getDetail).setHeader("รายละเอียด");
        grid.setItems(orders.model);
        add(title,grid);
        setWidth("100%");
        setVisible(false);
    }
    public void getOrders(){
        Orders out = WebClient.create().get()
                .uri("http://localhost:8080/orders")
                .retrieve()
                .bodyToMono(Orders.class)
                .block();
        orders = out;
        System.out.println(out);
    }
}
