package com.example.shabushabu;

import com.example.shabushabu.employee.MaterialDetail;
import com.vaadin.flow.component.Component;
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
    Button addMaterial = new Button("เพิ่ม");

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

        Scroller orderList = new Scroller();
        orderList.setHeight("200px");
        orderList.addClassName("border");

        //right layout
        leftLayout.setWidth("200%");

        //right layout
        FormLayout buttonLayout = new FormLayout(this.paymentConfirm);
        Component material = new MaterialDetail();
        rightLayout.add(material);

        rightLayout.addClassName("pt-5");
        mainLayout.add(leftLayout, rightLayout);
        addMaterial.addClassName("d-flex");
        this.add(addMaterial, mainLayout);

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
