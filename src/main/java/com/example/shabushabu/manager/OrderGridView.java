package com.example.shabushabu.manager;

import com.example.shabushabu.pojo.Menu;
import com.example.shabushabu.pojo.Order;
import com.example.shabushabu.pojo.Orders;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

public class OrderGridView extends VerticalLayout {
    Orders orders = new Orders();


    public OrderGridView(){
        getOrders();
        H2 title = new H2("รายการออร์เดอร์");
        Grid<Order> grid = new Grid<>(Order.class,false);
        grid.setItems(orders.model);
        grid.addColumn(Order::get_id).setHeader("ID");
        grid.addColumn(Order::getTableNo).setHeader("Table");
        grid.addColumn(Order::getTotalPrice).setHeader("Total-price");
        grid.addColumn(Order::getStatus).setHeader("Status");
        add(title,grid);
        setWidth("100%");
        this.setVisible(false);
    }
    public void getOrders(){
        Orders out = WebClient.create().get()
                .uri("http://localhost:8080/orders")
                .retrieve()
                .bodyToMono(Orders.class)
                .block();
        System.out.println("out " +out);
        for (int i = 0; i < out.model.size(); i++) {
            if(out.model.get(i).getStatus().equals("success")){
                orders.model.add(out.model.get(i));
            }
        }
    }
}
