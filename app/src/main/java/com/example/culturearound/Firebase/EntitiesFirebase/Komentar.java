package com.example.culturearound.Firebase.EntitiesFirebase;

public class Komentar {
    int idKomentar;
    String opis;
    int ocjena;
    String uid;
    int idZnamenitost;

    public Komentar() {
    }

    public Komentar(int idKomentar, String opis, int ocjena, String uid, int idZnamenitost) {
        this.idKomentar = idKomentar;
        this.opis = opis;
        this.ocjena = ocjena;
        this.uid = uid;
        this.idZnamenitost = idZnamenitost;
    }

    public int getIdKomentar() {
        return idKomentar;
    }

    public void setIdKomentar(int idKomentar) {
        this.idKomentar = idKomentar;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getIdZnamenitost() {
        return idZnamenitost;
    }

    public void setIdZnamenitost(int idZnamenitost) {
        this.idZnamenitost = idZnamenitost;
    }
}
