package com.example.shabushabu;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "order")
public class OrderView extends FormLayout {
    private OrderCardView orderCardView = new OrderCardView();
    public OrderView(){
        this.add(orderCardView);

    }
}
