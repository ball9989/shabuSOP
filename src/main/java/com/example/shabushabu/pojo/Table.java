package com.example.shabushabu.pojo;

import java.io.Serializable;
import java.util.ArrayList;

public class Table implements Serializable {
    private String _id;
    private Integer tableNo;
    private Double totalPrice;
    private String status;
    private ArrayList<Order> orders = new ArrayList<>();

    public Table() {}
    public Table(String _id, Integer tableNo, Double totalPrice, String status,ArrayList<Order> orders) {
        this._id = _id;
        this.tableNo = tableNo;
        this.orders = orders;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    public String get_id() {
        return _id;
    }

    public Integer getTableNo() {
        return tableNo;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setTableNo(Integer tableNo) {
        this.tableNo = tableNo;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
