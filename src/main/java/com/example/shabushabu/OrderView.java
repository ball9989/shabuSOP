package com.example.shabushabu;

import com.example.shabushabu.pojo.Menus;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;

@Route(value = "order")
@StyleSheet("https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css")
public class OrderView extends Div implements HasUrlParameter<String>  {
    private HorizontalLayout horizontalLayout = new HorizontalLayout();
    private FormLayout formLayout = new FormLayout();
    private int numberArr = 0;
    private int total = 0;
    private Menus menus;
    private String table;
    private ArrayList<OrderDetailView> cart = new ArrayList<>();
    VerticalLayout rightLayout = new VerticalLayout();
    Button confirmOrder = new Button("ยืนยัน ราคารวมทั้งหมด 0 บาท");
    H1 title = new H1("Order Menu Table 1");
    FormLayout headerOrder = new FormLayout();
    Label headerLabel = new Label("สั่งอาหาร");

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        this.table = parameter;
        this.title.setText("Order Menu Table: "+table);
    }


    public OrderView(){
        title.addClassName("h1");
        this.add(title);

        addAttachListener(event -> {
            getMenus();
            for (int i = 0; i < menus.model.size(); i++) {
                this.formLayout.add(getOrderCardView(menus.model.get(i).get_id(), menus.model.get(i).getName(), menus.model.get(i).getDetail(),
                        menus.model.get(i).getImage(), menus.model.get(i).getPrice()+"", menus.model.get(i).getMats_left()));
            }
        });



        this.formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("400px",3));
        this.formLayout.getStyle().set("padding", "20px");
        this.horizontalLayout.add(formLayout);

        //ด้านขวา
        headerLabel.addClassName("h3");
        headerOrder.add(headerLabel);
        rightLayout.add(headerOrder);





        HorizontalLayout newLayout = new HorizontalLayout();
        Scroller scrollerLeft = new Scroller(horizontalLayout);
        Scroller scrollerRight = new Scroller(rightLayout);
        scrollerLeft.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
        scrollerRight.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
        scrollerLeft.setHeight("650px");


        confirmOrder.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        confirmOrder.addClassName("d-flex");
        confirmOrder.addClassName("justify-content-center");
        confirmOrder.setWidth("100%");
        VerticalLayout groupRight = new VerticalLayout(scrollerRight, confirmOrder);
        groupRight.setHeight("650px");
        groupRight.setWidth("100%");
        scrollerLeft.setWidth("180%");
        newLayout.add(scrollerLeft, groupRight);
        newLayout.addClassName("pl-5");

        this.add(newLayout);

        confirmOrder.addClickListener(event -> {
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("size", cart.size()+"");
            formData.add("tableNo", table);
            formData.add("totalPrice", total+"");
            formData.add("status", "waiting");
            for (int i=0;i< cart.size();i++) {
//                MultiValueMap<String, String> formDataSub = new LinkedMultiValueMap<>();
                formData.add(i+"_id", cart.get(i).getId()+"");
                formData.add(i+"_name", cart.get(i).name);
                formData.add(i+"_count", cart.get(i).count+"");
                formData.add(i+"_price", cart.get(i).price+"");
//                formData.add(i+"", formDataSub+"");
            }
            Boolean out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/sendOrder")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
            Boolean mat = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/updateMat")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
            System.out.println("test " +out);
            this.cart.clear();
            this.rightLayout.removeAll();
            this.total = 0;
            this.confirmOrder.setText("ยืนยันการสั่งอาหาร ราคารวม: "+total+" ฿");
            rightLayout.add(headerOrder);

            this.formLayout.removeAll();
            getMenus();
            for (int i = 0; i < menus.model.size(); i++) {
                this.formLayout.add(getOrderCardView(menus.model.get(i).get_id(), menus.model.get(i).getName(), menus.model.get(i).getDetail(),
                        menus.model.get(i).getImage(), menus.model.get(i).getPrice()+"", menus.model.get(i).getMats_left()));
            }
        });
    }
    public void getMenus(){
        Menus out = WebClient.create().get()
                .uri("http://localhost:8080/menu")
                .retrieve()
                .bodyToMono(Menus.class)
                .block();
        menus = out;
    }

    public void addOrderToCart(String id, String name, String detail, String price,Integer mats_left) {
        OrderDetailView cartItem = new OrderDetailView(id,name,detail,price,mats_left);
        cartItem.plus.addClickListener(e->{
            if(cartItem.mats_left > cartItem.count){
                cartItem.count++;
                cartItem.number.setText(cartItem.count+"");
                this.total = 0;
                for (int i = 0; i < cart.size(); i++) {
                    total += Integer.parseInt(cart.get(i).price)*cart.get(i).count;
                }
            }
            else{
                Notification a = new Notification("วัตถุดิบไม่เพียงพอ",2000);
                a.addThemeVariants(NotificationVariant.LUMO_ERROR);
                a.open();
            }
            this.confirmOrder.setText("ยืนยันการสั่งอาหาร ราคารวม: "+total+" ฿");
        });
        cartItem.minus.addClickListener(e->{
            if (cartItem.count > 1) {
                cartItem.count--;
                cartItem.number.setText(cartItem.count+"");
                this.total = 0;
                for (int i = 0; i < cart.size(); i++) {
                    total += Integer.parseInt(cart.get(i).price)*cart.get(i).count;
                }
                this.confirmOrder.setText("ยืนยันการสั่งอาหาร ราคารวม: "+total+" ฿");
            }
        });
        cartItem.delMenu.addClickListener(e->{
           this.cart.remove(cartItem);
           this.rightLayout.remove(cartItem);
            this.total = 0;
            for (int i = 0; i < cart.size(); i++) {
                total += Integer.parseInt(cart.get(i).price)*cart.get(i).count;
            }
            this.confirmOrder.setText("ยืนยันการสั่งอาหาร ราคารวม: "+total+" ฿");
        });
        for (int i = 0; i < cart.size(); i++) {
            if(cart.get(i).getId().equals(cartItem.getId())==true){
                return;
            }
        }
        this.cart.add(cartItem);


        for (int c = 0; c < cart.size(); c++) {
            this.rightLayout.add(cart.get(c));
        }
    }

    public Div getOrderCardView(String id, String name, String detail, String imageUri, String price, Integer mats_left) {
        Image image = new Image(imageUri, name);
        Div body = new Div();
        H5 title = new H5(name);
        H6 subtitle = new H6(detail);
        Paragraph para = new Paragraph(price);


        Button addOrder = new Button("เลือก");

        Div mainLayout = new Div();
        mainLayout.addClassName("card");
        image.addClassName("card-img-top");
        image.setHeight("200px");
        body.addClassName("card-body");
        title.addClassName("card-title");
        subtitle.addClassName("card-subtitle");
        para.addClassName("card-text");

        body.add(title,subtitle,para);
        addOrder.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addOrder.addClickListener(event -> {
            this.addOrderToCart(id,name,detail,price,mats_left);
            this.total = 0;
            for (int i = 0; i < cart.size(); i++) {
                total += Integer.parseInt(cart.get(i).price)*cart.get(i).count;
            }
            this.confirmOrder.setText("ยืนยันการสั่งอาหาร ราคารวม: "+total+" ฿");
        });
        mainLayout.add(image);
        mainLayout.add(body);
        if(mats_left <= 0){
            addOrder.setEnabled(false);
            addOrder.addThemeVariants(ButtonVariant.LUMO_ERROR);
            addOrder.setText("หมด");
        }

        mainLayout.add(addOrder);

        return mainLayout;
    }
}
