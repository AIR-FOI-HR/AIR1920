package com.example.database;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.database.EntitiesFirebase.Znamenitost;
import com.example.database.Listeners.ZnamenitostListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ZnamenitostiHelper extends FirebaseHelper {
    private ZnamenitostListener znamenitostListener;

    public ZnamenitostiHelper(Context context, ZnamenitostListener znamenitostListener) {
        mAuth = FirebaseAuth.getInstance();
        mContext = context;
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();
        this.znamenitostListener = znamenitostListener;
    }

    public void dohvatiSveZnamenitosti(){
        mQuery = mDatabase.child("znamenitost");
        if (provjeriDostupnostMreze()){
            mQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<Znamenitost> listaZnamenitosti = new ArrayList<>();
                    for (DataSnapshot temp : dataSnapshot.getChildren()){
                        Znamenitost znamenitost = new Znamenitost();
                        znamenitost = temp.getValue(Znamenitost.class);
                        znamenitost.setIdZnamenitosti(Integer.parseInt(temp.getKey()));
                        listaZnamenitosti.add(znamenitost);
                    }
                    znamenitostListener.onLoadZnamenitostSucess("Uspješno dohvaćanje - listener", listaZnamenitosti);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    znamenitostListener.onLoadZnamenitostFail("Neuspješno dohvaćanje bajo moj - listener");
                }
            });
        }
    }

    public void dohvatiZnamenitostPremaId(int idZnamenitosti){
        mQuery = mDatabase.child("znamenitost").child(Integer.toString(idZnamenitosti));
        mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Znamenitost znamenitost = new Znamenitost();
                znamenitost = dataSnapshot.getValue(Znamenitost.class);
                znamenitost.setIdZnamenitosti(Integer.parseInt(dataSnapshot.getKey()));
                znamenitostListener.onLoadZnamenitostSucess("Uspješno dohvaćanje - listener", Arrays.asList(znamenitost));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                znamenitostListener.onLoadZnamenitostFail("Neuspješno dohvaćanje bajo moj - listener");
            }
        });
    }
}
