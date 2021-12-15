package com.example.smkharvard;

public class Mahasiswa {
    public int id;
    public String nama;
    public String nrp;
    public String prodi;
    public String alamat;
    public String kontak;

    public Mahasiswa(int id, String nama, String nrp, String prodi, String alamat, String kontak) {
        this.id=id;
        this.nama=nama;
        this.nrp=nrp;
        this.prodi=prodi;
        this.alamat=alamat;
        this.kontak=kontak;

    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getNrp() {
        return nrp;
    }

    public String getProdi() {
        return prodi;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getKontak() { return kontak; }
}
