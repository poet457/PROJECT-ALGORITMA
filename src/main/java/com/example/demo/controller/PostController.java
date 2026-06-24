package com.example.demo.controller;

import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    // Mengubah format penerimaan menjadi form-data (bukan JSON)
    @PostMapping
    public Post createPost(
            @RequestParam("userId") Long userId,
            @RequestParam("judul") String judul,
            @RequestParam("mataKuliah") String mataKuliah,
            @RequestParam("deskripsi") String deskripsi,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        // 1. Cari User
        User userDb = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan di DB"));

        // 2. Susun Data Post
        Post post = new Post();
        post.setUser(userDb);
        post.setJudul(judul);
        post.setMataKuliah(mataKuliah);
        post.setDeskripsi(deskripsi);

        // 3. Proses File (Jika ada file yang diunggah)
        if (file != null && !file.isEmpty()) {
            try {
                // Buat folder "uploads" jika belum ada
                String uploadDir = "uploads/";
                Files.createDirectories(Paths.get(uploadDir));

                // Tambahkan waktu di nama file agar tidak ada file bernama sama (duplikat)
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(uploadDir + fileName);
                
                // Simpan file ke laptopmu
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // Simpan URL file ke database
                post.setFileUrl("/uploads/" + fileName);
            } catch (Exception e) {
                throw new RuntimeException("Gagal menyimpan file: " + e.getMessage());
            }
        }

        return postRepository.save(post);
    }
}