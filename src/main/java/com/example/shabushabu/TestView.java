package com.example.shabushabu;


import com.example.shabushabu.test.ServeOrder;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.awt.*;
import java.util.ArrayList;

@Route(value = "test")
//@Service
public class TestView extends VerticalLayout {
    Button btn = new Button("test1");
    H1 text = new H1("");
    public TestView() {

        this.add(btn, text);
        btn.addClickListener(event -> {
            String out = WebClient.create().get()
                    .uri("http://localhost:8080")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            this.text.setText(out);
        });
    }

    @RabbitListener(queues = "AddOrderQueue")
    public void addBadSentence(ArrayList<ServeOrder> arrayList) {
        System.out.println("consumer "+arrayList);
    }
}
