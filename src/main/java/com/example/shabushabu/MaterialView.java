package com.example.shabushabu;

import com.example.shabushabu.employee.MaterialDetail;
import com.example.shabushabu.employee.PaymentConfirm;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@StyleSheet("https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css")
@Route(value = "material")
public class MaterialView extends VerticalLayout {
    FormLayout tableLayout = new FormLayout();
    HorizontalLayout mainLayout = new HorizontalLayout();
    VerticalLayout leftLayout = new VerticalLayout();
    VerticalLayout rightLayout = new VerticalLayout();
    Button paymentConfirm = new Button("ยืนยัน");

    public MaterialView() {
        for (int i=0;i<20;i++) {
            Div materialCard = createMaterialCard();
            this.tableLayout.add(materialCard);
        }
        this.tableLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("1px", 3));

        //set scroll
        Scroller tableScroller = new Scroller(tableLayout);
        tableScroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
        tableScroller.setHeight("650px");
        this.leftLayout.add(tableScroller);

        //set grid ไว้ทำ รายการอาหารที่เลือกก่อนจ่ายเงิน ถ้ามีเวลา
//        Grid<Table> tableGrid = new Grid<>(Table.class, false);


        Scroller orderList = new Scroller();
        orderList.setHeight("200px");
        orderList.addClassName("border");

        //right layout
        leftLayout.setWidth("200%");

        //right layout
        FormLayout buttonLayout = new FormLayout(this.paymentConfirm);
        rightLayout.add(new MaterialDetail());
        rightLayout.addClassName("pt-5");

        mainLayout.add(leftLayout, rightLayout);
        this.add(mainLayout);
    }

    public Div createMaterialCard() {
        Image image = new Image("https://dummyimage.com/600x400/000/fff", "DummyImage");
        Div mainLayout = new Div();
        H5 materialName = new H5("ชื่อวัตถุดิบ ");
        Paragraph materialDetail = new Paragraph("รายละเอียด : ");
        Paragraph materialNumber = new Paragraph("จำนวน : ");
        Div body = new Div();
        Button selectTable = new Button("เลือก");
        FormLayout buttonLayout = new FormLayout();

        mainLayout.addClassName("card");
        image.addClassName("card-img-top");
        body.addClassName("card-body");
        materialName.addClassName("card-title");
        materialNumber.addClassName("card-subtitle");

        buttonLayout.add(selectTable);
        body.add(materialName,materialDetail,materialNumber , buttonLayout);
        mainLayout.add(image,body);
        mainLayout.addClassName("mb-3");

        return mainLayout;
    }
}
