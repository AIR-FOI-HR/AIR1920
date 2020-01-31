package com.example.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.database.EntitiesFirebase.Korisnik;
import com.example.database.EntitiesFirebase.Znamenitost;
import com.example.database.Listeners.UserListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class UserHelper extends FirebaseHelper {

    private final UserListener userListener;
    //public String userId ;
    //public FirebaseUser user ;

    public UserHelper(Context context, UserListener userListener) {
        mAuth = FirebaseAuth.getInstance();
        mContext = context;
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();
        this.userListener = userListener;
        //userId= mAuth.getCurrentUser().getUid();
        //user = mAuth.getCurrentUser();
    }

    public String returnUserId () {
        String userId = mAuth.getCurrentUser().getUid();
        return userId ;
    }


    public void findUserById (final String userId) {
        if(provjeriDostupnostMreze()) {
            mQuery = mDatabase.child("Korisnik").child(userId);
            mQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Korisnik currentUser = new Korisnik();
                    currentUser = dataSnapshot.getValue(Korisnik.class);
                    currentUser.setUid(userId);
                    List<Znamenitost> listOfFavorites = new ArrayList<>();
                    for (DataSnapshot ds : dataSnapshot.child("listaSpremljenihZnamenitosti").getChildren()){
                        Znamenitost znamenitost = ds.getValue(Znamenitost.class);
                        znamenitost.setIdZnamenitosti(Integer.parseInt(ds.getKey()));
                        listOfFavorites.add(znamenitost);
                        Log.d("user", Integer.toString(znamenitost.getIdZnamenitosti()));
                    }
                    currentUser.setListaSpremljenihZnamenitosti(listOfFavorites);
                    userListener.onLoadUserSuccess("Uspjesno dohvaćanje- listener", currentUser);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    userListener.onLoadUserFail("Neuspješno dohvaćanje- listener");
                }
            });
        }
    }

    public void updateData(String firstName, String lastName, String email, String pictureUrl, final String userId) {

        if (firstName != "") {
            mDatabase.child("Korisnik").child(userId).child("ime").setValue(firstName);
        }

        if (lastName != "") {
            mDatabase.child("Korisnik").child(userId).child("prezime").setValue(lastName);
        }

        if (email != "") {
            mDatabase.child("Korisnik").child(userId).child("email").setValue(email);
            mAuth.getCurrentUser().updateEmail(email);
        }

        if (pictureUrl != "") {
            mDatabase.child("Korisnik").child(userId).child("lokacijaSlike").setValue(pictureUrl);
        }
    }


}

