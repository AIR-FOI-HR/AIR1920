package com.example.default_znamenitost.recyclerView;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.example.database.Listeners.RecenzijaListener;
import com.example.database.EntitiesFirebase.Komentar;
import com.example.database.EntitiesFirebase.Korisnik;
import com.example.database.Listeners.UserListener;
import com.example.database.RecenzijeHelper;
import com.example.database.UserHelper;
import com.example.default_znamenitost.R;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecenzijeViewHolder extends ParentViewHolder implements UserListener, RecenzijaListener {
    Context context;
    private UserHelper userHelper;
    private RecenzijeHelper recenzijeHelper;


    Komentar komentar;
    Korisnik korisnik;
    String mojaOcjenaKomentara="";

    TextView opis;
    TextView ocjena;
    TextView korime;
    ImageView slika;
    RatingBar rate;
    TextView up;
    TextView down;
    public ImageButton btnLike;
    public ImageButton btnDislike;

    private boolean mProcess=false;
    String rface;
    String rface2;

    /**
     * Default constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     */
    public RecenzijeViewHolder(@NonNull View itemView) {
        super(itemView);

        opis = itemView.findViewById(R.id.recenzija_opis);
        ocjena = itemView.findViewById(R.id.recenzija_ocjena);
        korime = itemView.findViewById(R.id.korime);
        slika = itemView.findViewById(R.id.korinsnik_image);
        rate = itemView.findViewById(R.id.recenzija_ocjena_rate);
        up = itemView.findViewById(R.id.recenzija_up);
        down = itemView.findViewById(R.id.recenzija_down);
        btnLike = (ImageButton) itemView.findViewById(R.id.likeButton);
        btnDislike = (ImageButton) itemView.findViewById(R.id.dislikeButton);
    }

    //Bind data i prikazivanje komentarovih podataka
    public void bindToData (Komentar komentar, Context context){
        this.context = context;
        this.komentar = komentar;
        Log.d("NjAnja", "Novi user helper");
        userHelper = new UserHelper(context, this);
        userHelper.findUserById(komentar.getUid());
        Log.d("NjAnja", "Novi recenzije helper");
        recenzijeHelper = new RecenzijeHelper(itemView.getContext(), this);

        Log.d("NjAnja", "Opis...");
        //Prikaz komentarovih podataka
        opis.setText(komentar.getOpis());
        Log.d("NjAnja", "Ocjena...");
        //PAZI set ocjena
        int ocjenaa = komentar.getOcjena();
        ocjena.setText(Integer.toString(ocjenaa));
        rate.setRating(komentar.getOcjena());

        Log.d("NjAnja", "BrojUPnDown...");
        int brojUp = komentar.getBrojUp();
        int brojDown = komentar.getBrojDown();
        up.setText(Integer.toString(brojUp));
        down.setText(Integer.toString(brojDown));
        Log.d("NjAnja", "KrajBinda");
    }


    //Postavljanje korisničkih podataka
    private void ucitajPodatkeKorisnika() {
        //Učitaj ocjene
        recenzijeHelper.provjeriOcjenuKomentara(korisnik.getUid(), komentar.getIdZnamenitost());

        korime.setText(korisnik.getIme() + " " + korisnik.getPrezime());
        Log.d("NjAnja", "slika"+ korisnik.getLokacijaSlike());
        Picasso.with(itemView.getContext())
                .load(korisnik.getLokacijaSlike())
                .into(slika);

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!recenzijeHelper.checkIfSignedIn()){
                    Snackbar.make(v, "Potrebno se je ulogirat za ocjenjivanje komentara.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                Log.d("NjAnja", "Pocetak klika like.");
                if (mojaOcjenaKomentara.equals("Like")){
                    recenzijeHelper.obrisiOcjenu(korisnik.getUid(), komentar.getIdZnamenitost(), "Like");
                    recenzijeHelper.bodoviAkojeKorisnikOdustao();
                    Log.d("kuda", "dosao sam tu");
                    mojaOcjenaKomentara="";
                }
                else {
                    if(mojaOcjenaKomentara.equals("Dislike")){
                        recenzijeHelper.postaviOcjenu(korisnik.getUid(), komentar.getIdZnamenitost(), "Like");
                        recenzijeHelper.promijeniVrijednostOcjene(korisnik.getUid(), komentar.getIdZnamenitost(), "brojDown", "smanji");
                        mojaOcjenaKomentara="Like";
                    }
                    else{
                        recenzijeHelper.postaviOcjenu(korisnik.getUid(), komentar.getIdZnamenitost(), "Like");
                        recenzijeHelper.bodoviZaLikeProgressBarKorisnikKojiGaJeDao();
                        mojaOcjenaKomentara="Like";
                    }

                }
                prikaziOcjenuKomentara();
                Log.d("kuda", "Kraj klika like.");
            }
        });//end of btnLike

        btnDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!recenzijeHelper.checkIfSignedIn()){
                    Snackbar.make(v, "Potrebno se je ulogirat za ocjenjivanje komentara.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                Log.d("NjAnja", "Pocetak klika dislike.");
                if (mojaOcjenaKomentara.equals("Dislike")){
                    recenzijeHelper.obrisiOcjenu(korisnik.getUid(), komentar.getIdZnamenitost(), "Dislike");
                    recenzijeHelper.bodoviAkojeKorisnikOdustao();
                    mojaOcjenaKomentara="";
                }
                else{

                    if(mojaOcjenaKomentara.equals("Like")){

                        recenzijeHelper.postaviOcjenu(korisnik.getUid(), komentar.getIdZnamenitost(), "Dislike");
                        recenzijeHelper.promijeniVrijednostOcjene(korisnik.getUid(), komentar.getIdZnamenitost(), "brojUp", "smanji");
                        mojaOcjenaKomentara="Dislike";
                    }
                    else{
                        recenzijeHelper.postaviOcjenu(korisnik.getUid(), komentar.getIdZnamenitost(), "Dislike");
                        recenzijeHelper.bodoviZaLikeProgressBarKorisnikKojiGaJeDao();
                        mojaOcjenaKomentara="Dislike";
                    }


                }
                prikaziOcjenuKomentara();
                Log.d("NjAnja", "Kraj klika dislike.");
            }
        });//end of btnDislike
    }

    private void prikaziOcjenuKomentara(){
        if (mojaOcjenaKomentara.equals("Like")){
            Log.d("NjAnja", "moja ocjena je Like");
            btnLike.setImageResource(R.mipmap.ic_like_reens_round);
            btnDislike.setImageResource(R.mipmap.ic_dislike_white_round);
        }
        else if (mojaOcjenaKomentara.equals("Dislike")){
            Log.d("NjAnja", "moja ocjena je Disike");
            btnLike.setImageResource(R.mipmap.ic_like_white_round);
            btnDislike.setImageResource(R.mipmap.ic_dislike_red_round);
        }
        else{ //ništa lajkano ni dislajkano
            Log.d("NjAnja", "moja ocjena je gg");
            btnLike.setImageResource(R.mipmap.ic_like_white_round);
            btnDislike.setImageResource(R.mipmap.ic_dislike_white_round);
        }
    }

    @Override
    public void onLoadUserSuccess(String message, Korisnik currentUser) {
        Log.d("NjAnja", message);
        korisnik = currentUser;
        ucitajPodatkeKorisnika();
    }

    @Override
    public void onLoadUserFail(String message) {
        Log.d("NjAnja", message);
    }

    @Override
    public void onLoadRecenzijaSucess(String message, List<Komentar> listaKomentara) {
        //generirano
    }

    @Override
    public void onOcjenaKomentaraSucess(String message, String mojaOcjenaKomentara) {
        Log.d("NjAnja", message);
        if (mojaOcjenaKomentara == null) mojaOcjenaKomentara = "";

        this.mojaOcjenaKomentara = mojaOcjenaKomentara;
        prikaziOcjenuKomentara();
    }

    @Override
    public void onLoadRecenzijaFail(String message) {
        Log.d("NjAnja", message);
    }
}
