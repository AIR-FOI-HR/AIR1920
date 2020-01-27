package com.example.culturearound.Firebase.EntitiesFirebase;

import java.util.List;

public class Korisnik {
    String uid;
    String ime;
    String prezime;
    String email;
    String lozinka;



    //slika profila
    String lokacijaSlike;

    //favoriti korisnika
    List<Integer> idFavouriteZnamenitostiList;

    public Korisnik() {
    }

    public Korisnik(String ime, String prezime, String email, String lozinka, String lokacijaSlike) {
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.lozinka = lozinka;
        this.lokacijaSlike = lokacijaSlike;
    }

    public Korisnik(String uid, String ime, String prezime, String email, String lozinka, String lokacijaSlike) {
        this.uid = uid;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.lozinka = lozinka;
        this.lokacijaSlike = lokacijaSlike;
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    /*
    public Korisnik(String uid, String ime, String prezime, String email, String lozinka, int lokacijaSlike) {
        this.uid = uid;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.lozinka = lozinka;
        this.lokacijaSlike = lokacijaSlike;
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
     */

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

    public String getLokacijaSlike() {
        return lokacijaSlike;
    }

    public void setLokacijaSlike(String lokacijaSlike) {
        this.lokacijaSlike = lokacijaSlike;
    }

    public List<Integer> getIdFavouriteZnamenitostiList() {
        return idFavouriteZnamenitostiList;
    }

    public void setIdFavouriteZnamenitostiList(List<Integer> idFavouriteZnamenitostiList) {
        this.idFavouriteZnamenitostiList = idFavouriteZnamenitostiList;
    }
}
