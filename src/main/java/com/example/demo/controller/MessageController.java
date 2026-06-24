package com.example.demo.controller;

import com.example.demo.model.Message;
import com.example.demo.model.User;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    // Mengirim pesan pribadi baru
    @PostMapping
    public Message sendMessage(@RequestBody Message message) {
        if (message.getPengirim() == null || message.getPengirim().getId() == null ||
            message.getPenerima() == null || message.getPenerima().getId() == null) {
            throw new RuntimeException("Data pengirim dan penerima harus lengkap!");
        }

        // Cegah error 500: Cari data User asli di database
        User pengirimDb = userRepository.findById(message.getPengirim().getId())
                .orElseThrow(() -> new RuntimeException("Pengirim tidak ditemukan!"));
        User penerimaDb = userRepository.findById(message.getPenerima().getId())
                .orElseThrow(() -> new RuntimeException("Penerima tidak ditemukan!"));

        message.setPengirim(pengirimDb);
        message.setPenerima(penerimaDb);
        return messageRepository.save(message);
    }

    // Mengambil seluruh riwayat percakapan antara 2 user (dipakai untuk polling chat).
    // Sekaligus menandai SUDAH DIBACA semua pesan yang dikirim userIdB ke userIdA,
    // karena memanggil endpoint ini artinya userIdA sedang membuka/melihat percakapan tersebut.
    @GetMapping("/conversation/{userIdA}/{userIdB}")
    public List<Message> getConversation(@PathVariable Long userIdA, @PathVariable Long userIdB) {
        List<Message> pesanList = messageRepository.findByPengirimIdAndPenerimaIdOrPenerimaIdAndPengirimIdOrderByWaktuKirimAsc(
                userIdA, userIdB, userIdA, userIdB);

        List<Message> pesanBelumDibaca = messageRepository.findByPengirimIdAndPenerimaIdAndDibacaFalse(userIdB, userIdA);
        if (!pesanBelumDibaca.isEmpty()) {
            for (Message pesan : pesanBelumDibaca) {
                pesan.setDibaca(true);
            }
            messageRepository.saveAll(pesanBelumDibaca);
        }

        return pesanList;
    }

    // Mengambil daftar percakapan (inbox) milik seorang user: satu baris per lawan chat,
    // berisi pesan terakhir & jumlah pesan yang belum dibaca dari lawan chat itu.
    @GetMapping("/inbox/{userId}")
    public List<Map<String, Object>> getInbox(@PathVariable Long userId) {
        List<Message> semuaPesan = messageRepository.findByPengirimIdOrPenerimaIdOrderByWaktuKirimDesc(userId, userId);

        // Karena semuaPesan sudah terurut dari yang terbaru, ambil kemunculan PERTAMA per lawan chat
        // sebagai "pesan terakhir" pada percakapan itu.
        Map<Long, Message> pesanTerakhirPerLawan = new LinkedHashMap<>();
        for (Message pesan : semuaPesan) {
            Long lawanId = pesan.getPengirim().getId().equals(userId)
                    ? pesan.getPenerima().getId()
                    : pesan.getPengirim().getId();
            pesanTerakhirPerLawan.putIfAbsent(lawanId, pesan);
        }

        List<Map<String, Object>> hasil = new ArrayList<>();
        for (Map.Entry<Long, Message> entry : pesanTerakhirPerLawan.entrySet()) {
            Long lawanId = entry.getKey();
            Message terakhir = entry.getValue();
            User lawan = terakhir.getPengirim().getId().equals(lawanId) ? terakhir.getPengirim() : terakhir.getPenerima();

            long belumDibaca = messageRepository.countByPengirimIdAndPenerimaIdAndDibacaFalse(lawanId, userId);

            Map<String, Object> item = new HashMap<>();
            item.put("userId", lawanId);
            item.put("nama", lawan.getNama());
            item.put("pesanTerakhir", terakhir.getIsiPesan());
            item.put("waktuTerakhir", terakhir.getWaktuKirim());
            item.put("belumDibaca", belumDibaca);
            hasil.add(item);
        }

        return hasil;
    }

    // Total seluruh pesan belum dibaca milik seorang user (dipakai untuk badge notifikasi di sidebar)
    @GetMapping("/unread-count/{userId}")
    public Map<String, Long> getUnreadCount(@PathVariable Long userId) {
        long total = messageRepository.countByPenerimaIdAndDibacaFalse(userId);
        Map<String, Long> hasil = new HashMap<>();
        hasil.put("total", total);
        return hasil;
    }
}