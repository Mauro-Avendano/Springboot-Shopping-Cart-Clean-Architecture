package com.example.shoppingcartcleanarchitecture.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Cart {
    private String id;
    private String sessionId;
    private List<ItemCart> items;

    public Cart(String sessionId, List<ItemCart> items) {
        this.sessionId = sessionId;
        this.items = items;
    }

    public void addProduct(Product product, int quantity) {
        items.forEach(itemCart -> {
            boolean found = false;
            if (itemCart.getProduct().getId() == product.getId()) {
                itemCart.setQuantity(itemCart.getQuantity() + quantity);
                found = true;
            }
            if (!found) {
                items.add(new ItemCart(product, quantity));
            }
        });
    }
}
