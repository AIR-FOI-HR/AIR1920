package com.example.culturearound.Firebase.EntitiesFirebase;

public class OcjenaKomentara {
    String uid;
    int idKomentar;
    boolean ocjena;

    public OcjenaKomentara() {
    }

    public OcjenaKomentara(String uid, int idKomentar, boolean ocjena) {
        this.uid = uid;
        this.idKomentar = idKomentar;
        this.ocjena = ocjena;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getIdKomentar() {
        return idKomentar;
    }

    public void setIdKomentar(int idKomentar) {
        this.idKomentar = idKomentar;
    }

    public boolean isOcjena() {
        return ocjena;
    }

    public void setOcjena(boolean ocjena) {
        this.ocjena = ocjena;
    }
}
