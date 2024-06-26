package com.example.shoppingcartcleanarchitecture.domain.useCases;

import com.example.shoppingcartcleanarchitecture.domain.entities.Cart;
import com.example.shoppingcartcleanarchitecture.domain.entities.ItemCart;
import com.example.shoppingcartcleanarchitecture.domain.entities.Product;
import com.example.shoppingcartcleanarchitecture.domain.useCases.interfaces.CartOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.mockito.Mockito.*;

class CartServiceTest {
    @Mock
    CartOutputPort cartOutputAdapter;

    @InjectMocks
    CartService cartService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCallTheCartOutputAdapter() {
        String cartId = "fake-cart-id";
        String sessionId = "fake-session-id";
        Product product = new Product("fake-product-id", "fake-product-name", "fake-product-description", 1000);
        ItemCart itemCart = new ItemCart(product, 1);
        Cart cart = new Cart(cartId, sessionId, Arrays.asList(itemCart));

        //we need to check if the cart exists in the database
        when(cartOutputAdapter.getCart(sessionId)).thenReturn(cart);

        cartService.getCart(sessionId);

        verify(cartOutputAdapter, times(1)).getCart(sessionId);
    }
}
