package com.example.database.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "korisnikLokacija", primaryKeys = {"korisnicko_ime", "id_lokacija"})
public class KorisnikLokacija {

    @ForeignKey(entity = Korisnik.class, parentColumns = "korisnicko_ime", childColumns = "korisnicko_ime")
    @ColumnInfo(index = true)
    String korisnicko_ime;

    @ForeignKey(entity = Lokacija.class, parentColumns = "id_lokacija", childColumns = "id_lokacija")
    @ColumnInfo(index = true)
    int id_lokacija;

    public String getKorisnicko_ime() {
        return korisnicko_ime;
    }

    public void setKorisnicko_ime(String korisnicko_ime) {
        this.korisnicko_ime = korisnicko_ime;
    }

    public int getId_lokacija() {
        return id_lokacija;
    }

    public void setId_lokacija(int id_lokacija) {
        this.id_lokacija = id_lokacija;
    }
}
