package com.example.shoppingcartcleanarchitecture.domain.useCases;

import com.example.shoppingcartcleanarchitecture.domain.entities.Cart;
import com.example.shoppingcartcleanarchitecture.domain.entities.Product;
import com.example.shoppingcartcleanarchitecture.domain.exceptions.ProductsNotFoundException;
import com.example.shoppingcartcleanarchitecture.domain.useCases.port.out.CartOutputPort;
import com.example.shoppingcartcleanarchitecture.domain.useCases.port.in.AddProductUseCase;
import com.example.shoppingcartcleanarchitecture.domain.useCases.port.in.InputProduct;
import com.example.shoppingcartcleanarchitecture.domain.useCases.port.out.ProductOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.availability.LivenessState;
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
        try {
            Cart cart = getCart(sessionId);
            List<Product> products = getProducts(inputProducts);

            return cart;
        } catch (ProductsNotFoundException e) {
            throw new ProductsNotFoundException("Some products were not found", e.getDetails());
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private List<Product> getProducts(List<InputProduct> inputProducts) {
        try {
            return productOutputAdapter.getProducts(inputProducts.stream().map(inputProduct -> inputProduct.productId()).collect(Collectors.toUnmodifiableList()));
        } catch (ProductsNotFoundException e) {
            throw new ProductsNotFoundException("Some products were not found", e.getDetails());
        } catch (Exception e) {
            throw new RuntimeException("There was an error getting the products");
        }
    }

    private Cart getCart(String sessionId) {
        try {
            return cartOutputAdapter.getCart(sessionId);
        } catch (Exception e) {
            throw new RuntimeException("There was an error getting the cart");
        }
    }
}
