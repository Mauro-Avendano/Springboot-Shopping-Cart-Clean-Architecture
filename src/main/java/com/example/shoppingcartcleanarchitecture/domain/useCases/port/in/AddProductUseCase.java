package com.example.shoppingcartcleanarchitecture.domain.useCases.port.in;

import com.example.shoppingcartcleanarchitecture.domain.entities.Cart;

import java.util.List;

public interface AddProductUseCase {
    Cart addProducts(String cartId, List<InputProduct> inputProducts);
}

