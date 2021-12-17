package com.example.shabushabu.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@Document("Bill")
public class Bill implements Serializable {
    private String _id;
    private Integer tableNo;
    private Double totalPrice;
    private String status;
    private ArrayList<ServeOrder> order = new ArrayList<>();

    public Bill() {}
    public Bill(String _id, Integer tableNo, Double totalPrice,String status,ArrayList<ServeOrder> orders) {
        this._id = _id;
        this.tableNo = tableNo;
        this.totalPrice = totalPrice;
        this.status = status;
        this.order = orders;
    }

}
