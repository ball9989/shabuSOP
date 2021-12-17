package com.example.shabushabu.manager;

import com.example.shabushabu.pojo.Menu;
import com.example.shabushabu.pojo.Menus;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
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
public class MenuGridView extends VerticalLayout {
    private Menus menu;
    private H5 menuName = new H5("ชื่อเมนู"), menuPrice = new H5("ราคา"), menuDetail = new H5("คำอธิบายเมนู");
    private Button addMenu;
    private H2 title = new H2("เมนูทั้งหมด");
    private Boolean nullFound = false;
    private String deleteId,editId;
    private String name = "", mats_left ="0", mats_cost="0", image="", price="0", detail="";
    private TextField titleFieldEdit = new TextField("ชื่อเมนู");
    private IntegerField priceField = new IntegerField("ราคาขาย");
    private IntegerField costField = new IntegerField("ราคาวัตถุดิบ");
    private IntegerField countField = new IntegerField("จำนวนวัตถุดิบ");
    private TextArea detailArea = new TextArea("คำอธิบาย");
    private TextArea imgUrl = new TextArea("url ของภาพเมนู");
    private Grid<Menu> grid = new Grid<>(Menu.class, false);

    public MenuGridView() {
        getMenu();
        addAttachListener(e -> {
            getMenu();
        });
        ///add Dialog
        Dialog dialog = new Dialog();
        dialog.getElement().setAttribute("aria-label", "Add");
        VerticalLayout dialogLayout = addMenuDialog(dialog);
        dialog.add(dialogLayout);
        dialog.setModal(false);
        dialog.setDraggable(true);

        ///delete Dialog
        Dialog delete = new Dialog();
        delete.getElement().setAttribute("aria-label", "Delete");
        VerticalLayout deleteLayout = deleteDialog(delete);
        delete.add(deleteLayout);
        delete.setModal(false);
        delete.setDraggable(true);

        ///Edit Dialog
        Dialog edit = new Dialog();
        delete.getElement().setAttribute("aria-label", "Edit");
        VerticalLayout editLayout = editDialog(edit);
        edit.add(editLayout);
        edit.setModal(false);
        edit.setDraggable(true);



        Button addMenu = new Button("เพิ่มเมนู", new Icon(VaadinIcon.PLUS), e -> dialog.open());
        addMenu.setWidth("100%");
        addMenu.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        add(dialog);

        grid.addComponentColumn(menu -> {
            HorizontalLayout group = new HorizontalLayout();
            Button editButton = new Button("แก้ไข");
            Button deleteButton = new Button("ลบ");
            editButton.getStyle().set("color", "#f0ad4e");
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
            deleteButton.addClickListener(e -> {
                deleteId = menu.get_id();
                delete.open();
            });
            editButton.addClickListener(e->{
                editId = menu.get_id();
                name = menu.getName();
                mats_left = menu.getMats_left()+"";
                mats_cost = menu.getMats_cost()+"";
                price = menu.getPrice()+"";
                image = menu.getImage();
                detail = menu.getDetail();
                this.titleFieldEdit.setValue(menu.getName());
                this.priceField.setValue(menu.getPrice());
                this.costField.setValue(menu.getMats_cost());
                this.countField.setValue(menu.getMats_left());
                this.detailArea.setValue(menu.getDetail());
                this.imgUrl.setValue(menu.getImage());
                edit.open();

            });
            group.add(editButton, deleteButton);
            group.setWidth("100%");
            return group;
        }).setWidth("15%").setFlexGrow(0).setHeader(addMenu);
        grid.addColumn(Menu::getName).setHeader("ชื่อเมนู").setFlexGrow(0);
        grid.addColumn(Menu::getMats_cost).setHeader("ราคาต้นทุน").setFlexGrow(0);
        grid.addColumn(Menu::getMats_left).setHeader("วัตถุดิบคงเหลือ").setFlexGrow(0).setWidth("10%");
        grid.addColumn(Menu::getPrice).setHeader("ราคาขาย").setFlexGrow(0);
        grid.addColumn(Menu::getDetail).setHeader("คำอธิบาย");
        grid.setItems(menu.model);
        add(title, grid);
        setVisible(false);
    }

    ///เพิ่มเมนู
    private VerticalLayout addMenuDialog(Dialog dialog) {
        //หัว Dialog
        H2 headline = new H2("เพิ่มเมนู");
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

        TextField titleField = new TextField("ชื่อเมนู");
        titleField.setPlaceholder("กรอกชื่อเมนู");

        IntegerField priceField = new IntegerField("ราคาขาย");
        priceField.setPlaceholder("จำนวนเต็ม");
        Div pricePrefix = new Div();
        pricePrefix.setText("฿");
        priceField.setPrefixComponent(pricePrefix);

        IntegerField costField = new IntegerField("ราคาวัตถุดิบ");
        Div costPrefix = new Div();
        costPrefix.setText("฿");
        costField.setPrefixComponent(costPrefix);

        IntegerField countField = new IntegerField("จำนวนวัตถุดิบ");
        Div countSuffix = new Div();
        countSuffix.setText("จำนวนชิ้น");
        countField.setSuffixComponent(countSuffix);


        TextArea detailArea = new TextArea("คำอธิบาย");
        TextArea imgUrl = new TextArea("url ของภาพเมนู");


        VerticalLayout fieldLayout = new VerticalLayout(
                titleField, priceField, costField, countField, detailArea, imgUrl
        );

        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        Button cancelButton = new Button("ยกเลิก");
        Button saveButton = new Button("เพิ่มเมนู");

        cancelButton.addClickListener(e -> {
            titleField.setValue("");
            countField.setValue(null);
            costField.setValue(null);
            imgUrl.setValue("");
            priceField.setValue(null);
            detailArea.setValue("");
            dialog.close();
        });
        saveButton.addClickListener(e -> {
            name = titleField.getValue();
            mats_left = (countField.getValue()).toString();
            mats_cost = (costField.getValue()).toString();
            image = imgUrl.getValue();
            price = (priceField.getValue()).toString();
            detail = detailArea.getValue();
            System.out.println(Integer.parseInt(mats_cost));
            if (!name.isEmpty()&&mats_left != null&&mats_cost != null&&price != null) {
                addMenu(name,mats_left,mats_cost,image,price,detail);
                Notification noti = new Notification("เพิ่มเมนู " + name + " แล้ว", 2000);
                noti.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                noti.setPosition(Notification.Position.BOTTOM_END);
                noti.open();
                getMenu();
                grid.setItems(menu.model);
                getMenu();
                dialog.close();
                titleField.setValue("");
                countField.setValue(null);
                costField.setValue(null);
                imgUrl.setValue("");
                priceField.setValue(null);
                detailArea.setValue("");
            }
            else{
                Notification noti = new Notification("ต้องกรอกข้อมูลให้ครบทุกช่่อง");
                noti.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                noti.setPosition(Notification.Position.BOTTOM_END);
                noti.open();
            }
        })
        ;

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton, saveButton);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        VerticalLayout dialogLayout = new VerticalLayout(header, fieldLayout, buttonLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "300px").set("max-width", "100%");
        return dialogLayout;
    }

    ///ปุ่มลบ
    private VerticalLayout deleteDialog(Dialog dialog) {
        H2 headline = new H2("ลบเมนู ");
        Text text = new Text("หากกดตกลงจะไม่สามารถย้อนกลับได้ คุณแน่ใจนะ?");
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
                text
        );
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        Button cancelButton = new Button("ยกเลิก");
        Button saveButton = new Button("ตกลง");

        cancelButton.addClickListener(e -> {
            deleteId = "";
            dialog.close();
        });
        saveButton.addClickListener(e -> {
            deleteMenu(deleteId);
            dialog.close();
            deleteId = "";
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

    ///ปุ่มแก้ไข
    private VerticalLayout editDialog(Dialog dialog) {
        //หัว Dialog

        H2 headline = new H2("แก้ไขเมนู");
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




        priceField.setPlaceholder("จำนวนเต็ม");
        Div pricePrefix = new Div();
        pricePrefix.setText("฿");
        priceField.setPrefixComponent(pricePrefix);


        Div costPrefix = new Div();
        costField.setPlaceholder("จำนวนเต็ม");
        costPrefix.setText("฿");
        costField.setPrefixComponent(costPrefix);


        Div countSuffix = new Div();
        countSuffix.setText("ชิ้น");
        countField.setSuffixComponent(countSuffix);




        VerticalLayout fieldLayout = new VerticalLayout(
                this.titleFieldEdit, this.priceField, this.costField, this.countField, this.detailArea, this.imgUrl
        );

        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        Button cancelButton = new Button("ยกเลิก");
        Button saveButton = new Button("แก้ไขเมนู");


        cancelButton.addClickListener(e -> {
            this.titleFieldEdit.setValue("");
            this.countField.setValue(null);
            this.costField.setValue(null);
            this.imgUrl.setValue("");
            this.priceField.setValue(null);
            this.detailArea.setValue("");
            dialog.close();
        });
        saveButton.addClickListener(e -> {
            name = this.titleFieldEdit.getValue();
            mats_left = (this.countField.getValue()).toString();
            mats_cost = (this.costField.getValue()).toString();
            image = this.imgUrl.getValue();
            price = (this.priceField.getValue()).toString();
            detail = this.detailArea.getValue();
            editMenu(editId,name,mats_left,mats_cost,image,price,detail);
            getMenu();
            dialog.close();
        })
        ;

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton, saveButton);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        VerticalLayout dialogLayout = new VerticalLayout(header, fieldLayout, buttonLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "300px").set("max-width", "100%");
        return dialogLayout;
    }

    public void getMenu() {
        Menus out = WebClient.create().get()
                .uri("http://localhost:8080/menu")
                .retrieve()
                .bodyToMono(Menus.class)
                .block();
        menu = out;
    }

    public void editMenu(String _id, String name,String mats_left,String mats_cost,String image,String price,String detail){
        System.out.println("this is a "+_id);
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("_id",_id);
        formData.add("name", name);
        formData.add("mats_left", mats_left);
        formData.add("mats_cost", mats_cost);
        formData.add("image", image);
        formData.add("price", price);
        formData.add("detail", detail);
        try {
            Boolean out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/editMenu")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
            getMenu();
            grid.setItems(menu.model);
            getMenu();
        }
        catch (Exception e){
            System.out.println("ไม่สามารถเพิ่มเมนูนี้ได้");
        }
    }


    public void addMenu(String name,String mats_left,String mats_cost,String image,String price,String detail){
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("name", name);
        formData.add("mats_left", mats_left);
        formData.add("mats_cost", mats_cost);
        formData.add("image", image);
        formData.add("price", price);
        formData.add("detail", detail);
        try {
            Boolean out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addMenu")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
            grid.setItems(menu.model);
            getMenu();
        }
        catch (Exception e){
            System.out.println("ไม่สามารถเพิ่มเมนูนี้ได้");
        }
    }


    public void deleteMenu(String id) {
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
        getMenu();
    }

}