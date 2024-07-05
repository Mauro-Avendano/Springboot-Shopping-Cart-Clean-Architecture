package com.example.shoppingcartcleanarchitecture.adapters.mongo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoCartRepository extends MongoRepository<CartDB, String> {
    CartDB findBySessionId(String sessionId);
}
