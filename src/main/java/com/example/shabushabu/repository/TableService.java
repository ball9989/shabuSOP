package com.example.shabushabu.repository;

import com.example.shabushabu.pojo.Account;
import com.example.shabushabu.pojo.Order;
import com.example.shabushabu.pojo.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TableService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OrderRespository orderRespository;

    public void confirmPayment(Integer tableNo) {
        List<Order> orders = orderRespository.findAll();
        for (int i=0;i<orders.size();i++) {
            if (orders.get(i).getTableNo() == tableNo && orders.get(i).getStatus().equals("pending")) {
                orders.get(i).setStatus("success");
                System.out.println("set status order "+ orders.get(i).getStatus());
                orderRespository.save(orders.get(i));
            }
        }
    }

    public List<Table> getTables() {
        System.out.println("table service");
        List<Account> accountList = accountRepository.findAll();

        ArrayList<Table> tables = new ArrayList<>();
        for (int i=0; i< accountList.size();i++) {
            Double totalePrice = 0.0;
            String status = "success";
            if (accountList.get(i).getRole().equals("customer")) {
                System.out.println("account user name ->"+accountList.get(i).getUsername());
                ArrayList<Order> ordersList = new ArrayList<>();

                List<Order> orders = orderRespository.findAll();
                for (int j=0;j<orders.size();j++) {
                    if (orders.get(j).getTableNo().equals(accountList.get(i).getTableNo()) && orders.get(j).getStatus().equals("pending")) {
                        ordersList.add(orders.get(j));
                        status = "pending";
                        totalePrice += orders.get(j).getTotalPrice();
                    }
                }
                tables.add(new Table(accountList.get(i).get_id(), accountList.get(i).getTableNo(),totalePrice, status, ordersList));
            }
        }
        return  tables;
    }
}
