package com.example.shabushabu.manager;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.grid.Grid;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@StyleSheet("https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css")
@Route(value = "manager")
public class ManagerView extends HorizontalLayout {
    private VerticalLayout drawer,data;
    private Button employeeBn,supplies,orders,food;
    private List<Component> components;
    private EmpData employee = new EmpData();
    private MenuData menu = new MenuData();
    private Test test = new Test();

    public ManagerView(){
        components = new ArrayList<Component>();
        components.add(employee);
        components.add(test);
        components.add(menu);
        employeeBn = new Button("พนักงาน", new Icon(VaadinIcon.USER));
        orders = new Button("ออร์เดอร์",new Icon(VaadinIcon.CART));
        food = new Button("เมนู", new Icon(VaadinIcon.BOOK));
        supplies = new Button("วัตถุดิบ",new Icon(VaadinIcon.PACKAGE));
        drawer = new VerticalLayout();
        employeeBn.setWidth("100%");
        supplies.setWidth("100%");
        orders.setWidth("100%");
        drawer.add(employeeBn,orders,supplies);
        drawer.setWidth("20%");
        drawer.setHeight("960px");
        drawer.addClassName("border");
        data = new VerticalLayout();
        for (int i=0;i<=components.size()-1;i++){
            data.add(components.get(i));
        }
        add(drawer,data);
        employeeBn.addClickListener(e->{getInfo(employee);});
        orders.addClickListener(e->{getInfo(test);});
    }
    public void getInfo(Component c){
        for (int i=0;i<=components.size()-1;i++){
            components.get(i).setVisible(false);
        }
        c.setVisible(true);
    }
}
