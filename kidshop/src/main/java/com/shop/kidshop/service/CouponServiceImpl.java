package com.shop.kidshop.service;

import com.shop.kidshop.model.Coupon;
import com.shop.kidshop.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Override
    public Coupon findByCode(String code) {
        return couponRepository.findByCode(code).orElse(null);
    }

    @Override
    public double calculateDiscount(double total, Coupon coupon) {
        if (coupon == null) return 0;

        double percent = coupon.getDiscount();

        // 🔥 chống dữ liệu lỗi
        if (percent < 0) percent = 0;
        if (percent > 100) percent = 100;

        return total * percent / 100.0;
    }

    @Override
    public List<Coupon> getAll() {
        return couponRepository.findAll();
    }

    @Override
    public Coupon getById(String id) {
        return couponRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Override
    public void delete(String id) {
        couponRepository.deleteById(id);
    }

}
