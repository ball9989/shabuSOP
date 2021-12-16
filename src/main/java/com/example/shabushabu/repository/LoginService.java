package com.example.shabushabu.repository;

import com.example.shabushabu.pojo.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private LoginRepository repository;

    public LoginService(LoginRepository respository){
        this.repository = repository;
    }

    public Account login(String username, String password) {
        return repository.login(username,password);
    }

}
