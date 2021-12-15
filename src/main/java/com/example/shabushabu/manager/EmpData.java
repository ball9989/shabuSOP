package com.example.shabushabu.manager;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.Arrays;
import java.util.List;

public class EmpData extends VerticalLayout {
    List<Person> people = Arrays.asList(
            new Person("Nicolaus Copernicus", 1543),
            new Person("Galileo Galilei", 1564),
            new Person("Johannes Kepler", 1571));

    public EmpData(){
        H2 title = new H2("ข้อมูลพนักงาน");
        Grid<Person> grid = new Grid<>();
        grid.setItems(people);
        grid.addColumn(Person::getFirstName).setHeader("Name");
        grid.addColumn(Person::getId)
                .setHeader("Year of birth");
        add(title,grid);
        setWidth("100%");
    }
}
