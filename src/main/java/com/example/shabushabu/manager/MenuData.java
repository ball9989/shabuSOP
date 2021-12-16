package com.example.shabushabu.manager;

import com.example.shabushabu.pojo.Menu;
import com.example.shabushabu.pojo.Menus;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import javax.xml.validation.Validator;
import java.awt.*;
import java.util.ArrayList;

@StyleSheet("https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css")
public class MenuData extends VerticalLayout {
    private Menus menu;
    private H5 menuName = new H5("ชื่อเมนู")
            ,menuPrice = new H5("ราคา")
            ,menuDetail = new H5("คำอธิบายเมนู");
    private Button addMenu;
    private H2 title = new H2("เมนูทั้งหมด");
    Grid<Menu> grid = new Grid<>(Menu.class, false);
    public MenuData(){
        getMenu();
        addAttachListener(e->{
            getMenu();
        });
        ///add Dialog
        Dialog dialog = new Dialog();
        dialog.getElement().setAttribute("aria-label", "Add note");
        VerticalLayout dialogLayout = addMenuDialog(dialog);
        dialog.add(dialogLayout);
        dialog.setModal(false);
        dialog.setDraggable(true);

        ///delete Dialog
//        Dialog delete = new Dialog();
//        dialog.getElement().setAttribute();

        Button addMenu = new Button("เพิ่มเมนู",new Icon(VaadinIcon.PLUS), e -> dialog.open());
        addMenu.setWidth("100%");
        addMenu.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        add(dialog);

        grid.addComponentColumn(menu ->{
            HorizontalLayout group = new HorizontalLayout();
            Button editButton = new Button("แก้ไข");
            Button deleteButton = new Button("ลบ");
            editButton.getStyle().set("color","#f0ad4e");
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
            deleteButton.addClickListener(e->{
                deleteMenu(menu.get_id());
            });
//            editButton.addClickListener(e->{
//
//            });
            group.add(editButton,deleteButton);
            group.setWidth("100%");
            return group;
        }).setWidth("15%").setFlexGrow(0).setHeader(addMenu);
        grid.addColumn(Menu::getName).setHeader("ชื่อเมนู").setFlexGrow(0);
        grid.addColumn(Menu::getPrice).setHeader("ราคา(บาท)").setFlexGrow(0);
        grid.addColumn(Menu::getDetail).setHeader("คำอธิบาย");
        grid.setItems(menu.model);
        add(title,grid);
        setVisible(false);
    }

    private VerticalLayout addMenuDialog(Dialog dialog) {
        H2 headline = new H2("เพิ่มเมนู");
        TextField titleField = new TextField("ชื่อเมนู");
        TextField priceField = new TextField("ราคาขาย");
        TextField costField = new TextField("ราคาวัตถุดิบ");
        TextField countField = new TextField("จำนวนวัตถุดิบ");
        TextArea detailArea = new TextArea("คำอธิบาย");
        TextArea imgUrl = new TextArea("url ของภาพเมนู");


        headline.getStyle().set("margin", "0").set("font-size", "1.5em")
                .set("font-weight", "bold");
        HorizontalLayout header = new HorizontalLayout(headline);
        header.getElement().getClassList().add("draggable");
        header.setSpacing(false);
        header.getStyle()
                .set("border-bottom", "1px solid var(--lumo-contrast-20pct)")
                .set("cursor", "move");
        header.getStyle()
                .set("padding", "var(--lumo-space-m) var(--lumo-space-l)")
                .set("margin",
                        "calc(var(--lumo-space-s) * -1) calc(var(--lumo-space-l) * -1) 0");

        VerticalLayout fieldLayout = new VerticalLayout(
                titleField,priceField,costField,countField, detailArea,imgUrl
        );

        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        Button cancelButton = new Button("ยกเลิก", e -> dialog.close());

        Button saveButton = new Button("เพิ่มเมนู");
        saveButton.addClickListener(e->{
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("name", titleField.getValue());
            formData.add("mats_left", countField.getValue());
            formData.add("mats_cost", costField.getValue());
            formData.add("image", imgUrl.getValue());
            formData.add("price", priceField.getValue());
            formData.add("detail", detailArea.getValue());
            Boolean nullFound = false;
            try {
                Boolean out = WebClient.create()
                        .post()
                        .uri("http://localhost:8080/addMenu")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .body(BodyInserters.fromFormData(formData))
                        .retrieve()
                        .bodyToMono(Boolean.class)
                        .block();
            }
            catch (Exception ex){
                nullFound = true;
                System.out.println("ไม่สามารถเพิ่มเมนูที่มีรายละเอียดไม่ครบ");
                Notification noti = new Notification("",2000);
                noti.setText("X ไม่สามารถเพิ่มเมนูที่มีรายละเอียดไม่ครบ");
                noti.addThemeVariants(NotificationVariant.LUMO_ERROR);
                noti.setPosition(Notification.Position.BOTTOM_END);
                noti.open();
            }
            if(nullFound == false){
                Notification noti = new Notification("เพิ่มเมนู "+titleField.getValue()+" แล้ว",2000);
                noti.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                noti.setPosition(Notification.Position.BOTTOM_END);
                noti.open();
                getMenu();
                grid.setItems(menu.model);
                dialog.close();
            }
        })
        ;

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton,
                saveButton);
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        VerticalLayout dialogLayout = new VerticalLayout(header, fieldLayout,
                buttonLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "300px").set("max-width", "100%");
        return dialogLayout;
    }

    public void getMenu(){
        Menus out = WebClient.create().get()
                .uri("http://localhost:8080/menu")
                .retrieve()
                .bodyToMono(Menus.class)
                .block();
        menu = out;
    }

    public void deleteMenu(String id){
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("_id", id);
        Boolean out = WebClient.create()
                .post()
                .uri("http://localhost:8080/deleteMenu")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
        System.out.println(out);
        getMenu();
        grid.setItems(menu.model);
    }
}