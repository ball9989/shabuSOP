package com.example.shabushabu.repository;

import com.example.shabushabu.pojo.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRespository extends MongoRepository<Order, String> {

}
