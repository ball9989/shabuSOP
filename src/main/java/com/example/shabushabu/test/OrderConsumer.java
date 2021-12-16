package com.example.shabushabu.test;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OrderConsumer {

    @RabbitListener(queues = "AddOrderQueue")
    public void addBadSentence(ArrayList<ServeOrder> arrayList) {
        System.out.println("consumer "+arrayList);
    }

}
