package com.example.culturearound.Firebase;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ZnamenitostiHelper extends FirebaseHelper {

    public ZnamenitostiHelper(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mContext = context;
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();
    }
}
