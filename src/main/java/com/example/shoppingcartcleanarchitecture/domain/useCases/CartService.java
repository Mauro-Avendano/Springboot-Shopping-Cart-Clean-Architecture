package com.example.shoppingcartcleanarchitecture.domain.useCases;

import com.example.shoppingcartcleanarchitecture.domain.entities.Cart;
import com.example.shoppingcartcleanarchitecture.domain.entities.Product;
import com.example.shoppingcartcleanarchitecture.domain.useCases.port.out.CartOutputPort;
import com.example.shoppingcartcleanarchitecture.domain.useCases.port.in.AddProductUseCase;
import com.example.shoppingcartcleanarchitecture.domain.useCases.port.in.InputProduct;
import com.example.shoppingcartcleanarchitecture.domain.useCases.port.out.ProductOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService implements AddProductUseCase {
    private final CartOutputPort cartOutputAdapter;
    private final ProductOutputPort productOutputAdapter;

    @Autowired
    public CartService(CartOutputPort cartOutputAdapter, ProductOutputPort productOutputAdapter) {
        this.cartOutputAdapter = cartOutputAdapter;
        this.productOutputAdapter = productOutputAdapter;
    }

    @Override
    public Cart addProducts(String sessionId, List<InputProduct> inputProducts) {
        Cart cart = cartOutputAdapter.getCart(sessionId);

        List<Product> products = productOutputAdapter.getProducts(inputProducts.stream().map((product -> product.productId())).collect(Collectors.toUnmodifiableList()));

        return cart;
    }
}
