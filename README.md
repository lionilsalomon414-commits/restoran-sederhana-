# 🍽️ Aplikasi Restoran Sederhana (Java Console)

Aplikasi restoran berbasis konsol yang dibuat dengan **Java**. Pelanggan dapat melihat menu, memesan makanan dan minuman, lalu mencetak struk lengkap dengan diskon, pajak, biaya pelayanan, dan promo. Pemilik restoran dapat menambah menu, mengubah harga, dan menghapus menu.

Proyek ini dibuat sebagai tugas praktik mata kuliah pemrograman untuk menerapkan **array, percabangan, perulangan, method, kelas dan objek, serta validasi input**.

## ✨ Fitur

### Menu Pelanggan
- Menampilkan daftar menu yang dikelompokkan menjadi **Makanan** dan **Minuman**.
- Memesan beberapa menu sekaligus (ketik `selesai` untuk mencetak struk).
- Jumlah pesanan untuk menu yang sama otomatis digabung.
- Struk berisi subtotal, diskon, pajak, biaya pelayanan, total bayar, dan promo.

### Menu Pemilik
- **Tambah menu** baru (nama, harga, kategori).
- **Ubah harga** menu, dengan konfirmasi sebelum disimpan.
- **Hapus menu**, dengan konfirmasi sebelum dihapus.

## 📏 Aturan Perhitungan

| Aturan | Ketentuan |
|---|---|
| Subtotal | Jumlah dari harga × jumlah untuk setiap item |
| Diskon | 10% jika subtotal **lebih dari Rp100.000** |
| Pajak | 10% dari harga setelah diskon |
| Biaya pelayanan | Rp20.000 (tetap) |
| Promo | **Beli 1 gratis 1 untuk minuman** jika subtotal **lebih dari Rp50.000** |

**Total bayar** = (subtotal − diskon) + pajak + biaya pelayanan

### Contoh perhitungan

Pesanan: Nasi Goreng Spesial ×2, Ayam Bakar ×2, Es Teh Manis ×1

| Komponen | Nilai |
|---|---|
| Subtotal | Rp118.000 |
| Diskon 10% | Rp11.800 |
| Setelah diskon | Rp106.200 |
| Pajak 10% | Rp10.620 |
| Biaya pelayanan | Rp20.000 |
| **Total bayar** | **Rp136.820** |
| Promo | Bonus gratis Es Teh Manis ×1 |

## 🍜 Data Menu Awal

| Kategori | Menu | Harga |
|---|---|---|
| Makanan | Nasi Goreng Spesial | Rp25.000 |
| Makanan | Mie Ayam Bakso | Rp20.000 |
| Makanan | Ayam Bakar | Rp30.000 |
| Makanan | Sate Ayam | Rp28.000 |
| Minuman | Es Teh Manis | Rp8.000 |
| Minuman | Jus Jeruk | Rp15.000 |
| Minuman | Kopi Susu | Rp18.000 |
| Minuman | Air Mineral | Rp5.000 |

## 📁 Struktur Proyek

```
.
├── Main.java   # Alur program, perhitungan, struk, dan menu pemilik
├── Menu.java   # Kelas model untuk satu item menu
└── README.md
```

Kedua berkas harus berada di folder yang sama.

## 🚀 Cara Menjalankan

**Prasyarat:** JDK 8 atau lebih baru.

```bash
# 1. Clone repositori
git clone https://github.com/<username>/<nama-repositori>.git
cd <nama-repositori>

# 2. Kompilasi
javac Main.java Menu.java

# 3. Jalankan
java Main
```

## 🧠 Penerapan Konsep Pemrograman

| Konsep | Penerapan |
|---|---|
| **Kelas & objek** | `Menu` dengan atribut `private`, constructor, getter, dan satu setter (`setHarga`) |
| **Array** | `daftarMenu`, `pesananMenu`, `pesananJumlah`; kapasitas diperbesar otomatis dengan `Arrays.copyOf()` |
| **Percabangan** | `switch` pada menu utama dan menu pemilik; `if-else` untuk diskon, promo, dan konfirmasi |
| **Perulangan** | `do-while` (menu), `while` (pemesanan), `for` dan `for-each` (struk, kategori, hapus menu) |
| **Method** | `hitungSubtotal`, `hitungDiskon`, `berhakPromo`, `cetakStruk`, `formatRupiah`, dan lainnya |
| **Validasi input** | `bacaAngka`, `bacaTeks`, dan `adalahAngka` menolak input kosong, bukan angka, atau di luar rentang |
| **Konstanta** | Aturan restoran disimpan sebagai `static final` sehingga mudah diubah di satu tempat |

## 🧩 Ringkasan Method pada `Main.java`

| Method | Fungsi |
|---|---|
| `main` | Titik awal program; mengisi menu awal lalu membuka menu utama |
| `isiMenuAwal` | Menambahkan delapan menu awal |
| `tambahKeDaftar` | Menyimpan menu ke array dan memperbesar kapasitas jika penuh |
| `menuUtama` | Menu utama (Pelanggan, Pemilik, Keluar) |
| `menuPelanggan` / `tampilkanMenu` / `prosesPesanan` / `tambahPesanan` | Alur pemesanan pelanggan |
| `hitungSubtotal` / `hitungDiskon` / `berhakPromo` | Perhitungan biaya dan promo |
| `cetakStruk` | Mencetak struk pembayaran |
| `menuPemilik` / `tambahMenuBaru` / `ubahHargaMenu` / `hapusMenu` | Pengelolaan menu oleh pemilik |
| `bacaAngka` / `bacaTeks` / `adalahAngka` | Validasi input |
| `formatRupiah` | Mengubah angka menjadi format `Rp 25.000` |

## 🛡️ Validasi Input

- Menu atau pilihan yang diketik bukan angka akan ditolak dan diminta ulang.
- Angka di luar rentang yang diizinkan akan ditolak.
- Nama menu tidak boleh kosong.
- Angka dibatasi maksimal 8 digit agar tidak terjadi kesalahan konversi ke `int`.
- Perubahan harga dan penghapusan menu hanya diproses jika pemilik menjawab `Ya`.

## 👤 Penulis

**[Nama Anda]** — [NIM] — Sistem Informasi, Universitas Terbuka

## 📄 Lisensi

Proyek ini dibuat untuk keperluan pembelajaran. Silakan gunakan dan kembangkan sesuai kebutuhan.
