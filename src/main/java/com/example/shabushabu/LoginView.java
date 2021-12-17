package com.example.shabushabu;


import com.example.shabushabu.pojo.Menus;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;


@Route(value = "login")
public class LoginView extends VerticalLayout {
    private final LoginForm login = new LoginForm();
    private Image image = new Image("https://kiji.life/eats/wp-content/uploads/2018/07/FCM_0541-1024x683.jpg","logo");

    public LoginView() {
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        login.setForgotPasswordButtonVisible(false);
        image.setWidth("500px");
        add(image, login);

        login.addLoginListener(e -> {
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("username", e.getUsername());
            formData.add("password", e.getPassword());

            MultiValueMap<String, String> out = WebClient.create().post()
                    .uri("http://localhost:8080/userLogin")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(MultiValueMap.class)
                    .block();

            Map<String,String> d = out.toSingleValueMap();
            System.out.println(d);
            if(d.get("status").equals("false")){
                Notification a = new Notification("Username or Password is invalid", 5000);
                a.addThemeVariants(NotificationVariant.LUMO_ERROR);
                a.open();
                login.setEnabled(true);
            }
            else{
                if(d.get("ses_role").equals("customer")) {
                    Notification a = new Notification("Login Success", 5000);
                    a.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    a.open();
                    login.getUI().ifPresent(ui ->
                            ui.navigate(OrderView.class, d.get("tableNo")));
                }
                else if(d.get("ses_role").equals("manager")){
                    Notification a = new Notification("Login Success", 5000);
                    a.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    a.open();
                    login.getUI().ifPresent(ui ->
                            ui.navigate("manager"));
                }
                else{
                    Notification a = new Notification("Login Success", 5000);
                    a.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    a.open();
                    login.getUI().ifPresent(ui ->
                            ui.navigate("employee"));
                }
            }


        });
    }
}