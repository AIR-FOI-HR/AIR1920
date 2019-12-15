package com.example.database.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "ocjenaKomentar", primaryKeys = {"korisnicko_ime", "id_komentar"})
public class OcjenaKomentar {

    @ForeignKey(entity = Slika.class, parentColumns = "korisnicko_ime", childColumns = "korisnicko_ime")
    @ColumnInfo(index = true)
    String korisnicko_ime;

    @ForeignKey(entity = Znamenitost.class, parentColumns = "id_komentar", childColumns = "id_komentar")
    @ColumnInfo(index = true)
    int id_komentar;

    public String getKorisnicko_ime() {
        return korisnicko_ime;
    }

    public void setKorisnicko_ime(String korisnicko_ime) {
        this.korisnicko_ime = korisnicko_ime;
    }

    public int getId_komentar() {
        return id_komentar;
    }

    public void setId_komentar(int id_komentar) {
        this.id_komentar = id_komentar;
    }
}
