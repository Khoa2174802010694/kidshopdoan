package com.shop.kidshop.service;

import com.shop.kidshop.model.User;
import java.util.List;

public interface UserService {

    User login(String username, String password);

    boolean register(User user);

    List<User> getAll();

    void delete(Long id);

    User getById(Long id);              // 👈 thêm

    void update(User user);

    User getByUsername(String username);// 👈 thêm
}