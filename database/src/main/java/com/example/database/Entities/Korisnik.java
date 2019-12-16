package com.example.database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "korisnik")
public class Korisnik {
    @PrimaryKey
    @NonNull String korisnicko_ime;

    @ForeignKey(entity = Slika.class, parentColumns = "id_slika", childColumns = "id_slika")
    @ColumnInfo(index = true)
    @NonNull
    int id_slika;

    String ime;
    String prezime;
    String email;
    String lozinka;


    public String getKorisnicko_ime() {
        return korisnicko_ime;
    }

    public int getId_slika() { return id_slika; }

    public void setId_slika(int id_slika) { this.id_slika = id_slika; }

    public void setKorisnicko_ime(String korisnicko_ime) {
        this.korisnicko_ime = korisnicko_ime;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

}
