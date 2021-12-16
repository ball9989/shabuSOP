package com.example.shabushabu;

import com.example.shabushabu.pojo.ServeOrder;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.ArrayList;

public class PaymentConfirm extends FormLayout {
    VerticalLayout mainLayout = new VerticalLayout();
    FormLayout buttonLayout = new FormLayout();
    H5 tableNumber = new H5("โต๊ะ : ");
    H5 status = new H5("สถานะ : ");
    H5 totalPrice = new H5("ราคารวมทั้งหมด : ");
    Button paymentConfirm = new Button("ยืนยัน");

    Grid<ServeOrder> grid = new Grid<>(ServeOrder.class, false);

    public PaymentConfirm() {

        grid.addColumn(ServeOrder::getName).setHeader("ชื่ออาหาร");
        grid.addColumn(ServeOrder::getCount).setHeader("จำนวน");
        grid.addColumn(ServeOrder::getPrice).setHeader("ราคา");
        grid.addColumn(ServeOrder::getTotalPrice).setHeader("รวมเป็น");

        buttonLayout.add(paymentConfirm);
        mainLayout.add(tableNumber, status, totalPrice, grid,buttonLayout);
        this.add(mainLayout);
    }

    public void setPaymentConfirm(Integer tableNo, String status, Double totalPrice, ArrayList<ServeOrder> arrayList){
        this.tableNumber.setText("โต๊ะ : "+tableNo);
        this.status.setText("สถานะ : "+status);
        this.totalPrice.setText("ราคารวมทั้งหมด : "+totalPrice);
        grid.setItems(arrayList);
    }

    public void clearStage() {
        this.tableNumber.setText("โต๊ะ : ");
        this.status.setText("สถานะ : ");
        this.totalPrice.setText("ราคารวมทั้งหมด : ");
        this.grid.setItems(new ArrayList<ServeOrder>());

    }
}
