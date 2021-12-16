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
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.springframework.web.reactive.function.client.WebClient;

import java.awt.*;

@StyleSheet("https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css")
public class MenuData extends VerticalLayout {
    private Menus menu;
    private H5 menuName,menuPrice,menuDetail;
    private Button addMenu;
    private H2 title = new H2("เมนูทั้งหมด");
    public MenuData(){
        getMenu();
        addMenu = new Button("เพิ่มเมนู",new Icon(VaadinIcon.PLUS));
        Grid<Menu> grid = new Grid<>(Menu.class, false);
        grid.addComponentColumn(menu ->{
            Button editButton = new Button("แก้ไข");
            editButton.addClickListener(e->{
                System.out.println(menu.getPrice());
            });
            return editButton;
        }).setWidth("12%").setFlexGrow(0).setHeader(addMenu);
        grid.addColumn(Menu::getName).setHeader("ชื่อเมนู").setFlexGrow(0);
        grid.addColumn(Menu::getPrice).setHeader("ราคา(บาท)").setFlexGrow(0);
        grid.addColumn(Menu::getDetail).setHeader("คำอธิบาย");
        grid.setItems(menu.model);
        add(title,grid);
        setVisible(false);

        Dialog dialog = new Dialog();
        dialog.getElement().setAttribute("aria-label", "Add note");

        VerticalLayout dialogLayout = createDialogLayout(dialog);
        dialog.add(dialogLayout);
        dialog.setModal(false);
        dialog.setDraggable(true);

        Button button = new Button("Show dialog", e -> dialog.open());
        add(dialog, button);
    }

    private static VerticalLayout createDialogLayout(Dialog dialog) {
        H2 headline = new H2("Add note");
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
        TextArea descriptionArea = new TextArea("Description");
        VerticalLayout fieldLayout = new VerticalLayout(titleField, descriptionArea);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);

        Button cancelButton = new Button("ยกเลิก", e -> dialog.close());
        Button saveButton = new Button("เพิ่มเมนู", e -> dialog.close());
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
    public void test(){
        System.out.println("Test");
        remove(title);
        title.setText("test");
        add(title);
    }

}