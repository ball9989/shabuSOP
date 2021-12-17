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
    public AccountGridView() {
        getAccount();
        H2 title = new H2("รายชื่อผู้ใช้งาน");
        grid.addColumn(Account::get_id).setHeader("ID");
        grid.addColumn(Account::getUsername).setHeader("Username");
        grid.addColumn(Account::getRole).setHeader("Role");
        grid.setItems(accounts.model);
        add(title,grid);
        setWidth("100%");
    }


    public void getAccount(){
        Accounts out = WebClient.create().get()
                .uri("http://localhost:8080/getAccount")
                .retrieve()
                .bodyToMono(Accounts.class)
                .block();
        accounts = out;
    }

}
