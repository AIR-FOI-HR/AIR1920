package com.example.database.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "slika")
public class Slika {
    @PrimaryKey(autoGenerate = true)
    int id_slika;

    String img_url;
    String tekst_slike;

    public int getId_slika() {
        return id_slika;
    }

    public void setId_slika(int id_slika) {
        this.id_slika = id_slika;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getTekst_slike() {
        return tekst_slike;
    }

    public void setTekst_slike(String tekst_slike) {
        this.tekst_slike = tekst_slike;
    }
}
