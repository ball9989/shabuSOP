package com.example.shabushabu;

import com.example.shabushabu.pojo.Orders;
import com.example.shabushabu.pojo.ServeOrder;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;

@StyleSheet("https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css")
@Route(value = "serve", layout = EmployeeView.class)
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
    Button confirmBtn = new Button("ยืนยัน");
    String selectId = "";

    ServeConfirm serveConfirm = new ServeConfirm(firstList);



    public ServeView(){
        //reload
        addAttachListener(event -> {
            this.getOrders();
            for (int i = 0; i < orders.model.size(); i++) {
                if (orders.model.get(i).getStatus().equals("waiting")) {
                    Div tableCard = createTableCard(orders.model.get(i).get_id() ,orders.model.get(i).getTableNo(), orders.model.get(i).getStatus(), orders.model.get(i).getTotalPrice(), orders.model.get(i).getOrders());
                    tableCard.addClassName("border");
                    tableCard.setWidth("500px");
                    this.tableLayout.add(tableCard);
                }
            }
        });
        this.tableLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("1px", 3));
        this.tableLayout.setWidth("100%");

        //set scroll
        Scroller tableScroller = new Scroller(tableLayout);
        tableScroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
        tableScroller.setHeight("650px");
        tableScroller.setWidth("100%");

        H3 header = new H3("รายการเสิร์ฟ");
        header.setClassName("ml-1");
        header.setClassName("mt-1");
        this.leftLayout.add(header,tableScroller);

        Scroller orderList = new Scroller();
        orderList.setHeight("200px");
        orderList.addClassName("border");

        //left layout
        leftLayout.setWidth("70%");

        //right layout
        rightLayout.setWidth("30%");
        rightLayout.add(serveConfirm);
        rightLayout.addClassName("pt-5");

        mainLayout.add(leftLayout, rightLayout);
        mainLayout.setWidth("100%");

        //confirm
        this.serveConfirm.confirmBtn.addClickListener(event -> {
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("_id", selectId);
            Boolean out = WebClient.create().post().uri("http://localhost:8080/orders/confirm")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();



            if (out && this.serveConfirm.statusVal.equals("waiting")) {
                tableLayout.removeAll();
                this.getOrders();
                for (int i = 0; i < orders.model.size(); i++) {
                    if (orders.model.get(i).getStatus().equals("waiting")) {
                        Div tableCard = createTableCard(orders.model.get(i).get_id() ,orders.model.get(i).getTableNo(), orders.model.get(i).getStatus(), orders.model.get(i).getTotalPrice(), orders.model.get(i).getOrders());
                        tableCard.addClassName("border");
                        tableCard.setWidth("500px");
                        this.tableLayout.add(tableCard);
                    }
                }
                this.selectId = "";
                this.serveConfirm.clearStage();

                this.serveConfirm.statusVal = "";

                Notification a = new Notification("Serve Success", 5000);
                a.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                a.open();
            }


        });


        this.add(mainLayout);
    }

    public Div createTableCard(String _id, Integer tableNo, String status, Double totalPrice, ArrayList<ServeOrder> ordersServe) {
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
            this.selectId = _id;
//            System.out.println(_id);
            this.serveConfirm.setServeConfirm(_id,tableNo, status, totalPrice, ordersServe);
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
