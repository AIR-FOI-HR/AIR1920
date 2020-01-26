package com.example.culturearound.Firebase;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.culturearound.Firebase.EntitiesFirebase.Lokacija;
import com.example.culturearound.Firebase.Listeners.LokacijaListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LokacijaHelper extends FirebaseHelper {
    private LokacijaListener lokacijaListener;

    public LokacijaHelper(Context context, LokacijaListener lokacijaListener) {
        mAuth = FirebaseAuth.getInstance();
        mContext = context;
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();
        this.lokacijaListener = lokacijaListener;
    }

    public void dohvatiSveLokacije(){
        mQuery = mDatabase.child("lokacija");
        if (provjeriDostupnostMreze()){
            mQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<Lokacija> listaLokacija = new ArrayList<>();
                    for (DataSnapshot temp : dataSnapshot.getChildren()){
                        Lokacija lokacija = new Lokacija();
                        lokacija = temp.getValue(Lokacija.class);
                        lokacija.setIdLokacija(Integer.parseInt(temp.getKey()));
                        listaLokacija.add(lokacija);
                    }
                    lokacijaListener.onLoadLokacijaSucess("Uspješno dohvaćanje - listener", listaLokacija);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    lokacijaListener.onLoadLokacijaFail("Neuspješno dohvaćanje - listener");
                }
            });
        }
    }
}
