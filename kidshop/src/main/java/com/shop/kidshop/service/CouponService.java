package com.shop.kidshop.service;

import com.shop.kidshop.model.Coupon;
import com.shop.kidshop.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CouponService {

    Coupon findByCode(String code);

    double calculateDiscount(double total, Coupon coupon);

    List<Coupon> getAll();

    Coupon getById(String id);

    void save(Coupon coupon);

    void delete(String id);
}