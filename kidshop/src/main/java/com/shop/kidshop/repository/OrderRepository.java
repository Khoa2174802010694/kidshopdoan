package com.shop.kidshop.repository;

import com.shop.kidshop.model.Order;
import com.shop.kidshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByUser(User user);
}