package com.example.shabushabu;

import com.example.shabushabu.pojo.Orders;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import org.springframework.web.reactive.function.client.WebClient;

@Route(value = "order")
@StyleSheet("https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css")
public class OrderView extends Div {
    private HorizontalLayout horizontalLayout = new HorizontalLayout();
    private FormLayout formLayout = new FormLayout();
    private int numberArr = 0;
    private Orders orders;
    public OrderView(){

        H1 title = new H1("Order Menu โต๊ะ 1");
        title.addClassName("h1");
        this.add(title);

        addAttachListener(event -> {
            getOrders();
            for (int i = 0; i < orders.model.size(); i++) {
                OrderCardView orderCardView = new OrderCardView();
                orderCardView.setId(orders.model.get(i).get_id());
                orderCardView.title.setText(orders.model.get(i).getName());
                orderCardView.subtitle.setText(orders.model.get(i).getDetail());
                orderCardView.image.setSrc(orders.model.get(i).getImage());
                orderCardView.para.setText(orders.model.get(i).getPrice()+"");
                orderCardView.addClassName("mb-5");
                this.formLayout.add(orderCardView);
            }
        });



        this.formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("400px",3));
        this.formLayout.getStyle().set("padding", "20px");
        this.horizontalLayout.add(formLayout);

        //ด้านขวา
        VerticalLayout rightLayout = new VerticalLayout();
        FormLayout headerOrder = new FormLayout();
        Label headerLabel = new Label("สั่งอาหาร");
        headerLabel.addClassName("h3");
        headerOrder.add(headerLabel);
        rightLayout.add(headerOrder);




    //rightLayout.add(new OrderDetailView())

        HorizontalLayout newLayout = new HorizontalLayout();
        Scroller scrollerLeft = new Scroller(horizontalLayout);
        Scroller scrollerRight = new Scroller(rightLayout);
        scrollerLeft.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
        scrollerRight.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
        scrollerLeft.setHeight("650px");

        Button confirmOrder = new Button("ยืนยัน ราคารวมทั้งหมด 500 บาท");
        confirmOrder.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        confirmOrder.addClassName("d-flex");
        confirmOrder.addClassName("justify-content-center");
        confirmOrder.addClassName("ml-5");
        confirmOrder.setWidth("300px");
        VerticalLayout groupRight = new VerticalLayout(scrollerRight, confirmOrder);
        groupRight.setHeight("650px");
        groupRight.setWidth("100%");
        scrollerLeft.setWidth("180%");
        newLayout.add(scrollerLeft, groupRight);
        newLayout.addClassName("pl-5");

        this.add(newLayout);
    }
    public void getOrders(){
        Orders out = WebClient.create().get()
                .uri("http://localhost:8080/orders")
                .retrieve()
                .bodyToMono(Orders.class)
                .block();
        orders = out;
    }
}
