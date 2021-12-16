package com.example.shabushabu.test;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Publisher {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @RequestMapping(value = "/testpub", method = RequestMethod.GET)
    public String  testPub() {
        Object str = rabbitTemplate.convertSendAndReceive("Direct", "", "");
        return (String) str;
    }
}
