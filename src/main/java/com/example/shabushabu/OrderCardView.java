//package com.example.shabushabu;
//
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.button.ButtonVariant;
//import com.vaadin.flow.component.dependency.StyleSheet;
//import com.vaadin.flow.component.formlayout.FormLayout;
//import com.vaadin.flow.component.html.*;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//
//
//@StyleSheet("https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css")
//public class OrderCardView extends Div {
//    Image image = new Image("https://dummyimage.com/600x400/000/fff", "DummyImage");
//    Div body = new Div();
//    H5 title = new H5("ชื่ออาหาร:");
//    H6 subtitle = new H6("รายละเอียด:");
//    Paragraph para = new Paragraph("ราคา:");
//    Button addOrder = new Button("เลือก");
//
//
//    public OrderCardView(){
//        this.addClassName("card");
//        this.image.addClassName("card-img-top");
//        this.image.setHeight("200px");
//        this.body.addClassName("card-body");
//        this.title.addClassName("card-title");
//        this.subtitle.addClassName("card-subtitle");
//        this.para.addClassName("card-text");
//
//        body.add(title,subtitle,para);
//        addOrder.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        this.add(image);
//        this.add(body);
//        this.add(addOrder);
//    }
//}
