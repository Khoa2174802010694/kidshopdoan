package com.shop.kidshop.service;

import com.shop.kidshop.model.CartItem;
import com.shop.kidshop.model.User;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface CartService {

    List<CartItem> getCart(User user);

    void addToCart(User user, Long productId);

    void updateQuantity(User user, Long productId, String action);

    void removeItem(User user, Long productId);

    int getTotalPrice(User user);
}
