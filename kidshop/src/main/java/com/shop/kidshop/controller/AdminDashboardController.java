package com.shop.kidshop.controller;

import com.shop.kidshop.model.Order;
import com.shop.kidshop.repository.OrderRepository;
import com.shop.kidshop.repository.ProductRepository;
import com.shop.kidshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("")
    public String dashboard(Model model) {

        // tổng sản phẩm
        long totalProducts = productRepository.count();

        // tổng đơn hàng
        long totalOrders = orderRepository.count();

        // doanh thu
        double revenue = orderRepository.findAll()
                .stream()
                .filter(o -> "APPROVED".equals(o.getStatus()))
                .mapToDouble(Order::getTotal)
                .sum();

        // đơn hàng mới nhất (top 5)
        List<Order> recentOrders = orderRepository.findAll(
                Sort.by(Sort.Direction.DESC, "date")
        ).stream().limit(5).toList();

        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("revenue", revenue);
        model.addAttribute("recentOrders", recentOrders);

        return "admin/dashboard";
    }
}