package com.example.database.EntitiesFirebase;

import java.util.List;

public class Slika {
    int idSlika;
    String imgUrl;
    String tekstSlike;
    List<Integer> idSlikaGalerijeList;

    public Slika() {
    }

    public Slika(int idSlika, String imgUrl, String tekstSlike) {
        this.idSlika = idSlika;
        this.imgUrl = imgUrl;
        this.tekstSlike = tekstSlike;
    }

    public int getIdSlika() {
        return idSlika;
    }

    public void setIdSlika(int idSlika) {
        this.idSlika = idSlika;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTekstSlike() {
        return tekstSlike;
    }

    public void setTekstSlike(String tekstSlike) {
        this.tekstSlike = tekstSlike;
    }

    public List<Integer> getIdSlikaGalerijeList() {
        return idSlikaGalerijeList;
    }

    public void setIdSlikaGalerijeList(List<Integer> idSlikaGalerijeList) {
        this.idSlikaGalerijeList = idSlikaGalerijeList;
    }
}
