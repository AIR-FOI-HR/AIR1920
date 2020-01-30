package com.example.database.EntitiesFirebase;

import java.util.List;

public class Znamenitost {
    int idZnamenitosti;
    String naziv;
    String adresa;
    int idKategorijaZnamenitosti;
    String opis;
    double longitude;
    double latitude;

    int idLokacija;
    String lokacijaSlike;
    List<String> slikeGalerijeList;

    public Znamenitost(){}

    public Znamenitost(int idZnamenitosti, String naziv, String adresa, int idKategorijaZnamenitosti, String opis, double longitude, double latitude, int idLokacija, String lokacijaSlike) {
        this.idZnamenitosti = idZnamenitosti;
        this.naziv = naziv;
        this.adresa = adresa;
        this.idKategorijaZnamenitosti = idKategorijaZnamenitosti;
        this.opis = opis;
        this.longitude = longitude;
        this.latitude = latitude;
        this.idLokacija = idLokacija;
        this.lokacijaSlike = lokacijaSlike;
    }

    public int getIdZnamenitosti() {
        return idZnamenitosti;
    }

    public void setIdZnamenitosti(int idZnamenitosti) {
        this.idZnamenitosti = idZnamenitosti;
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

    public int getIdKategorijaZnamenitosti() {
        return idKategorijaZnamenitosti;
    }

    public void setIdKategorijaZnamenitosti(int idKategorijaZnamenitosti) {
        this.idKategorijaZnamenitosti = idKategorijaZnamenitosti;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getIdLokacija() {
        return idLokacija;
    }

    public void setIdLokacija(int idLokacija) {
        this.idLokacija = idLokacija;
    }

    public String getLokacijaSlike() {
        return lokacijaSlike;
    }

    public void setLokacijaSlike(String lokacijaSlike) {
        this.lokacijaSlike = lokacijaSlike;
    }

    public List<String> getSlikeGalerijeList() {
        return slikeGalerijeList;
    }

    public void setSlikeGalerijeList(List<String> slikeGalerijeList) {
        this.slikeGalerijeList = slikeGalerijeList;
    }
}
