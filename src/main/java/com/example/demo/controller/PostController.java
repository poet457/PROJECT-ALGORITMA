package com.example.demo.controller;

import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        // Penting: Pastikan user ditemukan di database berdasarkan ID
        if (post.getUser() != null && post.getUser().getId() != null) {
            User userDb = userRepository.findById(post.getUser().getId())
                            .orElseThrow(() -> new RuntimeException("User tidak ditemukan di DB"));
            post.setUser(userDb); // Hubungkan objek post dengan objek user yang asli dari DB
        } else {
            throw new RuntimeException("User harus dipilih!");
        }
        return postRepository.save(post);
    }
}