package com.shop.kidshop.controller;

import com.shop.kidshop.model.User;
import com.shop.kidshop.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // ================= LOGIN PAGE =================
    @GetMapping("/login")
    public String loginPage() {
        return "client/login";
    }

    // ❌ XÓA login POST (Spring Security xử lý)
    // ================= REGISTER PAGE =================
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "client/register";
    }

    // ================= REGISTER =================
    @PostMapping("/register")
    public String register(
            @ModelAttribute User user,
            Model model
    ) {

        boolean success = userService.register(user);

        if (!success) {
            model.addAttribute("error", "Username đã tồn tại");
            return "client/register";
        }

        return "redirect:/auth/login";
    }

    // ❌ Logout cũng để Spring xử lý
}