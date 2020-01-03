package com.example.culturearound.Firebase;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class FirebaseHelper {

    //instanca FirebaseAutorizacije - usluga Authentication
    protected FirebaseAuth mAuth;

    //kontekst koristimo za toast
    protected Context mContext;

    //instanca FirebaseDatabase - usluga Database
    FirebaseDatabase database;
    //referenca na podatak unutar baze podataka
    DatabaseReference mDatabase;
}
