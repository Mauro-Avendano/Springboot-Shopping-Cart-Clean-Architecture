package com.example.shoppingcartcleanarchitecture.domain.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class ProductsNotFoundException extends RuntimeException {
    private List<String> details;

    public ProductsNotFoundException(String message, List<String> details) {
        super(message);
        this.details = details;
    }
}
