package com.example.shabushabu.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document("Order")
public class Order implements Serializable {
    @Id
    private String _id;
    private String name;
    private String image;
    private String detail;
    private int price;

    public Order (){}

    public Order (String _id, String name, String image, String detail, int price){
        this._id = _id;
        this.name = name;
        this.image = image;
        this.detail = detail;
        this.price = price;
    }
}
