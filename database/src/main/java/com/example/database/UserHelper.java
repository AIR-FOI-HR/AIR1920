package com.example.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.database.EntitiesFirebase.Korisnik;
import com.example.database.Listeners.UserListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


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

            mQuery.addValueEventListener(new ValueEventListener() {
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
    public void findUserById (final String id) {
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

    public void updateData(String firstName, String lastName, String email, String pictureUrl) {

        if (firstName != "") {
            mDatabase.child("Korisnik").child(userId).child("ime").setValue(firstName);
        }

        if (lastName != "") {
            mDatabase.child("Korisnik").child(userId).child("prezime").setValue(lastName);
        }

        if (email != "") {
            mDatabase.child("Korisnik").child(userId).child("email").setValue(email);
            user.updateEmail(email);
        }

        if (pictureUrl != "") {
            mDatabase.child("Korisnik").child(userId).child("lokacijaSlike").setValue(pictureUrl);
        }
    }

}

