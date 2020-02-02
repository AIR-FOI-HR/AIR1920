package com.example.database.Listeners;

import com.example.database.EntitiesFirebase.Komentar;
import java.util.List;

public interface RecenzijaListener {
    //Vrati uspješno dobavljenu listu komentara
    void onLoadRecenzijaSucess(String message, List<Komentar> listaKomentara);
    //Vrati Like/Dislike
    void onOcjenaKomentaraSucess(String message, String mojaOcjenaKomentara);
    //Neuspjšno dohvaćanje iz baze
    void onLoadRecenzijaFail(String message);
}
