package com.example.shabushabu.employee;

import com.example.shabushabu.pojo.ServeOrder;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.ArrayList;

public class ServeConfirm extends FormLayout {
    VerticalLayout mainLayout = new VerticalLayout();
    FormLayout buttonLayout = new FormLayout();
    H5 tableNumber = new H5("โต๊ะ : ");
    H5 status = new H5("สถานะ : ");
    H5 totalPrice = new H5("ราคารวมทั้งหมด : ");
    Button paymentConfirm = new Button("ยืนยัน");

    public ServeConfirm(ArrayList<ServeOrder> arrayList) {
        Grid<ServeOrder> grid = new Grid<>(ServeOrder.class, false);
        grid.addColumn(ServeOrder::getName).setHeader("ชื่ออาหาร");
        grid.addColumn(ServeOrder::getCount).setHeader("จำนวน");
        grid.setItems(arrayList);

        buttonLayout.add(paymentConfirm);
        mainLayout.add(tableNumber, status, totalPrice, grid, buttonLayout);
        this.add(mainLayout);
    }
}
