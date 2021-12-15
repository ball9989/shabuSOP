package com.example.shabushabu;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.apache.http.util.TextUtils;
import org.springframework.web.reactive.function.client.WebClient;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

import java.util.Arrays;
import java.util.List;

import java.util.ArrayList;

import java.awt.*;

@StyleSheet("https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css")
@Route(value = "manager")
public class ManagerView extends HorizontalLayout {
    private VerticalLayout drawer,data;
    private Button user,supplies,orders;

    List<Person> people = Arrays.asList(
            new Person("Nicolaus Copernicus", 1543),
            new Person("Galileo Galilei", 1564),
            new Person("Johannes Kepler", 1571));

    public ManagerView(){
        user = new Button("User", new Icon(VaadinIcon.USER));
        orders = new Button("Orders",new Icon(VaadinIcon.CART));
        supplies = new Button("Supplies",new Icon(VaadinIcon.PACKAGE));
        drawer = new VerticalLayout();
        user.setWidth("100%");
        supplies.setWidth("100%");
        orders.setWidth("100%");
        drawer.add(user,orders,supplies);
        drawer.setWidth("20%");
        drawer.setHeight("960px");
        drawer.addClassName("border");
        data = new VerticalLayout();

        Grid<Person> grid = new Grid<>();
        grid.setItems(people);
        grid.addColumn(Person::getFirstName).setHeader("Name");
        grid.addColumn(Person::getId)
                .setHeader("Year of birth");

        data.add(grid);
        add(drawer,grid);
    }

}
