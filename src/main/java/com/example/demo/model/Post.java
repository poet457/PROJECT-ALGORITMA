package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String judul;
    private String mataKuliah; // Contoh: Algoritma 2, Basis Data, dll.
    
    @Column(columnDefinition = "TEXT")
    private String deskripsi; // Isi materi atau penjelasan singkat
    
    private String fileUrl; // Link/URL file materi jika ada

    // Relasi: Banyak postingan materi bisa dibuat oleh satu User (Mahasiswa)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // --- GETTER AND SETTER ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getJudul() { return judul; }
    public void setJudul(String judul) { this.judul = judul; }

    public String getMataKuliah() { return mataKuliah; }
    public void setMataKuliah(String mataKuliah) { this.mataKuliah = mataKuliah; }

    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }

    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}