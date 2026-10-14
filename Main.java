import java.util.Arrays;
import java.util.Scanner;

/**
 * Aplikasi Restoran Sederhana
 * - Menu Pelanggan : memesan, menghitung total, mencetak struk
 * - Menu Pemilik   : menambah menu, mengubah harga, menghapus menu
 */
public class Main {

    // ===== Konstanta aturan restoran =====
    static final int PERSEN_PAJAK = 10;
    static final int BIAYA_PELAYANAN = 20000;
    static final int BATAS_DISKON = 100000;  // diskon bila subtotal melebihi nilai ini
    static final int PERSEN_DISKON = 10;
    static final int BATAS_PROMO = 50000;    // promo beli 1 gratis 1 minuman
    static final String[] KATEGORI = {"Makanan", "Minuman"};

    // ===== Data (menggunakan Array) =====
    static Scanner input = new Scanner(System.in);
    static Menu[] daftarMenu = new Menu[20];
    static int jumlahMenu = 0;

    static Menu[] pesananMenu = new Menu[10];
    static int[] pesananJumlah = new int[10];
    static int jumlahPesanan = 0;

    // =====================================================
    // MAIN
    // =====================================================
    public static void main(String[] args) {
        isiMenuAwal();
        menuUtama();
        System.out.println("Terima kasih. Program selesai.");
    }

    // Mengisi data awal: minimal 4 makanan dan 4 minuman
    static void isiMenuAwal() {
        tambahKeDaftar(new Menu("Nasi Goreng Spesial", 25000, "Makanan"));
        tambahKeDaftar(new Menu("Mie Ayam Bakso", 20000, "Makanan"));
        tambahKeDaftar(new Menu("Ayam Bakar", 30000, "Makanan"));
        tambahKeDaftar(new Menu("Sate Ayam", 28000, "Makanan"));
        tambahKeDaftar(new Menu("Es Teh Manis", 8000, "Minuman"));
        tambahKeDaftar(new Menu("Jus Jeruk", 15000, "Minuman"));
        tambahKeDaftar(new Menu("Kopi Susu", 18000, "Minuman"));
        tambahKeDaftar(new Menu("Air Mineral", 5000, "Minuman"));
    }

    // Menambah menu ke array; array diperbesar otomatis bila penuh
    static void tambahKeDaftar(Menu m) {
        if (jumlahMenu == daftarMenu.length) {
            daftarMenu = Arrays.copyOf(daftarMenu, daftarMenu.length * 2);
        }
        daftarMenu[jumlahMenu] = m;
        jumlahMenu++;
    }

    // =====================================================
    // MENU UTAMA (parent)
    // =====================================================
    static void menuUtama() {
        int pilihan;
        do {
            System.out.println();
            System.out.println("===== RESTORAN SEDERHANA =====");
            System.out.println("1. Menu Pelanggan (pesan makanan/minuman)");
            System.out.println("2. Menu Pemilik (kelola menu)");
            System.out.println("0. Keluar");
            pilihan = bacaAngka("Pilih menu: ", 0, 2);

            switch (pilihan) {
                case 1:
                    menuPelanggan();
                    break;
                case 2:
                    menuPemilik();
                    break;
                case 0:
                    break;
            }
        } while (pilihan != 0);
    }

    // =====================================================
    // MENU PELANGGAN
    // =====================================================
    static void menuPelanggan() {
        // setiap pelanggan baru memulai dengan pesanan kosong
        pesananMenu = new Menu[10];
        pesananJumlah = new int[10];
        jumlahPesanan = 0;

        tampilkanMenu();
        prosesPesanan();

        if (jumlahPesanan == 0) {
            System.out.println("Belum ada pesanan. Kembali ke menu utama.");
        } else {
            cetakStruk();
        }
    }

    // Menampilkan daftar menu dikelompokkan berdasarkan kategori
    static void tampilkanMenu() {
        System.out.println();
        System.out.println("========== DAFTAR MENU ==========");
        for (String kategori : KATEGORI) {
            System.out.println("[" + kategori.toUpperCase() + "]");
            boolean adaItem = false;
            for (int i = 0; i < jumlahMenu; i++) {
                if (daftarMenu[i].getKategori().equals(kategori)) {
                    System.out.println("  " + (i + 1) + ". " + daftarMenu[i].getNama()
                            + " - " + formatRupiah(daftarMenu[i].getHarga()));
                    adaItem = true;
                }
            }
            if (!adaItem) {
                System.out.println("  (belum ada menu)");
            }
        }
        System.out.println("=================================");
    }

    // Menerima pesanan berulang sampai pelanggan mengetik 'selesai'
    static void prosesPesanan() {
        boolean selesai = false;
        while (!selesai) {
            System.out.print("Masukkan nomor menu (ketik 'selesai' untuk mengakhiri): ");
            String teks = input.nextLine().trim();

            if (teks.equalsIgnoreCase("selesai")) {
                selesai = true;
            } else if (!adalahAngka(teks)) {
                System.out.println("Input tidak valid. Masukkan nomor menu atau 'selesai'.");
            } else {
                int nomor = Integer.parseInt(teks);
                if (nomor < 1 || nomor > jumlahMenu) {
                    System.out.println("Nomor menu tidak ada. Silakan masukkan kembali.");
                } else {
                    Menu dipilih = daftarMenu[nomor - 1];
                    int jumlah = bacaAngka("Jumlah " + dipilih.getNama() + ": ", 1, 100);
                    tambahPesanan(dipilih, jumlah);
                    System.out.println("  + " + dipilih.getNama() + " x" + jumlah + " ditambahkan.");
                }
            }
        }
    }

    // Menyimpan pesanan ke array (jumlah digabung bila menu sama dipesan lagi)
    static void tambahPesanan(Menu m, int jumlah) {
        for (int i = 0; i < jumlahPesanan; i++) {
            if (pesananMenu[i] == m) {
                pesananJumlah[i] += jumlah;
                return;
            }
        }
        if (jumlahPesanan == pesananMenu.length) {
            pesananMenu = Arrays.copyOf(pesananMenu, pesananMenu.length * 2);
            pesananJumlah = Arrays.copyOf(pesananJumlah, pesananJumlah.length * 2);
        }
        pesananMenu[jumlahPesanan] = m;
        pesananJumlah[jumlahPesanan] = jumlah;
        jumlahPesanan++;
    }

    // =====================================================
    // PERHITUNGAN BIAYA
    // =====================================================

    // Total harga seluruh item sebelum diskon, pajak, dan pelayanan
    static int hitungSubtotal() {
        int total = 0;
        for (int i = 0; i < jumlahPesanan; i++) {
            total += pesananMenu[i].getHarga() * pesananJumlah[i];
        }
        return total;
    }

    // Diskon 10% bila subtotal melebihi Rp 100.000
    static int hitungDiskon(int subtotal) {
        if (subtotal > BATAS_DISKON) {
            return subtotal * PERSEN_DISKON / 100;
        } else {
            return 0;
        }
    }

    // Promo beli 1 gratis 1 untuk minuman bila subtotal melebihi Rp 50.000
    static boolean berhakPromo(int subtotal) {
        return subtotal > BATAS_PROMO;
    }

    // =====================================================
    // CETAK STRUK
    // =====================================================
    static void cetakStruk() {
        int subtotal = hitungSubtotal();
        int diskon = hitungDiskon(subtotal);
        int setelahDiskon = subtotal - diskon;
        int pajak = setelahDiskon * PERSEN_PAJAK / 100;
        int totalBayar = setelahDiskon + pajak + BIAYA_PELAYANAN;
        boolean promo = berhakPromo(subtotal);

        System.out.println();
        System.out.println("==================== STRUK PESANAN ====================");
        System.out.printf("%-4s%-22s%5s%14s%14s%n", "No", "Item", "Qty", "Harga", "Total");
        System.out.println("-------------------------------------------------------");
        for (int i = 0; i < jumlahPesanan; i++) {
            int totalItem = pesananMenu[i].getHarga() * pesananJumlah[i];
            System.out.printf("%-4d%-22s%5d%14s%14s%n", (i + 1), pesananMenu[i].getNama(),
                    pesananJumlah[i], formatRupiah(pesananMenu[i].getHarga()), formatRupiah(totalItem));
        }
        System.out.println("-------------------------------------------------------");
        System.out.printf("%-45s%14s%n", "Total biaya pesanan", formatRupiah(subtotal));

        // Struktur keputusan: diskon
        if (diskon > 0) {
            System.out.printf("%-45s%14s%n", "Diskon " + PERSEN_DISKON + "% (pesanan > " + formatRupiah(BATAS_DISKON) + ")",
                    "-" + formatRupiah(diskon));
            System.out.printf("%-45s%14s%n", "Harga setelah diskon", formatRupiah(setelahDiskon));
        } else {
            System.out.printf("%-45s%14s%n", "Diskon", "-");
        }

        System.out.printf("%-45s%14s%n", "Pajak " + PERSEN_PAJAK + "%", formatRupiah(pajak));
        System.out.printf("%-45s%14s%n", "Biaya pelayanan", formatRupiah(BIAYA_PELAYANAN));
        System.out.println("-------------------------------------------------------");
        System.out.printf("%-45s%14s%n", "TOTAL BAYAR", formatRupiah(totalBayar));

        // Struktur keputusan: promo beli 1 gratis 1 (kategori Minuman)
        System.out.println();
        if (promo) {
            boolean adaMinuman = false;
            for (int i = 0; i < jumlahPesanan; i++) {
                if (pesananMenu[i].getKategori().equals("Minuman")) {
                    if (!adaMinuman) {
                        System.out.println("PROMO Beli 1 Gratis 1 (Minuman, pesanan > " + formatRupiah(BATAS_PROMO) + "):");
                        adaMinuman = true;
                    }
                    System.out.println("  * Bonus gratis: " + pesananMenu[i].getNama() + " x" + pesananJumlah[i]);
                }
            }
            if (!adaMinuman) {
                System.out.println("Promo Beli 1 Gratis 1 berlaku untuk Minuman (tidak ada minuman dipesan).");
            }
        } else {
            System.out.println("Promo Beli 1 Gratis 1 belum berlaku (pesanan belum melebihi "
                    + formatRupiah(BATAS_PROMO) + ").");
        }
        System.out.println("=======================================================");
        System.out.println("        Terima kasih atas kunjungan Anda!");
    }

    // =====================================================
    // MENU PEMILIK (pengelolaan menu)
    // =====================================================
    static void menuPemilik() {
        int pilihan;
        do {
            System.out.println();
            System.out.println("===== PENGELOLAAN MENU (PEMILIK) =====");
            System.out.println("1. Tambah menu baru");
            System.out.println("2. Ubah harga menu");
            System.out.println("3. Hapus menu");
            System.out.println("0. Kembali ke menu utama");
            pilihan = bacaAngka("Pilih menu: ", 0, 3);

            switch (pilihan) {
                case 1:
                    tambahMenuBaru();
                    break;
                case 2:
                    ubahHargaMenu();
                    break;
                case 3:
                    hapusMenu();
                    break;
                case 0:
                    break;
            }
        } while (pilihan != 0);
    }

    // Menambah beberapa menu sekaligus (pengulangan sebanyak yang diminta)
    static void tambahMenuBaru() {
        int banyak = bacaAngka("Berapa menu yang ingin ditambahkan (1-10)? ", 1, 10);
        for (int i = 1; i <= banyak; i++) {
            System.out.println("--- Menu baru ke-" + i + " ---");
            String nama = bacaTeks("Nama menu : ");
            int harga = bacaAngka("Harga (Rp): ", 1, 10000000);
            int pilihKategori = bacaAngka("Kategori (1. Makanan, 2. Minuman): ", 1, 2);
            String kategori = KATEGORI[pilihKategori - 1];
            tambahKeDaftar(new Menu(nama, harga, kategori));
            System.out.println("Menu '" + nama + "' berhasil ditambahkan.");
        }
    }

    // Mengubah harga menu dengan konfirmasi 'Ya'
    static void ubahHargaMenu() {
        if (jumlahMenu == 0) {
            System.out.println("Daftar menu kosong.");
            return;
        }
        tampilkanMenu();
        int nomor = bacaAngka("Masukkan nomor menu yang akan diubah harganya: ", 1, jumlahMenu);
        Menu m = daftarMenu[nomor - 1];
        int hargaBaru = bacaAngka("Harga baru untuk " + m.getNama() + " (Rp): ", 1, 10000000);

        System.out.println("Konfirmasi: harga " + m.getNama() + " akan diubah dari "
                + formatRupiah(m.getHarga()) + " menjadi " + formatRupiah(hargaBaru) + ".");
        System.out.print("Yakin mengubah? Ketik 'Ya' untuk melanjutkan: ");
        String jawab = input.nextLine().trim();

        if (jawab.equalsIgnoreCase("ya")) {
            m.setHarga(hargaBaru);
            System.out.println("Harga berhasil diubah.");
        } else {
            System.out.println("Perubahan dibatalkan. Tidak ada perubahan data.");
        }
    }

    // Menghapus menu dengan konfirmasi 'Ya'
    static void hapusMenu() {
        if (jumlahMenu == 0) {
            System.out.println("Daftar menu kosong.");
            return;
        }
        tampilkanMenu();
        int nomor = bacaAngka("Masukkan nomor menu yang akan dihapus: ", 1, jumlahMenu);
        Menu m = daftarMenu[nomor - 1];

        System.out.println("Konfirmasi: menu '" + m.getNama() + "' akan dihapus.");
        System.out.print("Yakin menghapus? Ketik 'Ya' untuk melanjutkan: ");
        String jawab = input.nextLine().trim();

        if (jawab.equalsIgnoreCase("ya")) {
            // geser elemen array ke kiri untuk menutup posisi yang dihapus
            for (int i = nomor - 1; i < jumlahMenu - 1; i++) {
                daftarMenu[i] = daftarMenu[i + 1];
            }
            daftarMenu[jumlahMenu - 1] = null;
            jumlahMenu--;
            System.out.println("Menu berhasil dihapus.");
        } else {
            System.out.println("Penghapusan dibatalkan. Tidak ada perubahan data.");
        }
    }

    // =====================================================
    // METHOD BANTU (input & format)
    // =====================================================

    // Meminta angka dalam rentang [min, max]; terus meminta ulang bila tidak valid
    static int bacaAngka(String pesan, int min, int max) {
        int hasil = 0;
        boolean valid = false;
        while (!valid) {
            System.out.print(pesan);
            String teks = input.nextLine().trim();
            if (!adalahAngka(teks)) {
                System.out.println("Input tidak valid, masukkan angka.");
            } else {
                hasil = Integer.parseInt(teks);
                if (hasil < min || hasil > max) {
                    System.out.println("Pilihan di luar jangkauan (" + min + "-" + max + "). Coba lagi.");
                } else {
                    valid = true;
                }
            }
        }
        return hasil;
    }

    // Meminta teks yang tidak boleh kosong
    static String bacaTeks(String pesan) {
        String teks = "";
        while (teks.isEmpty()) {
            System.out.print(pesan);
            teks = input.nextLine().trim();
            if (teks.isEmpty()) {
                System.out.println("Teks tidak boleh kosong.");
            }
        }
        return teks;
    }

    // Memeriksa apakah teks hanya berisi angka (maksimal 8 digit)
    static boolean adalahAngka(String teks) {
        if (teks.isEmpty() || teks.length() > 8) {
            return false;
        }
        for (int i = 0; i < teks.length(); i++) {
            if (!Character.isDigit(teks.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // Mengubah angka menjadi format rupiah, contoh 25000 -> Rp 25.000
    static String formatRupiah(int angka) {
        String digit = String.valueOf(angka);
        String hasil = "";
        int hitung = 0;
        for (int i = digit.length() - 1; i >= 0; i--) {
            hasil = digit.charAt(i) + hasil;
            hitung++;
            if (hitung % 3 == 0 && i > 0) {
                hasil = "." + hasil;
            }
        }
        return "Rp " + hasil;
    }
}
