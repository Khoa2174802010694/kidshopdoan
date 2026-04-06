package com.shop.kidshop.service;

import com.shop.kidshop.model.CartItem;
import com.shop.kidshop.model.Product;
import com.shop.kidshop.model.User;
import com.shop.kidshop.repository.CartItemRepository;
import com.shop.kidshop.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartItemRepository cartRepo;

    @Autowired
    private ProductRepository productRepo;

    @Override
    public List<CartItem> getCart(User user) {
        return cartRepo.findByUser(user);
    }

    @Override
    public void addToCart(User user, Long productId) {
        Product product = productRepo.findById(productId).orElseThrow();

        Optional<CartItem> existing = cartRepo.findByUserAndProduct(user, product);

        if (existing.isPresent()) {
            CartItem item = existing.get();
            item.setQuantity(item.getQuantity() + 1);
            cartRepo.save(item);
        } else {
            CartItem item = new CartItem();
            item.setUser(user);
            item.setProduct(product);
            item.setQuantity(1);
            cartRepo.save(item);
        }
        System.out.println("ADDING PRODUCT ID: " + productId);
    }

    @Override
    public void updateQuantity(User user, Long productId, String action) {
        Product product = productRepo.findById(productId).orElseThrow();
        CartItem item = cartRepo.findByUserAndProduct(user, product).orElseThrow();

        if (action.equals("increase")) {
            item.setQuantity(item.getQuantity() + 1);
        } else {
            item.setQuantity(item.getQuantity() - 1);
            if (item.getQuantity() <= 0) {
                cartRepo.delete(item);
                return;
            }
        }

        cartRepo.save(item);
    }

    @Transactional
    @Override
    public void removeItem(User user, Long productId) {
        Product product = productRepo.findById(productId).orElseThrow();
        cartRepo.deleteByUserAndProduct(user, product);
    }

    @Override
    public int getTotalPrice(User user) {
        return cartRepo.findByUser(user).stream()
                .mapToInt(item -> (int)(item.getProduct().getPrice() * item.getQuantity()))
                .sum();
    }
}
