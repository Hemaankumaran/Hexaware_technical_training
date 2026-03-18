package com.service;

import com.model.CartItem;
import com.model.User;
import com.repository.CartRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private final CartRepo cartRepo;

    public CartService(CartRepo cartRepo){ // constructor injection
        this.cartRepo = cartRepo;
    }

    public void addUser(User user) {
        cartRepo.addUser(user);
    }

    public void addItem(CartItem cartItem) {
        cartRepo.addItem(cartItem);
    }

    public List<CartItem> fetchAllItems() {
        return cartRepo.fetchAllItems();
    }

    public List<CartItem> fetchByUserName(String name) {
        return cartRepo.fetchByUserName(name);
    }

    public void deleteById(int id) {
        cartRepo.deleteById(id);
    }
}
