package com.example.shabushabu.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@Document("Order")
public class Order implements Serializable {
    private String _id;
    private Integer tableNo;
    private Double totalPrice;
    private String status;
    private ArrayList<ServeOrder> order = new ArrayList<>();

    public Order() {}
    public Order(String _id, Integer tableNo, Double totalPrice,String status,ArrayList<ServeOrder> orders) {
        this._id = _id;
        this.tableNo = tableNo;
        this.totalPrice = totalPrice;
        this.status = status;
        this.order = orders;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setTableNo(Integer tableNo) {
        this.tableNo = tableNo;
    }

    public void setOrders(ArrayList<ServeOrder> orders) {
        this.order = orders;
    }

    public String get_id() {
        return _id;
    }

    public Integer getTableNo() {
        return tableNo;
    }

    public ArrayList<ServeOrder> getOrders() {
        return order;
    }
}
