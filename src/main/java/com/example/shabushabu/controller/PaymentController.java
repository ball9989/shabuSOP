package com.example.shabushabu.controller;

import com.example.shabushabu.pojo.*;
import com.example.shabushabu.repository.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class PaymentController {
    @Autowired
    private TableService tableService;
    protected Tables tables = new Tables();



    @RequestMapping(value = "/tables", method = RequestMethod.GET)
    public ResponseEntity<?> getTables() {
        System.out.println();
        Object obj = tableService.getTables();
        ArrayList<Table> tablesList = (ArrayList<Table>) obj;
        tables.model = tablesList;
        System.out.println(tables);

        return ResponseEntity.ok(tables);
    }


    @RequestMapping(value = "/payment/confirm", method = RequestMethod.POST)
    public Boolean confirmOrder(@RequestBody MultiValueMap<String, String> n) {
        try {
            Map<String, String> d = n.toSingleValueMap();
            Integer tableNo = Integer.parseInt(d.get("tableNo"));
            System.out.println(tableNo);
            tableService.confirmPayment(tableNo);
            return true;
        } catch (Exception e) {
            return false;
        }
    }



}
