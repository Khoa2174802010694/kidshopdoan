package com.shop.kidshop.repository;

import com.shop.kidshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory_Id(Long categoryId);
    List<Product> findTop8ByCategory_IdAndIdNot(Long categoryId, Long id);
}