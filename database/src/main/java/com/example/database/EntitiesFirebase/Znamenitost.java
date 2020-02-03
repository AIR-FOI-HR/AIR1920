package com.example.database.EntitiesFirebase;

import java.util.Comparator;
import java.util.List;

public class Znamenitost implements Comparable<Znamenitost> {
    int idZnamenitosti;
    String naziv;
    String adresa;
    int idKategorijaZnamenitosti;
    String opis;
    double longitude;
    double latitude;
    int idLokacija;
    String lokacijaSlike;
    double averageGrade;
    List<Slika> listaSlikaGalerije;






    public Znamenitost(){}

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

    public List<Slika> getListaSlikaGalerije() {
        return listaSlikaGalerije;
    }
    public void setListaSlikaGalerije(List<Slika> listaSlikaGalerije) {
        this.listaSlikaGalerije = listaSlikaGalerije;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(float averageGrade) {
        this.averageGrade = averageGrade;
    }

//   @Override
 //   public int compareTo(Znamenitost u) {
      //  if (getAverageGrade() == 0|| u.getAverageGrade() == 0) {
           // return 0;
      //  }
       // return new Double(averageGrade).compareTo( u.averageGrade);
   // }


    @Override
    public int compareTo(Znamenitost znamenitost) {
        return Double.compare(znamenitost.getAverageGrade(),this.averageGrade);

    }








}
