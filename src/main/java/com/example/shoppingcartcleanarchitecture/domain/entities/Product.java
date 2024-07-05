package com.example.shoppingcartcleanarchitecture.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class Product {
    String id;
    String name;
    String description;
    int price;
}
