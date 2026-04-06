package com.shop.kidshop.service;

import com.shop.kidshop.model.Order;
import com.shop.kidshop.repository.OrderRepository;
import com.shop.kidshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }
}