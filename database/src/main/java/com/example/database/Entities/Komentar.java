package com.example.database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "komentar")
public class Komentar {
    @PrimaryKey(autoGenerate = true)
    int id_komentar;

    @ForeignKey(entity = Korisnik.class, parentColumns = "korisnicko_ime", childColumns = "korisnicko_ime")
    @ColumnInfo(index = true)
    @NonNull String korisnicko_ime;

    @ForeignKey(entity = Znamenitost.class, parentColumns = "id_znamenitost", childColumns = "id_znamenitost")
    @ColumnInfo(index = true)
    @NonNull int id_znamenitost;

    String opis;

    public int getId_komentar() {
        return id_komentar;
    }

    public void setId_komentar(int id_komentar) {
        this.id_komentar = id_komentar;
    }

    public String getKorisnicko_ime() {
        return korisnicko_ime;
    }

    public void setKorisnicko_ime(String korisnicko_ime) {
        this.korisnicko_ime = korisnicko_ime;
    }

    public int getId_znamenitost() {
        return id_znamenitost;
    }

    public void setId_znamenitost(int id_znamenitost) {
        this.id_znamenitost = id_znamenitost;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public int getOcjena() {
        return ocjena;
    }

    public void setOcjena(int ocjena) {
        this.ocjena = ocjena;
    }

    int ocjena;


}
