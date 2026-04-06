package com.shop.kidshop.controller;

import com.shop.kidshop.model.Coupon;
import com.shop.kidshop.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/admin/coupons")
public class AdminCouponController {

    @Autowired
    private CouponService couponService;

    // VIEW PAGE
    @GetMapping
    public String coupons(Model model) {
        model.addAttribute("coupon", new Coupon());
        model.addAttribute("coupons", couponService.getAll());
        return "admin/coupons";
    }

    // SAVE
    @PostMapping("/save")
    public String save(@ModelAttribute Coupon coupon) {

        // generate ID nếu chưa có
        if (coupon.getId() == null || coupon.getId().isEmpty()) {
            coupon.setId(UUID.randomUUID().toString());
        }

        couponService.save(coupon);
        return "redirect:/admin/coupons";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        couponService.delete(id);
        return "redirect:/admin/coupons";
    }
}