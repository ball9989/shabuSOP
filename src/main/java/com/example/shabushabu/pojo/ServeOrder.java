package com.example.shabushabu.pojo;

import java.io.Serializable;


public class ServeOrder implements Serializable {
    private String _id;
    private String name;
    private Integer count;
    private Double price;

    public ServeOrder() {}
    public ServeOrder(String id, String name, Integer count, Double price) {
        this._id = id;
        this.name = name;
        this.count = count;
        this.price = price;
    }


    public void setPrice(Double price) {
        this.price = price;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public Integer getCount() {
        return count;
    }

    public Double getTotalPrice() {
        return this.price * this.count;
    }
    public Double getPrice() {
        return price;
    }
}