package com.shop.kidshop.controller;

import ch.qos.logback.core.model.Model;
import com.shop.kidshop.model.Category;
import com.shop.kidshop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/category")
public class AdminCategoryController {

    @GetMapping("")
    public String index(Model model) {
        return "admin/category";
    }


    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/save")
    public String save(@RequestParam String name) {

        Category c = new Category();
        c.setName(name);

        categoryRepository.save(c);

        return "redirect:/admin/product";
    }
}
