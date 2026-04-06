package com.shop.kidshop.service;

import com.shop.kidshop.model.Product;
import com.shop.kidshop.repository.ProductRepository;
import com.shop.kidshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repo;

    @Override
    public long count() {
        return repo.count();
    }

    @Override
    public List<Product> getAll() {
        return repo.findAll();
    }

    @Override
    public Product getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void save(Product product) {
        repo.save(product);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<Product> findByCategory(Long categoryId) {
        return repo.findByCategory_Id(categoryId);
    }

    // ✨ THÊM CÁI NÀY
    @Override
    public List<Product> getRelatedProducts(Long categoryId, Long currentProductId) {
        return repo.findTop8ByCategory_IdAndIdNot(categoryId, currentProductId);
    }
}