package com.example.shoppingcartcleanarchitecture.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Product {
    String id;
    String name;
    String description;
    int price;
}
