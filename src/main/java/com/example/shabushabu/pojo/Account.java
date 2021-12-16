package com.example.shabushabu.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document("Account")
public class Account implements Serializable {
    @Id
    private String _id;
    private String username;
    private String password;
    private String role;
    private int tableNo;
    private String token;

    public Account(){}

    public Account(String _id,String username,String password, String role,int tableNo,String token){
        this._id = _id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.tableNo = tableNo;
        this.token = token;
    }
}
