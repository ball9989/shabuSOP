package com.example.shabushabu.managerView;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.*;

@StyleSheet("https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css")
@Route(value = "manager")
public class ManagerView extends HorizontalLayout {
    private VerticalLayout drawer,data;
    private Button userBn,supplies,orders;
    private EmpComponent user = new EmpComponent();
    private Customer1 customer1 = new Customer1();
    private List<Component> componets;

    public ManagerView(){
        componets = new ArrayList<>();
        componets.add(user);
        componets.add(customer1);
        userBn = new Button("พนักงาน", new Icon(VaadinIcon.USER));
        orders = new Button("การสั่งอาหาร",new Icon(VaadinIcon.CART));
        supplies = new Button("วัตถุดิบ",new Icon(VaadinIcon.PACKAGE));
        drawer = new VerticalLayout();
        userBn.setWidth("100%");
        supplies.setWidth("100%");
        orders.setWidth("100%");
        drawer.add(userBn,orders,supplies);
        drawer.setWidth("300px");
        drawer.setHeight("960px");
        drawer.addClassName("border");
        data = new VerticalLayout();
        data.add(user);
        for(int i=0;i<=componets.size()-1;i++){
            data.add(componets.get(i));
        };
        add(drawer,data);
        userBn.addClickListener(e->{getData(user);});
        orders.addClickListener(e->{getData(customer1);});
    }
    public void getData(Component show){
        for(int i=0;i<=componets.size()-1;i++){
            componets.get(i).setVisible(false);
        }
        show.setVisible(true);
    }
}
