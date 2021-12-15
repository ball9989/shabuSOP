package com.example.shabushabu;

import com.example.shabushabu.employee.PaymentConfirm;
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
@Route(value = "paymentView", layout = EmployeeView.class)
public class PaymentView extends VerticalLayout {
    FormLayout tableLayout = new FormLayout();
    HorizontalLayout mainLayout = new HorizontalLayout();
    VerticalLayout leftLayout = new VerticalLayout();
    VerticalLayout rightLayout = new VerticalLayout();
    DrawerToggle toggle = new DrawerToggle();
    H1 title = new H1("MyApp");
    H5 tableNumber = new H5("โต๊ะ : ");
    H5 status = new H5("สถานะ : ");
    H5 totalPrice = new H5("ราคารวมทั้งหมด : ");
    Button paymentConfirm = new Button("ยืนยัน");

    public PaymentView() {
        for (int i=0;i<20;i++) {
            Div tableCard = createTableCard();
            tableCard.addClassName("border");
            this.tableLayout.add(tableCard);
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
        rightLayout.add(new PaymentConfirm());
        rightLayout.addClassName("pt-5");

        mainLayout.add(leftLayout, rightLayout);
        this.add(mainLayout);
    }

    public Div createTableCard() {
        Div mainLayout = new Div();
        H5 tableNumber = new H5("โต๊ะ : ");
        Paragraph price = new Paragraph("ราคา : ");
        Paragraph status = new Paragraph("สถานะ : ");
        Div body = new Div();
        Button selectTable = new Button("เลือก");
        FormLayout buttonLayout = new FormLayout();

        mainLayout.addClassName("card");
        body.addClassName("card-body");
        tableNumber.addClassName("card-header");
        price.addClassName("card-subtitle");
        status.addClassName("card-subtitle");

        buttonLayout.add(selectTable);
        body.add(price, status, buttonLayout);
        mainLayout.add(tableNumber,body);
        mainLayout.addClassName("mb-3");

        return mainLayout;
    }

}
