package com.example.shoppingcartcleanarchitecture.adapters.mongo;

import com.example.shoppingcartcleanarchitecture.domain.entities.Cart;
import com.example.shoppingcartcleanarchitecture.domain.entities.ItemCart;
import com.example.shoppingcartcleanarchitecture.domain.entities.Product;
import com.example.shoppingcartcleanarchitecture.domain.exceptions.CartNotFoundException;
import com.example.shoppingcartcleanarchitecture.domain.useCases.port.out.CartOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MongoCartAdapter implements CartOutputPort {
    MongoCartRepository cartRepository;
    MongoProductRepository productRepository;

    @Autowired
    public MongoCartAdapter(MongoCartRepository cartRepository, MongoProductRepository mongoProductRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = mongoProductRepository;
    }

    @Override
    public Cart getCart(String sessionId) {
        try {
            CartDB cartDB = cartRepository.findBySessionId(sessionId);
            if (cartDB == null) {
                throw new CartNotFoundException("Cart not found");
            }
            List<String> productsIds = cartDB.getItems().stream().map(itemCartDB -> itemCartDB.getProductId()).collect(Collectors.toUnmodifiableList());

            List<ProductDB> productsDB = productRepository.findByIdIn(productsIds);

            return buildCart(cartDB, productsDB);
        } catch (CartNotFoundException e) {
            throw new CartNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("There was an error getting the cart");
        }
    }

    public void insertCart(Cart cart) {
        cartRepository.save(buildCartDB(cart));
    }

    private Cart buildCart(CartDB cartDB, List<ProductDB> productsDB) {
        Cart cart = new Cart();
        List<ItemCart> itemsCart = new ArrayList<>();

        for (ItemCartDB itemCartDB : cartDB.getItems()) {
            Optional<ProductDB> oPDB = productsDB.stream().filter(productDB -> productDB.getId().toHexString().equals(itemCartDB.getProductId())).findFirst();
            ProductDB productDB = oPDB.get();
            Product product = buildProduct(productDB);
            itemsCart.add(new ItemCart(product, itemCartDB.getQuantity()));
        }

        cart.setSessionId(cartDB.getSessionId());
        cart.setItems(itemsCart);

        return cart;
    }

    private Product buildProduct(ProductDB productDB) {
        Product product = new Product();
        product.setId(productDB.getId().toHexString());
        product.setName(productDB.getName());
        product.setDescription(productDB.getDescription());
        product.setPrice(productDB.getPrice());

        return product;
    }

    private CartDB buildCartDB(Cart cart) {
        CartDB cartDB = new CartDB();
        cartDB.setSessionId(cart.getSessionId());
        List<ItemCartDB> itemsCart = cart.getItems().stream()
                .map(itemCart -> new ItemCartDB(itemCart.getProduct().getId(), itemCart.getQuantity()))
                .collect(Collectors.toUnmodifiableList());
        cartDB.setItems(itemsCart);

        return cartDB;
    }
}
