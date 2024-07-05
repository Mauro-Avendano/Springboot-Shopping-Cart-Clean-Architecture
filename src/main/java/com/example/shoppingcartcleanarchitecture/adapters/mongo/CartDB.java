package com.example.shoppingcartcleanarchitecture.adapters.mongo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "carts")
@Setter
@Getter
public class CartDB {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    private String sessionId;
    private List<ItemCartDB> items;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
}

@AllArgsConstructor
@Getter
@Setter
class ItemCartDB {
    private String productId;
    private int quantity;
}
