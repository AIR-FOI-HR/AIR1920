package com.example.culturearound.Firebase.Listeners;

import com.example.culturearound.Firebase.EntitiesFirebase.Komentar;
import java.util.List;

public interface RecenzijaListener {

    void onLoadRecenzijaSucess(String message, List<Komentar> listaKomentara);
    void onLoadRecenzijaFail(String message);
}
