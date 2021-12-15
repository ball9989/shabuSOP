package com.example.shabushabu.employee;

import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;

@StyleSheet("https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css")
public class TableCardView extends Div {
    H5 tableNumber = new H5("โต๊ะ : ");
    Paragraph price = new Paragraph("ราคา : ");
    Paragraph status = new Paragraph("สถานะ : ");
    Div body = new Div();
    public TableCardView() {
        this.addClassName("card");
        this.body.addClassName("card-body");
        this.tableNumber.addClassName("card-header");
        this.price.addClassName("card-subtitle");
        this.status.addClassName("card-subtitle");

        this.body.add(price, status);
        this.add(tableNumber,body);
    }
}
