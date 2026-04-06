package com.shop.kidshop.controller;

import com.shop.kidshop.model.Order;
import com.shop.kidshop.model.User;
import com.shop.kidshop.repository.OrderRepository;
import com.shop.kidshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/customer")
public class AdminCustomerController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderRepository orderRepository;

    // ================= LIST USERS =================
    @GetMapping("")
    public String list(Model model) {

        List<User> users = userService.getAll();

        model.addAttribute("users", users);

        return "admin/customer";
    }

    // ================= VIEW USER DETAIL =================
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {

        User user = userService.getById(id);

        List<Order> orders = orderRepository.findByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("orders", orders);

        return "admin/customer_detail";
    }

    // ================= EDIT FORM =================
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {

        User user = userService.getById(id);

        model.addAttribute("user", user);

        return "admin/customer_edit"; // 👈 file mới
    }

    // ================= UPDATE =================
    @PostMapping("/update")
    public String update(@ModelAttribute User user) {

        userService.update(user);

        return "redirect:/admin/customer";
    }

    // ================= DELETE =================
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        userService.delete(id);

        return "redirect:/admin/customer";
    }
}