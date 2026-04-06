package com.shop.kidshop.controller;

import com.shop.kidshop.model.Order;
import com.shop.kidshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    @Autowired
    private OrderRepository orderRepository;

    // ================= VIEW ORDERS =================
    @GetMapping("")
    public String list(Model model) {

        // Lấy tất cả đơn hàng, mới nhất lên đầu
        List<Order> orders = orderRepository.findAll(
                Sort.by(Sort.Direction.DESC, "date")
        );

        model.addAttribute("orders", orders);

        return "admin/orders";
    }

    // ================= UPDATE STATUS =================
    @GetMapping("/update")
    public String updateStatus(
            @RequestParam("id") String id,
            @RequestParam("status") String status
    ) {

        Order order = orderRepository.findById(id).orElse(null);

        if (order != null) {
            order.setStatus(status);
            orderRepository.save(order);
        }

        return "redirect:/admin/orders";
    }
}