package com.example.shoppingcartcleanarchitecture.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ItemCart {
    private Product product;
    private int quantity;
}
