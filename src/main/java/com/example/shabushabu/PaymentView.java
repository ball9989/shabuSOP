package com.example.shabushabu;

import com.example.shabushabu.pojo.Menus;
import com.example.shabushabu.pojo.ServeOrder;
import com.example.shabushabu.pojo.Tables;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.apache.commons.compress.archivers.ar.ArArchiveEntry;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;

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

    Integer tableNoSelect = 0;


    private Tables tables;

    PaymentConfirm paymentRight = new PaymentConfirm();

    public PaymentView() {
        getTables();
        for (int i=0;i<tables.model.size();i++) {
            Div tableCard = createTableCard(tables.model.get(i).getTableNo(), tables.model.get(i).getTotalPrice(), tables.model.get(i).getStatus());
            tableCard.addClassName("border");
            this.tableLayout.add(tableCard);
        }

        this.tableLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("1px", 3));

        //set scroll
        this.tableLayout.setWidth("100%");
        Scroller tableScroller = new Scroller(tableLayout);
        tableScroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
        tableScroller.setHeight("650px");
        tableScroller.setWidth("100%");
        this.leftLayout.add(tableScroller);

        //set grid ไว้ทำ รายการอาหารที่เลือกก่อนจ่ายเงิน ถ้ามีเวลา
//        Grid<Table> tableGrid = new Grid<>(Table.class, false);


        Scroller orderList = new Scroller();
        orderList.setHeight("200px");
        orderList.addClassName("border");

        //right layout
        leftLayout.setWidth("60%");

        //right layout
        this.paymentRight.setWidth("100%");
        rightLayout.setWidth("40%");
        rightLayout.add(paymentRight);
        rightLayout.addClassName("pt-5");

        this.paymentRight.paymentConfirm.addClickListener(buttonClickEvent -> {
            System.out.println("click sdfsfs " + tableNoSelect);
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("tableNo", this.tableNoSelect+"");
            Boolean out = WebClient.create().post()
                    .uri("http://localhost:8080/payment/confirm")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

            this.paymentRight.clearStage();
        });

        mainLayout.add(leftLayout, rightLayout);
        mainLayout.setWidth("100%");
        this.add(mainLayout);
    }

    public Div createTableCard(Integer tableNumberInput, Double totalPriceInput, String statusInput) {
        Div mainLayout = new Div();
        H5 tableNumber = new H5("โต๊ะ : "+tableNumberInput);
        Paragraph price = new Paragraph("ราคา : "+totalPriceInput);
        Paragraph status = new Paragraph("สถานะ : "+statusInput);
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

        selectTable.addClickListener(event -> {
            this.tableNoSelect = tableNumberInput;
            ArrayList<ServeOrder> serveOrders = new ArrayList<>();
            for (int i=0;i<tables.model.size();i++) {
                System.out.println();
                if (tables.model.get(i).getTableNo().equals(tableNumberInput)) {
                    for (int j=0;j<tables.model.get(i).getOrders().size();j++) {
                        for (int k=0;k<tables.model.get(i).getOrders().get(j).getOrders().size();k++) {
//                    System.out.println(tables.model.get(i).getOrders().get(j).getOrders().get(k).getName());
                            String orderId = tables.model.get(i).getOrders().get(j).getOrders().get(k).get_id();
                            String orderName = tables.model.get(i).getOrders().get(j).getOrders().get(k).getName();
                            Integer orderCount =tables.model.get(i).getOrders().get(j).getOrders().get(k).getCount();
                            Double orderPrice =tables.model.get(i).getOrders().get(j).getOrders().get(k).getPrice();
                            serveOrders.add(new ServeOrder(orderId, orderName, orderCount, orderPrice));
                        }
                    }
                }
            }

            this.paymentRight.setPaymentConfirm(tableNumberInput, statusInput, totalPriceInput, serveOrders);

        });

        return mainLayout;
    }

    public void getTables() {
        Tables out = WebClient.create().get()
                .uri("http://localhost:8080/tables")
                .retrieve()
                .bodyToMono(Tables.class)
                .block();
        tables = out;

    }

}
