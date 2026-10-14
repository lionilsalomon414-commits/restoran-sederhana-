/**
 * Kelas Menu merepresentasikan satu item menu restoran
 * (makanan atau minuman) beserta harga dan kategorinya.
 */
public class Menu {
    // Atribut (variabel) dengan tipe data yang sesuai
    private String nama;
    private int harga;
    private String kategori; // "Makanan" atau "Minuman"

    // Constructor
    public Menu(String nama, int harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    // Getter
    public String getNama() {
        return nama;
    }

    public int getHarga() {
        return harga;
    }

    public String getKategori() {
        return kategori;
    }

    // Setter (hanya harga yang boleh diubah oleh pemilik restoran)
    public void setHarga(int harga) {
        this.harga = harga;
    }
}
