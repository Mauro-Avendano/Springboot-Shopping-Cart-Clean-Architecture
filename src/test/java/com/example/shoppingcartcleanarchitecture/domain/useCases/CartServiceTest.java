package com.example.shoppingcartcleanarchitecture.domain.useCases;

import com.example.shoppingcartcleanarchitecture.domain.entities.Cart;
import com.example.shoppingcartcleanarchitecture.domain.entities.ItemCart;
import com.example.shoppingcartcleanarchitecture.domain.entities.Product;
import com.example.shoppingcartcleanarchitecture.domain.exceptions.ProductsNotFoundException;
import com.example.shoppingcartcleanarchitecture.domain.useCases.port.out.CartOutputPort;
import com.example.shoppingcartcleanarchitecture.domain.useCases.port.in.InputProduct;
import com.example.shoppingcartcleanarchitecture.domain.useCases.port.out.ProductOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceTest {
    @Mock
    CartOutputPort cartOutputAdapter;

    @Mock
    ProductOutputPort productOutputAdapter;

    @InjectMocks
    CartService cartService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCallTheCartOutputAdapterToGetCartFromPersistence() {
        String sessionId = "fake-session-id";
        Cart cart = buildCart(sessionId);
        List<InputProduct> inputProducts = Arrays.asList(new InputProduct("fake-product-1", 1), new InputProduct("fake-product-2", 2));
        when(cartOutputAdapter.getCart(sessionId)).thenReturn(cart);

        cartService.addProducts(sessionId, inputProducts);

        verify(cartOutputAdapter, times(1)).getCart(sessionId);
    }

    @Test
    void shouldThrowRunTimeExceptionWhenThereIsAnErrorGettingCartFromPersistence() {
        String sessionId = "fake-session-id";

        when(cartOutputAdapter.getCart(sessionId)).thenThrow(new RuntimeException("fake-error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> cartService.addProducts(sessionId, Arrays.asList(new InputProduct("fake-product-1", 1))));
        assertEquals("There was an error getting the cart", exception.getMessage());
    }


    @Test
    void shouldCallTheProductOutputAdapterToGetProductsFromPersistence() {
        String sessionId = "fake-session-id";
        Cart cart = buildCart(sessionId);
        String productId = "fake-product-1";
        List<InputProduct> inputProducts = Arrays.asList(new InputProduct(productId, 1));
        List<Product> products = Arrays.asList(new Product(productId, "fake-name", "fake-description", 10000));
        when(cartOutputAdapter.getCart(sessionId)).thenReturn(cart);
        when(productOutputAdapter.getProducts(Arrays.asList(productId))).thenReturn(products);

        cartService.addProducts(sessionId, inputProducts);

        verify(productOutputAdapter, times(1)).getProducts(Arrays.asList(productId));
    }

    @Test
    void shouldThrowRunTimeExceptionWhenThereIsAnErrorGettingProductsFromPersistence() {
        String sessionId = "fake-session-id";
        String productId = "fake-product-1";
        Cart cart = buildCart(sessionId);
        when(cartOutputAdapter.getCart(sessionId)).thenReturn(cart);
        when(productOutputAdapter.getProducts(Arrays.asList(productId))).thenThrow(new RuntimeException("fake-error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> cartService.addProducts(sessionId, Arrays.asList(new InputProduct(productId, 1))));
        assertEquals("There was an error getting the products", exception.getMessage());
    }

    @Test
    void shouldThrowProductsNotFoundExceptionWhenProductIsNotFound() {
        String sessionId = "fake-session-id";
        String productId = "fake-product-1";
        String messageException = "Products not found";
        Cart cart = buildCart(sessionId);
        List<String> exceptionDetails = Arrays.asList(productId);
        List<InputProduct> inputProducts = Arrays.asList(new InputProduct(productId, 1));
        when(cartOutputAdapter.getCart(sessionId)).thenReturn(cart);
        when(productOutputAdapter.getProducts(Arrays.asList(productId))).thenThrow(new ProductsNotFoundException(messageException, Arrays.asList(productId)));

        ProductsNotFoundException exception = assertThrows(ProductsNotFoundException.class, () -> { cartService.addProducts(sessionId, inputProducts); });
        assertIterableEquals(exceptionDetails, exception.getDetails());
        assertEquals("Some products were not found", exception.getMessage());
    }

    private Cart buildCart(String sessionId) {
        String cartId = "fake-cart-id";
        Product product = new Product("fake-product-id", "fake-product-name", "fake-product-description", 1000);
        ItemCart itemCart = new ItemCart(product, 1);
        Cart cart = new Cart(cartId, sessionId, Arrays.asList(itemCart));

        return cart;
    }
}
