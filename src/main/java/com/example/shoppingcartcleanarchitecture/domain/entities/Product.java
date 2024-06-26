package com.example.shoppingcartcleanarchitecture.domain.entities;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Product {
    String productId;
    String productName;
    String productDescription;
    int productPrice;
}
