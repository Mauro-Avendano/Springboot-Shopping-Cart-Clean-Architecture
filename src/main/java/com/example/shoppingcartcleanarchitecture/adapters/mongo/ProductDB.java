package com.example.shoppingcartcleanarchitecture.adapters.mongo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "products")
@Getter
@Setter
@AllArgsConstructor
public class ProductDB {
    @Id
    private ObjectId id;
    private String name;
    private String description;
    private int price;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
}
