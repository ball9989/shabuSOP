package com.example.shabushabu.employee;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class PaymentConfirm extends FormLayout {
    VerticalLayout mainLayout = new VerticalLayout();
    FormLayout buttonLayout = new FormLayout();
    H5 tableNumber = new H5("โต๊ะ : ");
    H5 status = new H5("สถานะ : ");
    H5 totalPrice = new H5("ราคารวมทั้งหมด : ");
    Button paymentConfirm = new Button("ยืนยัน");

    public PaymentConfirm() {
        buttonLayout.add(paymentConfirm);
        mainLayout.add(tableNumber, status, totalPrice, buttonLayout);
        this.add(mainLayout);
    }
}
