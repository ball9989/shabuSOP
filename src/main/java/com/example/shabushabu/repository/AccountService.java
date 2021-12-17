package com.example.shabushabu.repository;

import com.example.shabushabu.pojo.Account;
import com.example.shabushabu.pojo.Order;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository repository;

    public AccountService(AccountRepository repository){
        this.repository = repository;
    }

    @RabbitListener(queues = "GetAccountQueue")
    public List<Account> getAccount() {
        return repository.findAll();
    }

}
