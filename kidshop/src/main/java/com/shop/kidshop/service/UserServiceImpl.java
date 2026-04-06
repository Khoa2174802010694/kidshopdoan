package com.shop.kidshop.service;

import com.shop.kidshop.model.User;
import com.shop.kidshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder; // 🔥 thêm

    @Override
    public User login(String username, String password) {
        // ⚠️ Sẽ KHÔNG dùng nữa khi có Spring Security
        User user = repo.findByUsername(username);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }

        return null;
    }

    @Override
    public User getByUsername(String username) {
        return repo.findByUsername(username);
    }
    @Override
    public boolean register(User user) {

        if (repo.findByUsername(user.getUsername()) != null) {
            return false;
        }

        user.setRole("USER");

        // 🔥 encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        repo.save(user);
        return true;
    }

    @Override
    public List<User> getAll() {
        return repo.findAll();
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public User getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void update(User user) {

        User oldUser = repo.findById(user.getId()).orElse(null);

        if (oldUser == null) return;

        // giữ password cũ nếu không nhập mới
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            user.setPassword(oldUser.getPassword());
        } else {
            // 🔥 encode nếu có password mới
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        repo.save(user);
    }
}