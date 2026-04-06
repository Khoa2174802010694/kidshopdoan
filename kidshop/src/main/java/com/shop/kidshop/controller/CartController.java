package com.shop.kidshop.controller;

import com.shop.kidshop.model.CartItem;
import com.shop.kidshop.model.Coupon;
import com.shop.kidshop.model.Order;
import com.shop.kidshop.model.User;
import com.shop.kidshop.service.CartService;
import com.shop.kidshop.service.CouponService;
import com.shop.kidshop.service.UserService;
import com.shop.kidshop.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/client/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService; // 🔥 thêm

    // 🔥 LẤY USER CHUẨN
    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userService.getByUsername(username); // cần có method này
    }

    // ================= VIEW CART =================
    @GetMapping("")
    public String viewCart(HttpSession session, Model model) {

        User user = getCurrentUser();

        List<CartItem> cartItems = cartService.getCart(user);
        int total = cartService.getTotalPrice(user);

        Double discount = (Double) session.getAttribute("discountAmount");
        Coupon currentCoupon = (Coupon) session.getAttribute("coupon");

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", total);
        model.addAttribute("discountAmount", discount != null ? discount : 0);
        model.addAttribute("coupons", couponService.getAll());
        model.addAttribute("currentCoupon", currentCoupon);

        return "client/cart";
    }

    // ================= ADD =================
    @GetMapping("/add/{id}")
    public String addToCart(@PathVariable Long id) {

        User user = getCurrentUser();
        cartService.addToCart(user, id);

        return "redirect:/client/cart";
    }

    // ================= UPDATE =================
    @GetMapping("/update")
    public String update(@RequestParam Long id,
                         @RequestParam String action) {

        User user = getCurrentUser();
        cartService.updateQuantity(user, id, action);

        return "redirect:/client/cart";
    }

    // ================= REMOVE =================
    @GetMapping("/remove/{id}")
    public String remove(@PathVariable Long id) {

        User user = getCurrentUser();
        cartService.removeItem(user, id);

        return "redirect:/client/cart";
    }

    // ================= APPLY COUPON =================
    @PostMapping("/apply-coupon")
    public String applyCoupon(@RequestParam String couponCode,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {

        User user = getCurrentUser();

        Coupon coupon = couponService.findByCode(couponCode);

        if (coupon == null) {
            redirectAttributes.addFlashAttribute("couponError", "Mã không tồn tại");
            return "redirect:/client/cart";
        }

        int total = cartService.getTotalPrice(user);
        double discount = couponService.calculateDiscount(total, coupon);

        session.setAttribute("coupon", coupon);
        session.setAttribute("discountAmount", discount);

        return "redirect:/client/cart";
    }

    // ================= CHECKOUT =================
    @PostMapping("/checkout/submit")
    public String checkout(@RequestParam String customerName,
                           @RequestParam String phone,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {

        User user = getCurrentUser();

        List<CartItem> cartItems = cartService.getCart(user);

        if (cartItems.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Giỏ hàng trống");
            return "redirect:/client/cart";
        }

        double total = cartService.getTotalPrice(user);

        Double discount = (Double) session.getAttribute("discountAmount");
        if (discount != null) total -= discount;

        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setCustomerName(customerName);
        order.setPhone(phone);
        order.setDate(LocalDateTime.now().toString());
        order.setStatus("PENDING");
        order.setTotal(total);

        orderRepository.save(order);

        cartItems.forEach(item ->
                cartService.removeItem(user, item.getProduct().getId())
        );

        session.removeAttribute("discountAmount");
        session.removeAttribute("coupon");

        redirectAttributes.addFlashAttribute("successMessage", "Đặt hàng thành công");

        return "redirect:/client/cart";
    }
}