package com.example.culturearound.Firebase.EntitiesFirebase;

public class Lokacija {
    int idLokacija;
    String naziv;
    String opis;
    int idSlika;

    public Lokacija() {
    }

    public Lokacija(int idLokacija, String naziv, String opis, int idSlika) {
        this.idLokacija = idLokacija;
        this.naziv = naziv;
        this.opis = opis;
        this.idSlika = idSlika;
    }

    public int getIdLokacija() {
        return idLokacija;
    }

    public void setIdLokacija(int idLokacija) {
        this.idLokacija = idLokacija;
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

    public int getIdSlika() {
        return idSlika;
    }

    public void setIdSlika(int idSlika) {
        this.idSlika = idSlika;
    }
}
