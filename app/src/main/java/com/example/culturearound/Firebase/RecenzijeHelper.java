package com.example.culturearound.Firebase;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import com.example.culturearound.Firebase.EntitiesFirebase.Komentar;
import com.google.firebase.auth.FirebaseAuth;
import com.example.culturearound.Firebase.Listeners.RecenzijaListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class RecenzijeHelper extends FirebaseHelper {
    private RecenzijaListener recenzijaListener;
    public RecenzijeHelper (Context context, RecenzijaListener recenzijaListener){
        mAuth = FirebaseAuth.getInstance();
        mContext = context;
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();
        this.recenzijaListener = recenzijaListener;

    }



    public double ukupno;



    public void dohvatiSveRecenzije(){
        mQuery = mDatabase.child("komentar");
        if (provjeriDostupnostMreze()){
            mQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d("Anja", "Dobavljanje..");
                    List<Komentar> listaRecenzija = new ArrayList<>();
                    for (DataSnapshot temp : dataSnapshot.getChildren()){
                        Komentar komentar = new Komentar();
                        komentar = temp.getValue(Komentar.class);
                        komentar.setIdKomentar(Integer.parseInt(temp.getKey()));
                        listaRecenzija.add(komentar);
                    }
                    Log.d("Anja", "Dobavljanje DONE");
                    recenzijaListener.onLoadRecenzijaSucess("Uspješno dohvaćanje - listener", listaRecenzija);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    recenzijaListener.onLoadRecenzijaFail("Neuspješno dohvaćanje - listener");
                }
            });
        }
    }

    double zbrOcjena=0;
    double brRec=0;
    double vrijednost;



    public void dohvatiRecenzijePremaId(int idZnamenitost){
        mQuery = mDatabase.child("komentar").child(Integer.toString(idZnamenitost));
        mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Komentar> listaRecenzija = new ArrayList<>();
                for (DataSnapshot temp : dataSnapshot.getChildren()){
                    Log.d("Anja1", "Dosao do tud ");
                    Komentar komentar = new Komentar();
                    komentar = temp.getValue(Komentar.class);
                    komentar.setUid(temp.getKey());
                    komentar.setIdZnamenitost(idZnamenitost);
                    Log.d("Anja", "Uid anja: " + komentar.getUid());
                    Log.d("Anja", "Idznam anja: " + komentar.getIdZnamenitost());
                    Log.d("Anja", "opis anja: " + komentar.getOpis());
                    zbrOcjena+=komentar.getOcjena();
                    brRec+=1;
                    listaRecenzija.add(komentar);
                }
                Log.d("Anja1", "Zbroj: " + zbrOcjena);
                Log.d("Anja1", "Broj:"+brRec);


                if (zbrOcjena!=0){
                    vrijednost=zbrOcjena/brRec;
                    Double toBeTruncated = new Double(vrijednost);
                    Double truncatedDouble = BigDecimal.valueOf(toBeTruncated)
                        .setScale(2, RoundingMode.HALF_UP)
                        .doubleValue();
                    ukupno=truncatedDouble;}

                else{
                    ukupno=0;
                }

                Log.d("Anja2", "BrojSve:"+ukupno);
                recenzijaListener.onLoadRecenzijaSucess("Uspješno dohvaćanje - listener", listaRecenzija);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                recenzijaListener.onLoadRecenzijaFail("Neuspješno dohvaćanje - listener");
            }
        });
    }










}
