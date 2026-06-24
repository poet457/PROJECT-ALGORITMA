package com.example.demo.repository;

import com.example.demo.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    // Ambil semua pesan antara 2 user (dua arah: A->B dan B->A), diurutkan dari yang paling lama
    List<Message> findByPengirimIdAndPenerimaIdOrPenerimaIdAndPengirimIdOrderByWaktuKirimAsc(
            Long pengirimId, Long penerimaId, Long penerimaId2, Long pengirimId2);

    // Ambil semua pesan yang melibatkan satu user (sebagai pengirim ATAU penerima), terbaru duluan.
    // Dipakai untuk menyusun daftar percakapan (inbox) di menu "Pesan".
    List<Message> findByPengirimIdOrPenerimaIdOrderByWaktuKirimDesc(Long pengirimId, Long penerimaId);

    // Hitung total pesan masuk yang belum dibaca oleh seorang user (untuk badge notifikasi di sidebar)
    long countByPenerimaIdAndDibacaFalse(Long penerimaId);

    // Hitung pesan belum dibaca dari satu lawan chat tertentu (untuk badge per-percakapan di inbox)
    long countByPengirimIdAndPenerimaIdAndDibacaFalse(Long pengirimId, Long penerimaId);

    // Ambil pesan-pesan yang belum dibaca dari satu lawan chat (dipakai untuk menandai "sudah dibaca" saat chat dibuka)
    List<Message> findByPengirimIdAndPenerimaIdAndDibacaFalse(Long pengirimId, Long penerimaId);
}