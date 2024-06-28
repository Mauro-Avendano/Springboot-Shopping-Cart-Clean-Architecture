package com.example.shoppingcartcleanarchitecture.domain.useCases.port.out;

import com.example.shoppingcartcleanarchitecture.domain.entities.Cart;

public interface CartOutputPort {
    Cart getCart(String sessionId);
}
