package com.example.culturearound.Firebase.Listeners;

import com.example.culturearound.Firebase.EntitiesFirebase.Lokacija;
import com.example.culturearound.Firebase.EntitiesFirebase.Znamenitost;

import java.util.List;

public interface LokacijaListener {
    void onLoadLokacijaSucess(String message, List<Lokacija> listaLokacija);
    void onLoadLokacijaFail(String message);
}
