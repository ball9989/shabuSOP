package com.example.shabushabu;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class OrderCardView extends VerticalLayout {
    Image image = new Image("https://dummyimage.com/600x400/000/fff", "DummyImage");
    public OrderCardView(){
        this.add(image);
        this.add(new H1("test"));


    }
}
