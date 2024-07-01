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

    public Cart(String sessionId) {
        this.sessionId = sessionId;
    }

    public void addProduct(Product product, int quantity) {
        Integer idx = null;
        for (int i = 0; i < items.size(); i++) {
            if(items.get(i).getProduct().getId() == product.getId()) {
                idx = i;
                break;
            }
        }
        if (idx != null) {
            items.get(idx).setQuantity(items.get(idx).getQuantity() + quantity);
        } else {
            this.items.add(new ItemCart(product, quantity));
        }
    }
}
