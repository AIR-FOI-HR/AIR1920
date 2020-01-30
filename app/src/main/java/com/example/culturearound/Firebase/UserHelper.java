package com.example.culturearound.Firebase;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.culturearound.Firebase.EntitiesFirebase.Korisnik;
import com.example.culturearound.Firebase.Listeners.UserListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;


public class UserHelper extends FirebaseHelper {

    private final UserListener userListener;
    public String userId ;
    public FirebaseUser user ;

    public UserHelper(Context context, UserListener userListener) {
        mAuth = FirebaseAuth.getInstance();
        Log.d("Anja", "Ucitavanje vrazji kontekst");
        mContext = context;
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();
        Log.d("Anja", "Ucitavanje vrazji kontekst...kretenski.");
        this.userListener = userListener;
        Log.d("Anja", "Kontekti uspjesni.");
        userId= mAuth.getCurrentUser().getUid();
        user = mAuth.getCurrentUser();
    }

    public void findUserById () {
        if(provjeriDostupnostMreze()) {
            mQuery = mDatabase.child("Korisnik").child(userId);

            mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Korisnik currentUser = new Korisnik();
                    currentUser = dataSnapshot.getValue(Korisnik.class);
                    currentUser.setUid(userId);

                    userListener.onLoadUserSuccess("Uspjesno dohvaćanje- listener", currentUser);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    userListener.onLoadUserFail("Neuspješno dohvaćanje usera- listener");
                }
            });
        }
    }
    public void findUserById (String id) {
        if(provjeriDostupnostMreze()) {
            mQuery = mDatabase.child("Korisnik").child(id);

            mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Korisnik currentUser = new Korisnik();
                    currentUser = dataSnapshot.getValue(Korisnik.class);
                    currentUser.setUid(id);
                    userListener.onLoadUserSuccess("Uspjesno dohvaćanje usera- listener", currentUser);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    userListener.onLoadUserFail("Neuspješno dohvaćanje usera- listener");
                }
            });
        }
    }


}

