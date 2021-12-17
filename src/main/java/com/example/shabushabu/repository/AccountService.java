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

    @RabbitListener(queues = "EditAccountQueue")
    public Boolean editAccount(String id,String role){
        List<Account> account = repository.findAll();
        for (int i=0;i<account.size();i++) {
            if (account.get(i).get_id() == id) {
                account.get(i).setRole(role);
                repository.save(account.get(i));
            }
        }
        return true;
    }
}
