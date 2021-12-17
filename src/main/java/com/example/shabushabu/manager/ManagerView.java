package com.example.shabushabu.manager;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@StyleSheet("https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css")
@Route(value = "manager")
public class ManagerView extends HorizontalLayout {
    private VerticalLayout drawer,data;
    private Button employeeBn,supplies,orders,food;
    private List<Component> components;
    private MenuGridView menu = new MenuGridView();
    private OrderGridView orderGridView = new OrderGridView();
    private AccountGridView accountGridView = new AccountGridView();

    public ManagerView(){
        components = new ArrayList<Component>();
        components.add(accountGridView);
        components.add(menu);
        components.add(orderGridView);
        employeeBn = new Button("ผู้ใช้งาน", new Icon(VaadinIcon.USER));
        orders = new Button("ออร์เดอร์",new Icon(VaadinIcon.CART));
        food = new Button("เมนูและคลัง", new Icon(VaadinIcon.BOOK));
        drawer = new VerticalLayout();
        employeeBn.setWidth("100%");
        orders.setWidth("100%");
        food.setWidth("100%");
        drawer.add(employeeBn,orders,food);
        drawer.setWidth("20%");
        drawer.setHeight("960px");
        drawer.addClassName("border");
        data = new VerticalLayout();
        for (int i=0;i<components.size();i++){
            data.add(components.get(i));
        }
        add(drawer,data);
        employeeBn.addClickListener(e->{getInfo(accountGridView);});
        orders.addClickListener(e->{getInfo(orderGridView);});
        food.addClickListener(e->{
            getInfo(menu);
        });
    }
    public void getInfo(Component c){
        for (int i=0;i<components.size();i++){
            components.get(i).setVisible(false);
        }
        c.setVisible(true);
    }
}
