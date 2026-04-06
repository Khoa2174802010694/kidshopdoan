package com.shop.kidshop.controller;

import com.shop.kidshop.model.Product;
import com.shop.kidshop.service.ProductService;
import com.shop.kidshop.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/index")
    public String index(
            @RequestParam(required = false) Long categoryId,
            Model model
    ) {

        List<Product> products;

        if (categoryId != null) {
            products = productService.findByCategory(categoryId);
        } else {
            products = productService.getAll();
        }

        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getAll());

        return "client/index";
    }

    @GetMapping("/product/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        Product product = productService.getById(id);

        // 🚨 tránh null crash
        List<Product> relatedProducts = null;
        if (product != null && product.getCategory() != null) {
            relatedProducts = productService.getRelatedProducts(
                    product.getCategory().getId(),
                    id
            );
        }

        model.addAttribute("product", product);
        model.addAttribute("relatedProducts", relatedProducts);
        model.addAttribute("categories", categoryService.getAll());

        return "client/detail";
    }



}