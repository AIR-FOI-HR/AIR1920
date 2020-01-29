package com.example.database.EntitiesFirebase;

public class KategorijaZnamenitost {

    int idKategorijaZnamenitosti;
    String naziv;

    public KategorijaZnamenitost() {
    }

    public KategorijaZnamenitost(int idKategorijaZnamenitosti, String naziv) {
        this.idKategorijaZnamenitosti = idKategorijaZnamenitosti;
        this.naziv = naziv;
    }

    public int getIdKategorijaZnamenitosti() {
        return idKategorijaZnamenitosti;
    }

    public void setIdKategorijaZnamenitosti(int idKategorijaZnamenitosti) {
        this.idKategorijaZnamenitosti = idKategorijaZnamenitosti;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}
