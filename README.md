# StudyGate

<p align="center">
  <img src="https://img.icons8.com/fluency/96/student-center.png" alt="StudyGate Logo" width="120" />
  <br>
  <h2 align="center">Sistem Belajar Mandiri Terintegrasi</h2>
  <p align="center">
    Aplikasi mobile berbasis Android untuk memfasilitasi pembelajaran mahasiswa dengan fitur repositori materi, forum diskusi, dan manajemen konten yang efisien.
  </p>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" />
  <img src="https://img.shields.io/badge/Language-Java-ED8B00?style=for-the-badge&logo=java&logoColor=white" />
  <img src="https://img.shields.io/badge/Backend-Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black" />
  <img src="https://img.shields.io/badge/Storage-Cloudinary-3448C5?style=for-the-badge&logo=cloudinary&logoColor=white" />
  <img src="https://img.shields.io/badge/Architecture-MVC-blue?style=for-the-badge" />
</p>

---

## 👥 Tim Pengembang

| No | Nama Anggota | NIM | Peran Utama |
| :---: | :--- | :--- | :--- |
| 1. | **Dinar Fauziah** | `F52123078` | Firebase Specialist |
| 2. | **Uswatun Hasanah H. N.** | `F52123093` | Project Manager |
| 3. | **Muh. Yusuf Mansur** | `F52123095` | UI/UX & Frontend |
| 4. | **Muh. Avif Arya Manggona** | `F52123073` | Programmer MVC & Testing |

> *Kelompok: Pemrograman Mobile - Semester 5*

---

## 📱 Fitur & Fungsionalitas

Aplikasi ini menerapkan **Role-Based Access Control (RBAC)**, memisahkan fitur antara Mahasiswa dan Admin.

### 🔐 Autentikasi (Security)
* **Dual Login System:**
    * 📧 Email & Password (dengan verifikasi email).
    * 🌐 **Google Sign-In** (One-Tap Login).
* **Auto Role Detection:** Sistem otomatis mengarahkan user ke *Dashboard Mahasiswa* atau *Admin Panel* berdasarkan role di database.
* **Session Management:** Logout aman dengan pembersihan sesi Google & Firebase.

### 🎓 Role: Mahasiswa (User)
| Fitur | Deskripsi |
| :--- | :--- |
| **Dashboard** | Melihat materi terbaru (*Recent*) dan melanjutkan materi terakhir (*Resume*). |
| **Resource Browser** | Mencari materi kuliah dengan **Smart Search** (Judul/MK) dan filter **Chip Prodi**. |
| **Materi Interaktif** | Membuka PDF, YouTube (Embed), dan Website langsung di dalam aplikasi (*In-App WebView*). |
| **Forum Diskusi** | Bertanya & menjawab diskusi. Upload foto pertanyaan, tag Mata Kuliah, serta fitur **Edit/Hapus** komentar sendiri. |
| **Notifikasi** | Menerima notifikasi saat ada balasan di forum diskusi. |
| **Profil Akun** | Mengelola foto profil (Cloudinary) dan data diri. |

### 🛠 Role: Administrator (Admin)
| Fitur | Deskripsi |
| :--- | :--- |
| **Admin Dashboard** | Panel kontrol utama dengan statistik ringkas. |
| **Manajemen Resource** | **CRUD (Create, Read, Update, Delete)** materi kuliah. Input data lengkap (Judul, URL, Tipe, Prodi, MK). |
| **Manajemen User** | Memantau daftar pengguna terdaftar (Read-Only untuk keamanan). |
| **Master Data** | Mengelola data referensi Program Studi (Prodi) dan Mata Kuliah (MK). |

---

## 📸 Tampilan Aplikasi (Screenshots)

<p align="center">
  <strong>— Authentication & Dashboard —</strong>
</p>
<p align="center">
  <img src="screenshots/login.png" width="200" />
  <img src="screenshots/home.png" width="200" />
  <img src="screenshots/search.png" width="200" />
  <img src="screenshots/account.png" width="200" />
</p>

<p align="center">
  <strong>— Forum Diskusi & Interaksi —</strong>
</p>
<p align="center">
  <img src="screenshots/forum.png" width="200" />
  <img src="screenshots/detail_forum.png" width="200" />
  <img src="screenshots/add_post.png" width="200" />
  <img src="screenshots/notification.png" width="200" />
</p>

<p align="center">
  <strong>— Admin Panel (CRUD) —</strong>
</p>
<p align="center">
  <img src="screenshots/admin.png" width="200" />
  <img src="screenshots/crud.png" width="200" />
  <img src="screenshots/users.png" width="200" />
  <img src="screenshots/manage_mk.png" width="200" />
</p>

---

## 🛠️ Tech Stack & Libraries

Project ini dibangun dengan standar industri menggunakan library modern:

### Core
* **Language:** Java
* **Minimum SDK:** API 24 (Android 7.0 Nougat)
* **Architecture:** Model-View-Controller (MVC)

### Backend & Cloud
* 🔥 **Firebase Authentication:** Manajemen user yang aman.
* 🔥 **Cloud Firestore:** Database NoSQL realtime yang cepat.
* ☁️ **Cloudinary Android SDK:** Penyimpanan & optimasi gambar (Foto Profil & Forum).
* 🌐 **Google Play Services Auth:** Integrasi login akun Google.

### UI & UX Libraries
* 🎨 **Material Design 3:** Komponen UI terbaru (Cards, Chips, InputLayout).
* 🖼️ **Glide:** Library loading gambar yang cepat dan efisien.
* 🔄 **Lottie:** (Opsional) Untuk animasi loading yang menarik.
* ⭕ **CircleImageView:** (Atau ShapeableImageView) untuk foto profil bulat.

---

## 🎨 Color Palette

Menggunakan tema **Teal Green** yang melambangkan ketenangan, pertumbuhan, dan akademik.

| Warna | Hex Code | Penggunaan |
| :--- | :--- | :--- |
| **Primary** | ` #009688 ` | Header, Tombol Utama, Ikon Aktif |
| **On Primary** | ` #FFFFFF ` | Teks pada tombol/header |
| **Background** | ` #F5F5F5 ` | Latar belakang layar (Surface) |
| **Surface** | ` #FFFFFF ` | Kartu (CardView), Dialog |
| **Error** | ` #B00020 ` | Pesan error, Tombol Hapus |

---

## ⚙️ Setup & Instalasi

Untuk menjalankan project ini di komputer Anda:

1.  **Clone Repository:**
    ```bash
    git clone [https://github.com/username/StudyGate.git](https://github.com/username/StudyGate.git)
    ```
2.  **Buka di Android Studio:** Gunakan versi terbaru (Ladybug / Koala).
3.  **Konfigurasi Firebase:**
    * Buat project di [Firebase Console](https://console.firebase.google.com/).
    * Download file `google-services.json`.
    * Letakkan file tersebut di dalam folder `app/`.
4.  **Konfigurasi Cloudinary:**
    * Daftar di [Cloudinary](https://cloudinary.com/).
    * Masukkan `CLOUD_NAME`, `API_KEY`, dan `API_SECRET` di file `MyApplication.java`.
5.  **Sync & Run:** Jalankan sinkronisasi Gradle, lalu Run di Emulator/Device.

---

<p align="center">
  Dibuat dengan ❤️ oleh Kelompok StudyGate <br>
  Copyright © 2025
</p>
