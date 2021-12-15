package com.example.shabushabu.managerView;

import com.example.shabushabu.Person;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.Arrays;
import java.util.List;


public class Customer1 extends VerticalLayout {
    List<Person> people = Arrays.asList(
            new Person("Nicolaus Copernicus", 1543));
    public Customer1(){
        H2 title = new H2("ข้อมูล 22222");
        Grid<Person> grid = new Grid<>();
        grid.setItems(people);
        grid.addColumn(Person::getFirstName).setHeader("Name");
        grid.addColumn(Person::getId)
                .setHeader("Year of birth");
        grid.setWidth("100%");
        add(title,grid);
        setVisible(false);
    }
}
