package com.example.shabushabu.repository;

import com.example.shabushabu.pojo.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Menu,String> {

}
