package com.shop.kidshop.service;

import com.shop.kidshop.model.Product;
import java.util.List;

public interface ProductService {

    long count();

    List<Product> getAll();

    Product getById(Long id);

    void save(Product product);

    void delete(Long id);

    List<Product> findByCategory(Long categoryId);

    List<Product> getRelatedProducts(Long categoryId, Long currentProductId);
}