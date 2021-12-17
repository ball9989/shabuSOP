package com.example.shabushabu;

import com.example.shabushabu.employee.PaymentConfirm;
import com.example.shabushabu.employee.ServeConfirm;
import com.example.shabushabu.pojo.Menus;
import com.example.shabushabu.pojo.Orders;
import com.example.shabushabu.pojo.ServeOrder;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;

@StyleSheet("https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css")
@Route(value = "serve")
public class ServeView extends VerticalLayout {

    ArrayList<ServeOrder> firstList = new ArrayList<>();

    private Orders orders;

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

    ServeConfirm serveConfirm = new ServeConfirm(firstList);

    public ServeView(){


        //reload
        addAttachListener(event -> {
            this.getOrders();
            for (int i = 0; i < orders.model.size(); i++) {
                System.out.println(orders.model.get(i).getOrders());
                Div tableCard = createTableCard(orders.model.get(i).getTableNo(), orders.model.get(i).getStatus(), orders.model.get(i).getTotalPrice(), orders.model.get(i).getOrders());
                tableCard.addClassName("border");
                tableCard.setWidth("500px");
                this.tableLayout.add(tableCard);
            }
        });
        this.tableLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("1px", 3));
        this.tableLayout.setWidth("100%");

        //set scroll
        Scroller tableScroller = new Scroller(tableLayout);
        tableScroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
        tableScroller.setHeight("650px");
        tableScroller.setWidth("100%");
        this.leftLayout.add(tableScroller);

        Scroller orderList = new Scroller();
        orderList.setHeight("200px");
        orderList.addClassName("border");

        //left layout
        leftLayout.setWidth("70%");

        //right layout
        rightLayout.setWidth("30%");
        FormLayout buttonLayout = new FormLayout(this.paymentConfirm);
        rightLayout.add(serveConfirm);
        rightLayout.addClassName("pt-5");

        mainLayout.add(leftLayout, rightLayout);
        mainLayout.setWidth("100%");
        this.add(mainLayout);




    }

    public Div createTableCard(Integer tableNo, String status, Double totalPrice, ArrayList<ServeOrder> ordersServe) {
        Div mainLayout = new Div();
        H5 tableNumber = new H5("โต๊ะ : "+tableNo);
        Paragraph statusP = new Paragraph("สถานะ : "+status);
        Div body = new Div();
        Button selectTable = new Button("เลือก");
        FormLayout buttonLayout = new FormLayout();

        mainLayout.addClassName("card");
        body.addClassName("card-body");
        tableNumber.addClassName("card-header");

        statusP.addClassName("card-subtitle");

        buttonLayout.add(selectTable);
        body.add(statusP, buttonLayout);
        mainLayout.add(tableNumber,body);
        mainLayout.addClassName("mb-3");

        selectTable.addClickListener(event -> {
            this.serveConfirm.setServeConfirm(tableNo, status, totalPrice, ordersServe);
        });

        return mainLayout;
    }

    public void getOrders(){
        Orders out = WebClient.create().get()
                .uri("http://localhost:8080/orders")
                .retrieve()
                .bodyToMono(Orders.class)
                .block();
        System.out.println("out " +out);
        orders = out;
    }
}
