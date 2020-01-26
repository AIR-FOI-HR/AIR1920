package com.example.culturearound.Firebase.Listeners;

import com.example.culturearound.Firebase.EntitiesFirebase.Lokacija;
import com.example.culturearound.Firebase.EntitiesFirebase.Znamenitost;

import java.util.List;

public interface LokacijaListener {
    /**
     * Metoda koja se poziva kada se USPJEŠNO izvrši upit nad bazom za dohvat lokacija.
     * @param message
     * @param listaLokacija
     */
    void onLoadLokacijaSucess(String message, List<Lokacija> listaLokacija);

    /**
     * Metoda koja se poziva kada se NEUSPJEŠNO izvrši upit nad bazom za dohvat lokacija.
     * @param message
     */
    void onLoadLokacijaFail(String message);
}
