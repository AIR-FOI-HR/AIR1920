package com.example.culturearound.Firebase;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.culturearound.Firebase.EntitiesFirebase.Znamenitost;
import com.example.culturearound.Firebase.Listeners.ZnamenitostListener;
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
        Log.d("FirebaseTag", "Konstruktor ZnamenitostiHelper");
        mAuth = FirebaseAuth.getInstance();
        Log.d("FirebaseTag", "Postavljanje konteksta...");
        mContext = context;
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();
        Log.d("FirebaseTag", "Postavljanje listenera...");
        this.znamenitostListener = znamenitostListener;
    }

    public void dohvatiSveZnamenitosti(){
        mQuery = mDatabase.child("znamenitost");
        Log.d("FirebaseTag", "Krećem dohvaćati Znamenitosti.");
        if (provjeriDostupnostMreze()){
            Log.d("FirebaseTag", "Query listener će sjesti...");
            mQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d("FirebaseTag", "Dohvaćanje djece...");
                    List<Znamenitost> listaZnamenitosti = new ArrayList<>();
                    for (DataSnapshot temp : dataSnapshot.getChildren()){
                        Znamenitost znamenitost = new Znamenitost();
                        Log.d("FirebaseTag", "... spremam ostatak...");
                        znamenitost = temp.getValue(Znamenitost.class);
                        Log.d("FirebaseTag", "Dohvaćeno dijete...spremam id...");
                        znamenitost.setIdZnamenitosti(Integer.parseInt(temp.getKey()));
                        listaZnamenitosti.add(znamenitost);
                    }
                    Log.d("FirebaseTag", "Dohvaćene znamenitosti");
                    znamenitostListener.onLoadSucess("Uspješno dohvaćanje - listener", listaZnamenitosti);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("FirebaseTag", "Nisu dohvaćene znamenitosti");
                    Toast.makeText(mContext, "Nisi uspio skinut podatke.", Toast.LENGTH_LONG).show();
                    znamenitostListener.onLoadFail("Neuspješno dohvaćanje bajo moj - listener");
                }
            });
        }
        Log.d("FirebaseTag", "Sjeo query listener.");
    }

    public void dohvatiZnamenitostPremaId(int idZnamenitosti){
        mQuery = mDatabase.child("znamenitost").child(Integer.toString(idZnamenitosti));
        mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Znamenitost znamenitost = new Znamenitost();
                znamenitost = dataSnapshot.getValue(Znamenitost.class);
                znamenitost.setIdZnamenitosti(Integer.parseInt(dataSnapshot.getKey()));
                znamenitostListener.onLoadSucess("Uspješno dohvaćanje - listener", Arrays.asList(znamenitost));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("FirebaseTag", "Nije dohvaćena znamenitost");
                Toast.makeText(mContext, "Nisi uspio skinut podatke o znamenitosti.", Toast.LENGTH_LONG).show();
                znamenitostListener.onLoadFail("Neuspješno dohvaćanje bajo moj - listener");
            }
        });
    }
}
