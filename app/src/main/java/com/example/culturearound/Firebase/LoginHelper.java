package com.example.culturearound.Firebase;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.culturearound.Firebase.EntitiesFirebase.Korisnik;
import com.example.culturearound.RegistryActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginHelper extends FirebaseHelper{

    public LoginHelper(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mContext = context;
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();
    }

    /**
     * Provjera je li korisnik ulogiran.
     * @return
     */
    public boolean checkIfSignedIn(){
        if(mAuth.getUid() == null) {
            Toast.makeText(mContext,"Nije ulogiran korisnik", Toast.LENGTH_SHORT).show();
            Log.d("FirebaseTag", "Nije ulogiran korisnik");
            return false;
        }
        else {
            Toast.makeText(mContext,"Dobrodošao " + mAuth.getUid(), Toast.LENGTH_SHORT).show();
            Log.d("FirebaseTag", "Ulogiran korisnik");
            return true;
        }
    }

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
                                Toast.makeText(mContext,"Prijava uspješna.", Toast.LENGTH_SHORT).show();
                                Log.d("FirebaseTag", "signInWithEmail:success");
                            }
                            else {
                                Toast.makeText(mContext, "Prijava neuspješna.", Toast.LENGTH_SHORT).show();
                                Log.w("FirebaseTag", "signInWithEmail:failure", task.getException());
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
    public void createAccount(String ime, String prezime, String email, String password) {
        if(provjeriDostupnostMreze()){
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("FirebaseTag", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                Toast.makeText(mContext, "Registracija uspješna", Toast.LENGTH_SHORT).show();
                                zapisiKorisnikaNaFirebase(user.getUid(), ime, prezime, email, password);
                            } else {
                                Log.w("FirebaseTag", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(mContext, "Registracija neuspješna.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void zapisiKorisnikaNaFirebase(String uid, String ime, String prezime, String email, String lozinka) {
        DatabaseReference rKorisnik = mDatabase.child("Korisnik");
        Korisnik noviKorisnik = new Korisnik(ime, prezime, email, lozinka, 1);
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
