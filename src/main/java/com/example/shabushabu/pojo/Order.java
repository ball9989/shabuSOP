package com.example.shabushabu.pojo;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
    private String _id;
    private String tableNo;
    private ArrayList<ServeOrder> orders = new ArrayList<>();

    public Order() {}
    public Order(String _id, String tableNo, ArrayList<ServeOrder> orders) {
        this._id = _id;
        this.tableNo = tableNo;
        this.orders = orders;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public void setOrders(ArrayList<ServeOrder> orders) {
        this.orders = orders;
    }

    public String get_id() {
        return _id;
    }

    public String getTableNo() {
        return tableNo;
    }

    public ArrayList<ServeOrder> getOrders() {
        return orders;
    }
}
