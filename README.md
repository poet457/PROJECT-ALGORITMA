# Study Buddy

Study Buddy adalah aplikasi berbasis web yang dirancang untuk membantu mahasiswa berbagi materi kuliah, berdiskusi melalui komentar, dan berkomunikasi dengan pengguna lain melalui fitur pesan.

## Fitur Utama

- Manajemen Pengguna
- Berbagi Materi Kuliah
- Komentar pada Materi
- Pesan Antar Pengguna
- Penyimpanan Data Menggunakan MySQL

---

# Teknologi yang Digunakan

- Java
- Spring Boot
- Spring Data JPA
- MySQL
- Gradle

---

# Cara Menjalankan Aplikasi

## 1. Clone Repository

```bash
git clone https://github.com/username/study-buddy.git
```

Masuk ke folder project:

```bash
cd study-buddy
```

---

## 2. Buat Database MySQL

Masuk ke MySQL lalu jalankan:

```sql
CREATE DATABASE study_buddy;
```

---

## 3. Konfigurasi Database

Buka file:

```text
src/main/resources/application.properties
```

Sesuaikan konfigurasi database:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/study_buddy
spring.datasource.username=root
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## 4. Jalankan Project

Windows:

```bash
gradlew.bat bootRun
```

Linux/MacOS:

```bash
./gradlew bootRun
```

Tunggu hingga muncul pesan:

```text
Started Application
```

---

## 5. Akses Aplikasi

Buka browser dan akses:

```text
http://localhost:8080
```

---

# Cara Penggunaan Aplikasi

## 1. Registrasi Pengguna

1. Buka halaman aplikasi.
2. Pilih menu Registrasi.
3. Isi data:
   - Nama
   - Email
   - Password
   - Program Studi
4. Klik tombol Daftar.

Sistem akan menyimpan data pengguna ke database.

---

## 2. Login

1. Masukkan email dan password.
2. Klik Login.
3. Jika data valid, pengguna akan masuk ke halaman utama aplikasi.

---

## 3. Melihat Materi

1. Setelah login, pengguna dapat melihat daftar materi yang tersedia.
2. Pilih salah satu materi untuk melihat detailnya.

---

## 4. Mengunggah Materi

1. Pilih menu Upload Materi.
2. Isi informasi materi:
   - Judul
   - Mata Kuliah
   - Deskripsi
3. Upload file jika tersedia.
4. Klik Simpan atau Upload.

Materi akan ditampilkan pada halaman utama.

---

## 5. Memberikan Komentar

1. Buka detail materi.
2. Masukkan komentar pada kolom yang tersedia.
3. Klik Kirim.

Komentar akan ditampilkan pada halaman materi.

---

## 6. Mengirim Pesan

1. Pilih pengguna yang ingin dihubungi.
2. Ketik isi pesan.
3. Klik Kirim.

Pesan akan tersimpan dan dapat dibaca oleh penerima.

---

## 7. Logout

1. Klik tombol Logout.
2. Sistem akan mengakhiri sesi pengguna.
3. Pengguna akan kembali ke halaman login.

---

