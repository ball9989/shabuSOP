package com.example.shabushabu.manager;

import com.example.shabushabu.pojo.Orders;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.Arrays;
import java.util.List;

public class MenuData extends VerticalLayout {
    private Orders orders;
    public MenuData(){
        H2 title = new H2("เมนูที่มีในร้าน");
//        Grid<Orders> grid = new Grid<>();
//        grid.addColumn(orders -> orders.model.get(0).getName()).setHeader("ชื่อเมนู");
        add(title);
        setWidth("100%");
        setVisible(false);
    }
}
