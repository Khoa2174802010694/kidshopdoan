package com.shop.kidshop.controller;

import com.shop.kidshop.model.Product;
import com.shop.kidshop.model.Category;
import com.shop.kidshop.service.ProductService;
import com.shop.kidshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Controller
@RequestMapping("/admin/product")
public class AdminProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    // ================= LIST =================
    @GetMapping
    public String index(Model model) {

        List<Product> products = productService.getAll();
        model.addAttribute("products", products);
        model.addAttribute("product", new Product()); // 👈 thiếu cái này
        model.addAttribute("categories", categoryService.getAll()); // 👈 thiếu cái này
        return "admin/product";
    }

    // ================= CREATE =================
    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAll());

        return "admin/product_form";
    }

    // ================= SAVE =================
    @PostMapping("/save")
    public String save(
            @ModelAttribute Product product,
            @RequestParam("imageFile") MultipartFile file,
            @RequestParam("categoryId") Long categoryId
    ) throws Exception {

        Category category = categoryService.getById(categoryId);
        product.setCategory(category);

        if (!file.isEmpty()) {

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            // 👉 lấy đường dẫn thật của project
            String uploadDir = System.getProperty("user.dir") + "/uploads/";

            File uploadPath = new File(uploadDir);

            if (!uploadPath.exists()) {
                uploadPath.mkdirs(); // 👈 đảm bảo folder tồn tại
            }

            file.transferTo(new File(uploadDir + fileName));

            product.setImage(fileName);
        }

        productService.save(product);

        return "redirect:/admin/product";
    }

    // ================= EDIT =================
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {

        Product product = productService.getById(id);

        model.addAttribute("product", product);
        model.addAttribute("products", productService.getAll());
        model.addAttribute("categories", categoryService.getAll());

        return "admin/product"; // 👈 đổi ở đây
    }

    // ================= DELETE =================
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        productService.delete(id);

        return "redirect:/admin/product";
    }
}