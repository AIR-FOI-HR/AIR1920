package com.example.culturearound.ProfilKorisnika;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.example.culturearound.R;
import com.example.database.EntitiesFirebase.Komentar;
import com.example.database.EntitiesFirebase.Korisnik;
import com.example.database.Listeners.RecenzijaListener;
import com.example.database.Listeners.UserListener;
import com.example.database.RecenzijeHelper;
import com.example.database.UserHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileRecyclerHolder extends ParentViewHolder implements UserListener, RecenzijaListener {

    Context context;
    private UserHelper userHelper;
    private RecenzijeHelper recenzijeHelper;
    Komentar komentar;
    Korisnik korisnik;
    String mojaOcjenaKomentara="";


    @BindView(R.id.recenzija_opis)
    TextView opis;
    @BindView(R.id.recenzija_ocjena)
    TextView ocjena;
    @BindView(R.id.korime)
    TextView korime;
    @BindView(R.id.korinsnik_image)
    ImageView slika;
    @BindView(R.id.recenzija_ocjena_rate)
    RatingBar rate;
    @BindView(R.id.recenzija_up)
    TextView up;
    @BindView(R.id.recenzija_down)
    TextView down;
    public ImageButton btnLike;
    public ImageButton btnDislike;

    /**
     * Default constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     */
    public ProfileRecyclerHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        btnLike = (ImageButton) itemView.findViewById(R.id.likeButton);
        btnDislike = (ImageButton) itemView.findViewById(R.id.dislikeButton);
    }

    public void bindToData (Komentar komentar, Context context){
        this.context = context;
        this.komentar = komentar;
        userHelper = new UserHelper(context, this);
        userHelper.findUserById(komentar.getUid());
        recenzijeHelper = new RecenzijeHelper(itemView.getContext(), this);

        //Prikaz komentarovih podataka
        opis.setText(komentar.getOpis());
        rate.setRating(komentar.getOcjena());
        ocjena.setText(Integer.toString(komentar.getOcjena()));
        int brojUp = komentar.getBrojUp();
        int brojDown = komentar.getBrojDown();
        up.setText(Integer.toString(brojUp));
        down.setText(Integer.toString(brojDown));
    }

    private void ucitajPodatkeKorisnika() {
        //Učitaj ocjene
        recenzijeHelper.provjeriOcjenuKomentara(korisnik.getUid(), komentar.getIdZnamenitost());

        korime.setText(korisnik.getIme() + " " + korisnik.getPrezime());
        Log.d("NjAnja", "slika"+ korisnik.getLokacijaSlike());
        Picasso.with(itemView.getContext())
                .load(korisnik.getLokacijaSlike())
                .into(slika);
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
    public void onLoadRecenzijaSucess(String message, List<Komentar> listaKomentara) {

    }

    @Override
    public void onOcjenaKomentaraSucess(String message, String mojaOcjenaKomentara) {
        Log.d("NjAnja", message);
        if (mojaOcjenaKomentara == null) mojaOcjenaKomentara = "";

        Log.d("NjAnja", "Listener ocj komentara je " + mojaOcjenaKomentara);
        this.mojaOcjenaKomentara = mojaOcjenaKomentara;
        prikaziOcjenuKomentara();
    }

    @Override
    public void onLoadRecenzijaFail(String message) {

    }

    @Override
    public void onLoadUserSuccess(String message, Korisnik currentUser) {
        korisnik = currentUser;
        ucitajPodatkeKorisnika();
    }

    @Override
    public void onLoadUserFail(String message) {

    }
}
