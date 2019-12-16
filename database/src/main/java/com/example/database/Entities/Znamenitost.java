package com.example.database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "znamenitost")
public class Znamenitost {
    @PrimaryKey(autoGenerate = true)
    int id_znamenitost;

    @ForeignKey(entity = KategorijaZnamenitosti.class, parentColumns = "id_kategorija_znamenitosti", childColumns = "id_kategorija_znamenitosti")
    @ColumnInfo(index = true)
    @NonNull
    int id_kategorija_znamenitosti;

    @ForeignKey(entity = Lokacija.class, parentColumns = "id_lokacija", childColumns = "id_lokacija")
    @ColumnInfo(index = true)
    @NonNull
    int id_lokacija;

    @ForeignKey(entity = Slika.class, parentColumns = "id_slika", childColumns = "id_slika")
    @ColumnInfo(index = true)
    @NonNull
    int id_slika;

    String naziv;
    String adresa;
    String opis;
    int longitude;
    int latitude;

    public int getId_znamenitost() {
        return id_znamenitost;
    }

    public void setId_znamenitost(int id_znamenitost) {
        this.id_znamenitost = id_znamenitost;
    }

    public int getId_kategorija_znamenitosti() {
        return id_kategorija_znamenitosti;
    }

    public void setId_kategorija_znamenitosti(int id_kategorija_znamenitosti) {
        this.id_kategorija_znamenitosti = id_kategorija_znamenitosti;
    }

    public int getId_lokacija() {
        return id_lokacija;
    }

    public void setId_lokacija(int id_lokacija) {
        this.id_lokacija = id_lokacija;
    }

    public int getId_slika() {
        return id_slika;
    }

    public void setId_slika(int id_slika) {
        this.id_slika = id_slika;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }
}
