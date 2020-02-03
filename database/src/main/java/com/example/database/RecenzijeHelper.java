package com.example.database;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.database.EntitiesFirebase.Komentar;

import com.example.database.FirebaseHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.example.database.Listeners.RecenzijaListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
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
        if (!provjeriDostupnostMreze()) return;
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

    public void dohvatiRecenzijePremaId(final int idZnamenitost){
        if (!provjeriDostupnostMreze()) return;
        Log.d("LukaEna", "Dohvaćanje reccenzija prema ID");
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

    public void dohvatiRecenzijePremaKorisniku(final String userId){
        if (provjeriDostupnostMreze()){
            mQuery = mDatabase.child("komentar");
            mQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final List<Komentar> listaRecenzija = new ArrayList<>();
                    Log.d("Anja", "Dobavljanje..");
                    for (DataSnapshot temp : dataSnapshot.getChildren()){
                        Log.d("hehe", "Pukne nakon " + temp.getKey());
                        for(DataSnapshot temp2: temp.getChildren()){
                            Log.d("LukaEna",temp2.getKey());
                            if(userId.equals(temp2.getKey())){
                                Komentar komentar2 = temp2.getValue(Komentar.class);
                                komentar2.setUid(mAuth.getUid());
                                Log.d("hehe", komentar2.getOpis());
                                listaRecenzija.add(komentar2);}
                    }}
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


    public void zapisiRecenziju(final String opis, final int ocjena, final int idZnamenitost){
        if (!provjeriDostupnostMreze()) return;
        final DatabaseReference aKomentar = mDatabase.child("komentar");
        Log.d("Anja3:","ovdje sam");
        aKomentar.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(Integer.toString(idZnamenitost)).hasChild(mAuth.getUid())){
                    Toast.makeText(mContext, "Recenzija već postavljena", Toast.LENGTH_LONG).show();

                }
                else{
                    Komentar noviKomentar = new Komentar(7,opis,ocjena, mAuth.getUid(),idZnamenitost,0,0);
                    aKomentar.child(Integer.toString(idZnamenitost)).child(mAuth.getUid()).setValue(noviKomentar);
                    Toast.makeText(mContext, "Uspješna recenzija", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void provjeriOcjenuKomentara(String otherUId, final int idZnamenitosti){
        if (checkIfSignedIn() && provjeriDostupnostMreze()){
            mQuery = mDatabase.child("Like").child(mAuth.getUid()).child(otherUId);
            //mQuery = mDatabase.child("Like").child(mAuth.getUid()).child(otherUId).child(Integer.toString(idZnamenitosti));
            mQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d("PromjenaTag", "onDataChange");
                    if (dataSnapshot.hasChild(Integer.toString(idZnamenitosti))){
                        String mojaOcjenaKomentara =
                                (String) dataSnapshot.child(Integer.toString(idZnamenitosti)).child("vote").getValue();
                        recenzijaListener.onOcjenaKomentaraSucess("Dohvaćena ocjena komentara!", mojaOcjenaKomentara);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    recenzijaListener.onLoadRecenzijaFail("Nije dohvaćena ocjena komentara!");
                }
            });
        }
    }

    public void postaviOcjenu(String otherUId, int idZnamenitosti, String ocjena){
        Log.d("NjAnja", "Postavlja ocjenu... " + ocjena + " sa idZnam " + Integer.toString(idZnamenitosti) + " otherUID " + otherUId);
        mDatabase.child("Like").child(mAuth.getUid()).child(otherUId).child(Integer.toString(idZnamenitosti)).child("vote").setValue(ocjena);
        mQuery = mDatabase.child("Komentar").child(Integer.toString(idZnamenitosti)).child(otherUId);
        if (ocjena.equals("Like")){
            Log.d("NjAnja","PromijeniVrijednost ocjene...");
            promijeniVrijednostOcjene(otherUId, idZnamenitosti, "brojUp", "povecaj");
            Log.d("NjAnja","PromijeniVrijednost ocjene...Done");
        }
        else{
            promijeniVrijednostOcjene(otherUId, idZnamenitosti, "brojDown", "povecaj");
        }
        //korisnik dobiva bodove
    }

    private void promijeniVrijednostOcjene(final String otherUId, final int idZnamenitosti, final String smjer, final String radnja){
        mQuery = mDatabase.child("komentar").child(Integer.toString(idZnamenitosti)).child(otherUId).child(smjer);
        mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int broj = Integer.parseInt(dataSnapshot.getValue().toString());
                if (radnja.equals("povecaj")) broj++; //plus 1 lajk
                else broj--;
                Log.d("NjAnja", "!!Spremanje broja...");
                mDatabase.child("komentar")
                        .child(Integer.toString(idZnamenitosti))
                        .child(otherUId)
                        .child(smjer).setValue(broj);
                Log.d("NjAnja", "!!Spremanje broja...DONE");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("FirebaseTag","aAnja");
            }
        });
    }

    public void obrisiOcjenu(String otherUId, int idZnamenitosti, String ocjena){
        mDatabase.child("Like").child(mAuth.getUid()).child(otherUId).child(Integer.toString(idZnamenitosti)).child("vote").removeValue();
        Log.d("kuda","dosao tu");
        mQuery = mDatabase.child("Komentar").child(Integer.toString(idZnamenitosti)).child(otherUId);
        if (ocjena.equals("Like")){
            Log.d("NjAnja","PromijeniVrijednost ocjene...");
            promijeniVrijednostOcjene(otherUId, idZnamenitosti, "brojUp", "smanji");
            Log.d("NjAnja","PromijeniVrijednost ocjene...Done");
        }
        else{
            promijeniVrijednostOcjene(otherUId, idZnamenitosti, "brojDown", "smanji");
        }
        //korisnik gubi bodove
    }
}
