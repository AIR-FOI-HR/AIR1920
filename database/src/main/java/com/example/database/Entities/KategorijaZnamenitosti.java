package com.example.database.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "kategorijaZnamenitosti")
public class KategorijaZnamenitosti {
    @PrimaryKey(autoGenerate = true)
    int id_kategorija_znamenitosti;

    public int getId_kategorija_namenitosti() {
        return id_kategorija_znamenitosti;
    }

    public void setId_kategorijaZnamenitosti(int id_kategorija_znamenitosti) {
        this.id_kategorija_znamenitosti = id_kategorija_znamenitosti;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    String naziv;
}
