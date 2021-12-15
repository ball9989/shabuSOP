package com.example.shabushabu;

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

@Route(value = "order")
@StyleSheet("https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css")
public class OrderView extends Div {
    private HorizontalLayout horizontalLayout = new HorizontalLayout();
    private FormLayout formLayout = new FormLayout();
    public OrderView(){

        H1 title = new H1("Order Menu โต๊ะ 1");
        title.addClassName("h1");
//        this.addClassName("ml-5");
        this.add(title);

        for (int i = 0; i < 12; i++) {
            OrderCardView orderCardView = new OrderCardView();
            orderCardView.addClassName("mb-5");
            this.formLayout.add(orderCardView);
        }
        this.formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("400px",3));
        this.formLayout.getStyle().set("padding", "20px");
        this.horizontalLayout.add(formLayout);

        //ด้านขวา
        VerticalLayout rightLayout = new VerticalLayout();
        FormLayout headerOrder = new FormLayout();
        Label headerLabel = new Label("สั่งอาหาร");
//        headerLabel.addClassName("text-white");
        headerLabel.addClassName("h3");
//        headerLabel
        headerOrder.add(headerLabel);

//        rightLayout.addClassName("bg-dark");
        rightLayout.add(headerOrder);
        for (int i=0;i<10;i++) {
            rightLayout.add(new OrderDetailView());
        }



        //split แยกมาสองหน้าจอ
//        SplitLayout splitLayout = new SplitLayout(horizontalLayout, rightLayout);
//        splitLayout.setSplitterPosition(70);
//        splitLayout.setMaxHeight("850px");
//        splitLayout.addClassName("p-5");

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
//        newLayout.setFlexGrow(0.8, scrollerLeft);
//        newLayout.setFlexGrow(1, scrollerRight);
        newLayout.addClassName("pl-5");

        this.add(newLayout);
    }
}
