package com.example.shoppingcartcleanarchitecture.domain.useCases;

import com.example.shoppingcartcleanarchitecture.domain.entities.Cart;
import com.example.shoppingcartcleanarchitecture.domain.useCases.interfaces.CartOutputPort;
import com.example.shoppingcartcleanarchitecture.domain.useCases.interfaces.AddProductUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService implements AddProductUseCase {
    private final CartOutputPort cartOutputAdapter;

    @Autowired
    public CartService(CartOutputPort cartOutputAdapter) {
        this.cartOutputAdapter = cartOutputAdapter;
    }

    @Override
    public Cart getCart(String sessionId) {
        Cart cart = cartOutputAdapter.getCart(sessionId);

        return cart;
    }
}
