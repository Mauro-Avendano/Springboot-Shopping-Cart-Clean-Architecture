package com.example.shoppingcartcleanarchitecture.domain.useCases.interfaces;

import com.example.shoppingcartcleanarchitecture.domain.entities.Cart;

public interface CartOutputPort {
    Cart getCart(String sessionId);
}
