package com.example.shabushabu.manager;


import com.example.shabushabu.pojo.Account;
import com.example.shabushabu.pojo.Accounts;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.awt.*;


public class AccountGridView extends VerticalLayout {
    private Accounts accounts = new Accounts();
    private String id = "",username ="",role="";
    private TextField idField, usernameField;
    private ComboBox<String> valueComboBox = new ComboBox<>();
    private Grid<Account> grid = new Grid<>(Account.class,false);
    public AccountGridView(){
        getAccount();
        Dialog dialog = new Dialog();
        dialog.getElement().setAttribute("aria-label", "Create new employee");
        dialog.setDraggable(true);
        VerticalLayout dialogLayout = editDialogLayout(dialog);
        dialog.add(dialogLayout);
        H2 title = new H2("รายชื่อผู้ใช้งาน");
        grid.addColumn(Account::get_id).setHeader("ID");
        grid.addColumn(Account::getUsername).setHeader("Username");
        grid.addColumn(Account::getRole).setHeader("Role");
        grid.setItems(accounts.model);
        grid.addComponentColumn(account -> {
            Button editButton = new Button("แก้ไข Role");
            editButton.addClickListener(e-> {
                this.id = account.get_id();
                this.username = account.getUsername();
                this.role = account.getRole();
                this.idField.setValue(this.id);
                this.usernameField.setValue(this.username);
                this.valueComboBox.setValue(this.role);
                dialog.open();
            });
        return editButton;}).setHeader("");
        add(title,grid,dialog);
        setWidth("100%");
    }
    private VerticalLayout editDialogLayout(Dialog dialog) {
        H2 headline = new H2("แก้ไข Role");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");

        usernameField = new TextField("Username");
        this.idField = new TextField("ID");
        this.idField.setEnabled(false);
        usernameField.setEnabled(false);
        usernameField.setEnabled(false);
        VerticalLayout fieldLayout = new VerticalLayout();
        fieldLayout.add(idField,usernameField);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        valueComboBox.setLabel("Role");
        valueComboBox.setItems("employee", "manager", "customer");

        Button cancelButton = new Button("Cancel", e -> dialog.close());
        Button saveButton = new Button("Save");
        saveButton.addClickListener(e ->{
            editRole(this.id,this.role);
            dialog.close();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton,
                saveButton);
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        VerticalLayout dialogLayout = new VerticalLayout(headline, fieldLayout,valueComboBox,
                buttonLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "300px").set("max-width", "100%");

        return dialogLayout;
    }

    public void getAccount(){
        Accounts out = WebClient.create().get()
                .uri("http://localhost:8080/getAccount")
                .retrieve()
                .bodyToMono(Accounts.class)
                .block();
        accounts = out;
    }
    public void editRole(String _id,String role){
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("_id",_id);
        formData.add("role",role);
        Boolean out = WebClient.create()
                .post()
                .uri("http://localhost:8080/editRole")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
        grid.setItems(accounts.model);
        getAccount();
    }
}
