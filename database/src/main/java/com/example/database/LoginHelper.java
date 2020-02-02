package com.example.database;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.database.EntitiesFirebase.Korisnik;
import com.example.database.Listeners.LoginListener;
import com.example.database.FirebaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginHelper extends FirebaseHelper {
    private LoginListener mLoginListener;


    public LoginHelper(LoginListener context) {
        mAuth = FirebaseAuth.getInstance();
        mContext = (Context) context;
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();

        mLoginListener = context;
    }

    /**
     * Provjera je li korisnik ulogiran.
     * @return
     */

    /**
     * Login koristeci email i zaporku.
     * @param email
     * @param password
     */
    public void signIn(String email, String password) {
        if(provjeriDostupnostMreze()){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Log.d("FirebaseTag", "signInWithEmail:success");

                                mLoginListener.onLoginSuccess("Prijava uspješna.");
                            }
                            else {
                                Log.w("FirebaseTag", "signInWithEmail:failure", task.getException());

                                mLoginListener.onLoginFail("Prijava neuspješna.");
                            }
                        }
                    });
        }
    }

    /**
     * Registracija korisnika
     * @param ime
     * @param prezime
     * @param email
     * @param password
     */
    public void createAccount(final String ime, final String prezime, final String email, final String password) {
        if(provjeriDostupnostMreze()){
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("FirebaseTag", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                zapisiKorisnikaNaFirebase(user.getUid(), ime, prezime, email, password);

                                mLoginListener.onLoginSuccess("Registracija uspješna");
                            } else {
                                Log.w("FirebaseTag", "createUserWithEmail:failure", task.getException());

                                mLoginListener.onLoginFail("Registracija neuspješna.");
                            }
                        }
                    });
        }
    }

    private void zapisiKorisnikaNaFirebase(String uid, String ime, String prezime, String email, String lozinka) {
        DatabaseReference rKorisnik = mDatabase.child("Korisnik");
        Korisnik noviKorisnik = new Korisnik(uid, ime, prezime, email, lozinka, "https://www.speakingtigerbooks.com/wp-content/uploads/2017/07/no-avatar.png");
        rKorisnik.child(uid).setValue(noviKorisnik);
    }

    /**
     * Obnavljanje zaporke tako što se šalje poruka na email.
     * @param email
     */


    public void resetPassword(String email){
        if(provjeriDostupnostMreze()){
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(mContext, "Poslana je poruka za obnovu na email.",
                                        Toast.LENGTH_LONG).show();
                                Log.d("FirebaseTag", "Zaboravljena lozinka - email poslan.");
                            }
                        }
                    });
        }
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(mContext, "Korisnik uspješno odjavljen.",
                Toast.LENGTH_LONG).show();
        Log.d("FirebaseTag", "Korisnik odjavljan.");
    }
}
