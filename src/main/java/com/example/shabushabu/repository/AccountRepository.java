package com.example.shabushabu.repository;

import com.example.shabushabu.pojo.Account;
import com.example.shabushabu.pojo.Table;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {

}
