package com.example.shoppingcartcleanarchitecture.adapters.mongo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoProductRepository extends MongoRepository<ProductDB, String> {
    List<ProductDB> findByIdIn(List<String> ids);
}
