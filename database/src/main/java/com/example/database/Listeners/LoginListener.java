package com.example.database.Listeners;

/**
 * Listener za prijavu i registraciju korisnika.
 */
public interface LoginListener {
    /**
     * Metoda koja se poziva kada se USPJEŠNO izvrši autentikacija.
     * @param message
     */
    void onLoginSuccess(String message);

    /**
     * Metoda koja se poziva kada se NEUSPJEŠNO izvrši autentikacija.
     * @param message
     */
    void onLoginFail(String message);
}
