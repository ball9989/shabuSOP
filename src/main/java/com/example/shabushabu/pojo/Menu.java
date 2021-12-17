package com.example.shabushabu.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Data
@Document("Menu")

public class Menu implements Serializable {
    @Id
    private String _id;
    private String name;
    private String image;
    private String detail;
    private int price;
    private int mats_left;
    private int mats_cost;

    public Menu (){}

    public Menu (String _id, String name, String image, String detail, int price,int mats_left,int mats_cost){
        this._id = _id;
        this.name = name;
        this.image = image;
        this.detail = detail;
        this.price = price;
        this.mats_left = mats_left;
        this.mats_cost = mats_cost;
    }

    public Menu(String id, String username) {
    }
}
