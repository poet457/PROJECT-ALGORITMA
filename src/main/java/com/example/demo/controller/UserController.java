package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Menampilkan daftar mahasiswa
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Mendaftarkan mahasiswa baru (register): menerima nama, prodi, email, password
    @PostMapping
    public User createUser(@RequestBody User user) {
        // Cegah pendaftaran ganda dengan email yang sama
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email sudah terdaftar! Silakan masuk.");
        }
        return userRepository.save(user);
    }

    // Login menggunakan email & password
    @PostMapping("/login")
    public User login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        User userDb = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email tidak ditemukan!"));

        if (!userDb.getPassword().equals(password)) {
            throw new RuntimeException("Password salah!");
        }

        return userDb;
    }
}