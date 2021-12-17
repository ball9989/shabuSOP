package com.example.shabushabu.repository;

import com.example.shabushabu.pojo.Bill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends MongoRepository<Bill, String> {
}
