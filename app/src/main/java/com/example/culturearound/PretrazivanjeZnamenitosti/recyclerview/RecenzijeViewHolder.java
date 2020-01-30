package com.example.culturearound.PretrazivanjeZnamenitosti.recyclerview;

import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ImageView;

import android.content.Context;
import androidx.annotation.NonNull;
import com.bignerdranch.expandablerecyclerview.ParentViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.squareup.picasso.Picasso;
import com.example.culturearound.Firebase.EntitiesFirebase.Komentar;
import com.example.culturearound.Firebase.EntitiesFirebase.Korisnik;
import com.example.culturearound.Firebase.Listeners.UserListener;
import com.example.culturearound.Firebase.UserHelper;
import com.example.culturearound.R;

public class RecenzijeViewHolder extends ParentViewHolder implements UserListener {
   private UserHelper userHelper;
   Komentar komentar;
   Korisnik korisnik;
   Context context;
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


    /**
     * Default constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     */
    public RecenzijeViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindToData (Komentar komentar, Context context){
        this.context = context;

        Log.d("Anja", "RecViewHold: postavljanje komentara");
        this.komentar = komentar;
        Log.d("Anja", "Dohvati uid: " + komentar.getUid());

        Log.d("Anja", "Ucitavanje userHelpera...");
        userHelper = new UserHelper(context, this);
        Log.d("Anja", "Ucitavanje userHelpera...DONE");

        userHelper.findUserById(komentar.getUid());
    }

    private void ucitajPodatke() {
        Log.d("Anja", "Setaj--opis");

        String opiss = komentar.getOpis();
        //int ocjenaa = komentar.getOcjena();
        Log.d("Anja", opiss);
        opis.setText(opiss);

        Log.d("Anja", "Setaj--ocjenu");
        ocjena.setText(Integer.toString(komentar.getOcjena()));


        rate.setRating(komentar.getOcjena());
        Log.d("Anja1", "Setano sve");

        korime.setText(korisnik.getIme() + " " + korisnik.getPrezime());
        Log.d("Anja", "slika"+ korisnik.getLokacijaSlike());
        Picasso.with(itemView.getContext())
                .load(korisnik.getLokacijaSlike())
                .into(slika);

    }

    @Override
    public void onLoadUserSuccess(String message, Korisnik currentUser) {
        korisnik = currentUser;
        ucitajPodatke();
    }

    @Override
    public void onLoadUserFail(String message) {
        Log.d("Anja", message);
    }
}
