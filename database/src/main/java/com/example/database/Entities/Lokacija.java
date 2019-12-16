package com.example.database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "lokacija")
public class Lokacija {
    @PrimaryKey(autoGenerate = true)
    int id_lokacija;

    @ForeignKey(entity = Slika.class, parentColumns = "id_slika", childColumns = "id_slika")
    @ColumnInfo(index = true)
    @NonNull public int id_slika;

    String naziv;
    String opis;

    public int getId_lokacija() {
        return id_lokacija;
    }

    public void setId_lokacija(int id_lokacija) {
        this.id_lokacija = id_lokacija;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
