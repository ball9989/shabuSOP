package com.example.shabushabu;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "order")
public class OrderView extends Div {
    private HorizontalLayout horizontalLayout = new HorizontalLayout();
    private FormLayout formLayout = new FormLayout();
    public OrderView(){
        H1 title = new H1("Order Menu");
        title.addClassName("h1");
        this.addClassName("container");
        this.add(title);

        for (int i = 0; i < 6; i++) {
            OrderCardView orderCardView = new OrderCardView();
            orderCardView.addClassName("mb-5");
            this.formLayout.add(orderCardView);
        }
        this.formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("400px",3));
        this.formLayout.setWidth("50%");
        this.horizontalLayout.add(formLayout);
        this.add(horizontalLayout);

    }
}
