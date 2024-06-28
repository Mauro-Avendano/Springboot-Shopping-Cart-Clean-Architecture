package com.example.shoppingcartcleanarchitecture.domain.useCases.port.out;

import com.example.shoppingcartcleanarchitecture.domain.entities.Product;
import com.example.shoppingcartcleanarchitecture.domain.useCases.port.in.InputProduct;

import java.util.List;

public interface ProductOutputPort {
    public List<Product> getProducts(List<String> productsIds);
}
