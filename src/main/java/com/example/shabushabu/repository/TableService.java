package com.example.shabushabu.repository;

import com.example.shabushabu.pojo.*;
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

    @Autowired
    private BillRepository billRepository;

    public void confirmPayment(Integer tableNo) {
        List<Order> orders = orderRespository.findAll();
        Double totalPrice = 0.0;
        String status = "success";
        Bill bill = new Bill(null, tableNo, totalPrice,status,new ArrayList<ServeOrder>());

        for (int i=0;i<orders.size();i++) {
            if (orders.get(i).getTableNo() == tableNo && orders.get(i).getStatus().equals("pending")) {
                orders.get(i).setStatus("success");
                System.out.println("set status order "+ orders.get(i).getStatus());
                orderRespository.save(orders.get(i));

                for (int j=0;j<orders.get(i).getOrder().size();j++) {
                    totalPrice += orders.get(i).getOrder().get(j).getPrice() * orders.get(i).getOrder().get(j).getCount();
                    bill.getOrder().add(new ServeOrder(orders.get(i).getOrder().get(j).get_id(), orders.get(i).getOrder().get(j).getName(), orders.get(i).getOrder().get(j).getCount(), orders.get(i).getOrder().get(j).getPrice()));
                }
            }
        }
        bill.setTotalPrice(totalPrice);
        billRepository.save(bill);
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

    public List<Bill> getBills() {
        return billRepository.findAll();
    }
}
