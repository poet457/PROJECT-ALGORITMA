package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String isiKomentar;

    // Relasi: Banyak komentar bisa ditulis oleh satu User (Mahasiswa)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Relasi: Banyak komentar bisa berada di dalam satu Post (Materi)
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    // --- GETTER AND SETTER ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIsiKomentar() { return isiKomentar; }
    public void setIsiKomentar(String isiKomentar) { this.isiKomentar = isiKomentar; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Post getPost() { return post; }
    public void setPost(Post post) { this.post = post; }
}