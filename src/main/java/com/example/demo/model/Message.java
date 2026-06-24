package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String isiPesan;

    private LocalDateTime waktuKirim;

    // Status apakah pesan ini sudah dibaca oleh penerima. Default false (belum dibaca) saat pesan baru dikirim.
    private boolean dibaca = false;

    // Relasi: User yang mengirim pesan
    @ManyToOne
    @JoinColumn(name = "pengirim_id", nullable = false)
    private User pengirim;

    // Relasi: User yang menerima pesan
    @ManyToOne
    @JoinColumn(name = "penerima_id", nullable = false)
    private User penerima;

    // Otomatis isi waktu kirim saat data pertama kali disimpan
    @PrePersist
    protected void onCreate() {
        this.waktuKirim = LocalDateTime.now();
    }

    // --- GETTER AND SETTER ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIsiPesan() { return isiPesan; }
    public void setIsiPesan(String isiPesan) { this.isiPesan = isiPesan; }

    public LocalDateTime getWaktuKirim() { return waktuKirim; }
    public void setWaktuKirim(LocalDateTime waktuKirim) { this.waktuKirim = waktuKirim; }

    public boolean isDibaca() { return dibaca; }
    public void setDibaca(boolean dibaca) { this.dibaca = dibaca; }

    public User getPengirim() { return pengirim; }
    public void setPengirim(User pengirim) { this.pengirim = pengirim; }

    public User getPenerima() { return penerima; }
    public void setPenerima(User penerima) { this.penerima = penerima; }
}