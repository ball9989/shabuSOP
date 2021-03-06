package com.example.shabushabu;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;

public class OrderDetailView extends FormLayout {
    Image image = new Image("https://dummyimage.com/600x400/000/fff", "DummyImage");
    Div body = new Div();
    H5 title = new H5("ชื่ออาหาร:wedrsdcsdcsdfcsd");
    H6 subtitle = new H6("รายละเอียด:");
    Paragraph para = new Paragraph("ราคา:");
    Integer count = 1;
    Button delMenu = new Button("ลบ");
    String price;
    Button plus = new Button(new Icon(VaadinIcon.PLUS_CIRCLE));
    Button minus = new Button(new Icon(VaadinIcon.MINUS_CIRCLE));
    Label number = new Label(count+"");
    Integer mats_left;

    String name;

    public OrderDetailView(){}

    public OrderDetailView(String id,String name, String detail, String price,Integer mats_left) {
        this.setId(id);
        this.mats_left = mats_left;
        this.name = name;
        this.title.setText("ชื่ออาหาร: "+name);
        this.subtitle.setText("รายละเอียด: "+detail);
        this.para.setText("ราคา: "+price);
        this.price = price;

        VerticalLayout layout = new VerticalLayout();
        number.addClassName("h5");
        HorizontalLayout horiLayout = new HorizontalLayout();
        horiLayout.addClassName("d-flex");
        horiLayout.addClassName("align-items-center");
        horiLayout.add(minus, number,plus);
        delMenu.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        layout.add(title, subtitle, para, horiLayout);


        this.add(layout,delMenu);
        this.addClassName("mb-3");
        this.setResponsiveSteps(new ResponsiveStep("1px", 1));
    }
}
