package com.example.shabushabu.employee;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

@Route(value = "payment")
public class PaymentView extends Div {
    public PaymentView() {
        this.add(new TableCardView());
    }
}
