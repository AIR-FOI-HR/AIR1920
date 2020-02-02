package com.example.database.EntitiesFirebase;

public class Slika {
    String idSlika;
    String lokacijaSlike;

    public Slika() {
    }

    public Slika(String idSlika, String lokacijaSlike) {
        this.idSlika = idSlika;
        this.lokacijaSlike = lokacijaSlike;
    }

    public String getIdSlika() {
        return idSlika;
    }

    public void setIdSlika(String idSlika) {
        this.idSlika = idSlika;
    }

    public String getLokacijaSlike() {
        return lokacijaSlike;
    }

    public void setLokacijaSlike(String lokacijaSlike) {
        this.lokacijaSlike = lokacijaSlike;
    }
}
