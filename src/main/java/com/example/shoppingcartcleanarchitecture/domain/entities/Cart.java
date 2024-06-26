package com.example.shoppingcartcleanarchitecture.domain.entities;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class Cart {
    private String id;
    private String sessionId;
    private List<ItemCart> items;

    public Cart(String sessionId, List<ItemCart> items) {
        this.sessionId = sessionId;
        this.items = items;
    }
}
