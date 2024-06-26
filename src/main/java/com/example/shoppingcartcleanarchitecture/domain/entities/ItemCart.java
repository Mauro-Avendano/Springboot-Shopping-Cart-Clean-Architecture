package com.example.shoppingcartcleanarchitecture.domain.entities;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ItemCart {
    private Product product;
    private int quantity;
}
