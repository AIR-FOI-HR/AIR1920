package com.example.culturearound.Firebase.Listeners;

import com.example.culturearound.Firebase.EntitiesFirebase.Znamenitost;

import java.util.List;

public interface ZnamenitostListener {
    /**
     * Metoda koja se poziva kada se USPJEŠNO izvrši upit nad bazom za dohvat znamenitosti.
     * @param message
     * @param listaZnamenitosti
     */
    void onLoadZnamenitostSucess(String message, List<Znamenitost> listaZnamenitosti);

    /**
     * Metoda koja se poziva kada se NEUSPJEŠNO izvrši upit nad bazom za dohvat znamenitosti.
     * @param message
     */
    void onLoadZnamenitostFail(String message);
}
