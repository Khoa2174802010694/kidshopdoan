package com.shop.kidshop.service;

import com.shop.kidshop.model.Category;
import java.util.List;

public interface CategoryService {

    List<Category> getAll();

    Category getById(Long id);

    void save(Category category);

    void delete(Long id);
}