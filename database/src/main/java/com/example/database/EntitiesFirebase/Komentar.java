package com.example.database.EntitiesFirebase;

public class Komentar {
    int idKomentar;
    String opis;
    int ocjena;
    String uid;
    int idZnamenitost;
    int brojUp;
    int brojDown;



    public Komentar() {
    }

    public Komentar(int idKomentar, String opis, int ocjena, String uid, int idZnamenitost, int brojUp, int brojDown) {
        this.idKomentar = idKomentar;
        this.opis = opis;
        this.ocjena = ocjena;
        this.uid = uid;
        this.idZnamenitost = idZnamenitost;
        this.brojUp=brojUp;
        this.brojDown=brojDown;
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

    public int getBrojUp() {
        return brojUp;
    }

    public void setBrojUp(int brojUp) {
        this.brojUp = brojUp;
    }

    public int getBrojDown() {
        return brojDown;
    }

    public void setBrojDown(int brojDown) {
        this.brojDown = brojDown;
    }




}
