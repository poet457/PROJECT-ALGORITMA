package com.example.demo.controller;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    // Menambahkan komentar baru
    @PostMapping
    public Comment createComment(@RequestBody Comment comment) {
        // Cegah error 500: Cari data User dan Post asli di database
        if (comment.getUser() != null && comment.getUser().getId() != null &&
            comment.getPost() != null && comment.getPost().getId() != null) {

            User userDb = userRepository.findById(comment.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User tidak ditemukan!"));
            Post postDb = postRepository.findById(comment.getPost().getId())
                    .orElseThrow(() -> new RuntimeException("Materi tidak ditemukan!"));

            // Sambungkan komentar dengan User dan Post yang ada di database
            comment.setUser(userDb);
            comment.setPost(postDb);
            return commentRepository.save(comment);
        } else {
            throw new RuntimeException("Data User dan Post harus lengkap!");
        }
    }

    // Mengambil semua komentar di satu materi
    @GetMapping("/post/{postId}")
    public List<Comment> getCommentsByPost(@PathVariable Long postId) {
        return commentRepository.findByPostId(postId);
    }

    // 3. FITUR EDIT: Mengubah isi komentar berdasarkan ID Comment
    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody Comment updatedComment) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Komentar tidak ditemukan!"));
        
        // Ubah isi komentarnya dengan teks yang baru dimasukkan
        comment.setIsiKomentar(updatedComment.getIsiKomentar());
        
        return commentRepository.save(comment);
    }

    // 4. FITUR HAPUS: Menghapus komentar dari database berdasarkan ID
    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable Long id) {
        if (!commentRepository.existsById(id)) {
            throw new RuntimeException("Komentar tidak ditemukan!");
        }
        commentRepository.deleteById(id);
        return "Komentar berhasil dihapus";
    }
}