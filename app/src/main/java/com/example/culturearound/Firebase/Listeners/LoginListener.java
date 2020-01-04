package com.example.culturearound.Firebase.Listeners;

/**
 * Listener za prijavu i registraciju korisnika.
 */
public interface LoginListener {
    void onLoginSuccess(String message);
    void onLoginFail(String message);
}
