package com.example.shabushabu.repository;

import com.example.shabushabu.pojo.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends MongoRepository<Account,String> {

    @Query(value = "{username: ?0, password: ?1}")
    public Account login(String username, String password);
}
