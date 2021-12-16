package com.example.shabushabu.test;

import java.io.Serializable;

public class ServeOrder implements Serializable {
    private String _id;
    private String name;
    private Double count;

    public ServeOrder() {}
    public ServeOrder(String id, String name, Double count) {
        this._id = id;
        this.name = name;
        this.count = count;
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

    public void setCount(Double count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public Double getCount() {
        return count;
    }
}
