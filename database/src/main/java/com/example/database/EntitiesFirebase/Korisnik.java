package com.example.database.EntitiesFirebase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Korisnik {
    String uid;
    String ime;
    String prezime;
    String email;
    String lozinka;
    int progressbar;



    //slika profila
    String lokacijaSlike;

    //favoriti korisnika
    List<Map<String, Integer>> listaSpremljenihZnamenitosti = new ArrayList<>();

    public Korisnik() {
    }

    public Korisnik(String ime, String prezime, String email, String lozinka, String lokacijaSlike,int progressbar) {
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.lozinka = lozinka;
        this.lokacijaSlike = lokacijaSlike;
        this.progressbar=progressbar;
    }

    public Korisnik(String uid, String ime, String prezime, String email, String lozinka, String lokacijaSlike,int progressbar) {
        this.uid = uid;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.lozinka = lozinka;
        this.lokacijaSlike = lokacijaSlike;
        this.progressbar=progressbar;
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

    public List<Map<String, Integer>> getListaSpremljenihZnamenitosti() {
        return listaSpremljenihZnamenitosti;
    }

    public void setListaSpremljenihZnamenitosti(List<Map<String, Integer>> listaSpremljenihZnamenitosti) {
        this.listaSpremljenihZnamenitosti = listaSpremljenihZnamenitosti;
    }

    public List<Integer> getIdoviZnamenitosti() {
        String kljucZaId = "idZnamenitosti";
        List<Integer> lista = new ArrayList<>();
        for(Map keyValue: listaSpremljenihZnamenitosti) {
            if(keyValue.containsKey(kljucZaId)) {
                lista.add((Integer)keyValue.get(kljucZaId));
            }
        };
        return lista;
    }

    public int getProgressbar() {
        return progressbar;
    }

    public void setProgressbar(int progressbar) {
        this.progressbar = progressbar;
    }


}
