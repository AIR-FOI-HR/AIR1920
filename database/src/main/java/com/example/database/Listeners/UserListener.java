package com.example.database.Listeners;

import com.example.database.EntitiesFirebase.Korisnik;

import java.util.List;

public interface UserListener {

    /**
     * Metoda koja se poziva kada se USPJEŠNO izvrši upit nad bazom za dohvat znamenitosti.
     * @param message
     * @param currentUser
     */
    void onLoadUserSuccess(String message, Korisnik currentUser);

    /**
     * Metoda koja se poziva kada se NEUSPJEŠNO izvrši upit nad bazom za dohvat znamenitosti.
     * @param message
     */
    void onLoadUserFail(String message);

}
