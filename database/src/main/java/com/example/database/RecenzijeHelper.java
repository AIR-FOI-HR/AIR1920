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
    double zbrOcjena=0;
    double brRec=0;
    double vrijednost;
    DatabaseReference mDataZnamenitist;

    public void dohvatiRecenzijePremaId(final int idZnamenitost){
        if (!provjeriDostupnostMreze()) return;
        mQuery = mDatabase.child("komentar").child(Integer.toString(idZnamenitost));
        mDataZnamenitist=mDatabase.child("znamenitost").child(Integer.toString(idZnamenitost));
        mQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Komentar> listaRecenzija = new ArrayList<>();
                for (DataSnapshot temp : dataSnapshot.getChildren()){
                    Komentar komentar = new Komentar();
                    komentar = temp.getValue(Komentar.class);
                    komentar.setUid(temp.getKey());
                    komentar.setIdZnamenitost(idZnamenitost);
                    zbrOcjena+=komentar.getOcjena();
                    brRec+=1;
                    listaRecenzija.add(komentar);
                }
                if (zbrOcjena!=0){
                    vrijednost=zbrOcjena/brRec;
                    Double toBeTruncated = new Double(vrijednost);
                    Double truncatedDouble = BigDecimal.valueOf(toBeTruncated)
                        .setScale(2, RoundingMode.HALF_UP)
                        .doubleValue();
                    ukupno=truncatedDouble;
                    mDataZnamenitist.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild("averageGrade")){
                                mDataZnamenitist.child("averageGrade").setValue(ukupno);
                            }
                            else{
                                mDataZnamenitist.child("averageGrade").setValue(ukupno);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
                else{
                    ukupno=0;
                }
                recenzijaListener.onLoadRecenzijaSucess("Uspješno dohvaćanje - listener", listaRecenzija);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                recenzijaListener.onLoadRecenzijaFail("Neuspješno dohvaćanje - listener");
            }
        });
    }


    public void zapisiRecenziju(final String opis, final int ocjena, final int idZnamenitost){
        if (!provjeriDostupnostMreze()) return;
        final DatabaseReference aKomentar = mDatabase.child("komentar");
        aKomentar.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(Integer.toString(idZnamenitost)).hasChild(mAuth.getUid())){


                }
                else{
                    Komentar noviKomentar = new Komentar(7,opis,ocjena, mAuth.getUid(),idZnamenitost,0,0);
                    aKomentar.child(Integer.toString(idZnamenitost)).child(mAuth.getUid()).setValue(noviKomentar);
                    bodoviZaRecenziju();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    private void bodoviZaRecenziju(){
        mQuery = mDatabase.child("Korisnik").child(mAuth.getUid()).child("progressbar");
        mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("Bodovi", "Dohvaćamo bodove...");
                int progresBroj = Integer.parseInt(dataSnapshot.getValue().toString());
                progresBroj=progresBroj+150;
                Log.d("Bodovi", "Progress +50 " + Integer.toString(progresBroj));
                mDatabase.child("Korisnik").child(mAuth.getUid()).child("progressbar").setValue(progresBroj);
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
        mDatabase.child("Like").child(mAuth.getUid()).child(otherUId).child(Integer.toString(idZnamenitosti)).child("vote").setValue(ocjena);
        mQuery = mDatabase.child("Komentar").child(Integer.toString(idZnamenitosti)).child(otherUId);
        if (ocjena.equals("Like")){
            promijeniVrijednostOcjene(otherUId, idZnamenitosti, "brojUp", "povecaj");
        }
        else{
            promijeniVrijednostOcjene(otherUId, idZnamenitosti, "brojDown", "povecaj");
        }


    }


    public void bodoviZaLikeProgressBarKorisnikKojiGaJeDao(){
        mQuery = mDatabase.child("Korisnik").child(mAuth.getUid()).child("progressbar");
        mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("Bodovi", "Dohvaćamo bodove...");
                int progresBroj = Integer.parseInt(dataSnapshot.getValue().toString());
                progresBroj=progresBroj+50;
                Log.d("Bodovi", "Progress +50 " + Integer.toString(progresBroj));
                mDatabase.child("Korisnik").child(mAuth.getUid()).child("progressbar").setValue(progresBroj);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    public void bodoviAkojeKorisnikOdustao(){
        mQuery = mDatabase.child("Korisnik").child(mAuth.getUid()).child("progressbar");
        mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("Bodovi", "Dohvaćamo bodove...");
                int progresBroj = Integer.parseInt(dataSnapshot.getValue().toString());
                progresBroj=progresBroj-50;
                Log.d("Bodovi", "Progress +50 " + Integer.toString(progresBroj));
                mDatabase.child("Korisnik").child(mAuth.getUid()).child("progressbar").setValue(progresBroj);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }





    private void bodoviZaLikeanuRecenziju(final String otherUid){
        mQuery = mDatabase.child("Korisnik").child(otherUid).child("progressbar");
        mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int progresBroj = Integer.parseInt(dataSnapshot.getValue().toString());
                progresBroj = progresBroj+100;
                mDatabase.child("Korisnik").child(otherUid).child("progressbar").setValue(progresBroj);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void bodoviZaDisLikeanuRecenziju(final String otherUid){
        mQuery = mDatabase.child("Korisnik").child(otherUid).child("progressbar");
        mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int progresBroj = Integer.parseInt(dataSnapshot.getValue().toString());
                progresBroj=progresBroj-100;
                mDatabase.child("Korisnik").child(otherUid).child("progressbar").setValue(progresBroj);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    public void promijeniVrijednostOcjene(final String otherUId, final int idZnamenitosti, final String smjer, final String radnja){
        mQuery = mDatabase.child("komentar").child(Integer.toString(idZnamenitosti)).child(otherUId).child(smjer);
        mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int broj = Integer.parseInt(dataSnapshot.getValue().toString());
                if (radnja.equals("povecaj")) {
                    //plus 1 lajk
                    broj++;
                    if(smjer.equals("brojUp")){
                        bodoviZaLikeanuRecenziju(otherUId);
                    }
                    else{
                        bodoviZaDisLikeanuRecenziju(otherUId);
                    }
                }
                else{
                    broj--;
                    if(smjer.equals("brojUp")){

                        bodoviZaDisLikeanuRecenziju(otherUId);
                    }
                    else{
                        bodoviZaLikeanuRecenziju(otherUId);

                    }
                }
                mDatabase.child("komentar")
                        .child(Integer.toString(idZnamenitosti))
                        .child(otherUId)
                        .child(smjer).setValue(broj);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void obrisiOcjenu(String otherUId, int idZnamenitosti, String ocjena){
        mDatabase.child("Like").child(mAuth.getUid()).child(otherUId).child(Integer.toString(idZnamenitosti)).child("vote").removeValue();
        mQuery = mDatabase.child("komentar").child(Integer.toString(idZnamenitosti)).child(otherUId);
        if (ocjena.equals("Like")){
            promijeniVrijednostOcjene(otherUId, idZnamenitosti, "brojUp", "smanji");
        }
        else{
            promijeniVrijednostOcjene(otherUId, idZnamenitosti, "brojDown", "smanji");
        }

    }
}
