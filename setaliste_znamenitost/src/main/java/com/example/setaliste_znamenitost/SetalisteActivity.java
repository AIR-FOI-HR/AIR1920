package com.example.setaliste_znamenitost;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.database.EntitiesFirebase.Korisnik;
import com.example.database.EntitiesFirebase.Lokacija;
import com.example.database.EntitiesFirebase.Znamenitost;
import com.example.database.Listeners.LokacijaListener;
import com.example.database.Listeners.UserListener;
import com.example.database.Listeners.ZnamenitostListener;
import com.example.database.LokacijaHelper;
import com.example.database.UserHelper;
import com.example.database.ZnamenitostiHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SetalisteActivity extends AppCompatActivity implements ZnamenitostListener, LokacijaListener, UserListener {

    private TextView txtNaziv;
    private ZnamenitostiHelper znamenitostiHelper;
    private LokacijaHelper lokacijaHelper;
    private ImageView imgNaslovnaSlika;
    private TextView txtAdresaZnamenitosti;
    private TextView txtLokacijaZnamenitosti;
    private TextView txtOpisZnamenitosti;

    private UserHelper userHelper;
    private Korisnik currentUser;
    private Znamenitost izabranaZnamenitost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setaliste);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userHelper = new UserHelper(this, this);
        if(userHelper.checkIfSignedIn()){
            userHelper.findUserById(userHelper.returnUserId());
        }

        znamenitostiHelper = new ZnamenitostiHelper((Context) this, this);
        lokacijaHelper = new LokacijaHelper((Context) this, this);

        txtNaziv = (TextView) findViewById(R.id.txtNaziv);
        imgNaslovnaSlika = (ImageView) findViewById(R.id.imgNaslovnaSlika);
        txtAdresaZnamenitosti = (TextView) findViewById(R.id.txtAdresaZnamenitosti);
        txtLokacijaZnamenitosti = (TextView) findViewById(R.id.txtLokacijaZnamenitosti);
        txtOpisZnamenitosti = (TextView) findViewById(R.id.txtOpisZnamenitosti);
    }

    @Override
    protected void onResume(){
        super.onResume();

        Intent intent = getIntent();
        int znamId = intent.getIntExtra("id_znamenitost", -1);

        if(znamId != -1){
            znamenitostiHelper.dohvatiZnamenitostPremaId(znamId);
        }
    }

    private void prikaziPodatkeZnamenitosti(Znamenitost znamenitost){
        txtNaziv.setText(znamenitost.getNaziv());
        Picasso.with(this)
                .load(znamenitost.getLokacijaSlike())
                .into(imgNaslovnaSlika);
        txtAdresaZnamenitosti.setText(znamenitost.getAdresa());
        lokacijaHelper.dohvatiZnamenitostPremaId(znamenitost.getIdLokacija());
        txtOpisZnamenitosti.setText(znamenitost.getOpis());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(currentUser != null) {

            isItInFavourites(fab, izabranaZnamenitost.getIdZnamenitosti());
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!view.isSelected()) {
                        view.setSelected(true);
                        userHelper.addItemToFavourites(userHelper.returnUserId(), izabranaZnamenitost.getIdZnamenitosti());
                        Snackbar.make(view, "Znamenitost dodana u favorite", Snackbar.LENGTH_LONG)
                                .setAction("Dodavanje", null).show();
                    } else {
                        view.setSelected(false);
                        userHelper.removeItemFromFavorites(userHelper.returnUserId(), izabranaZnamenitost.getIdZnamenitosti());
                        Snackbar.make(view, "Znamenitost maknuta iz favorita", Snackbar.LENGTH_LONG)
                                .setAction("Dodavanje", null).show();
                    }

                }
            });
        }
    }

    private void isItInFavourites (FloatingActionButton favoriteButton, Integer selectedId){
        List<Integer> listOfFavouritesID = currentUser.getIdoviZnamenitosti() ;
        if(listOfFavouritesID.contains(selectedId)){
            favoriteButton.setSelected(true);}
        else {
            favoriteButton.setSelected(false);
        }

    }

    private void prikaziLokaciju(Lokacija lokacija) {
        txtLokacijaZnamenitosti.setText(lokacija.getNaziv());
    }

    @Override
    public void onLoadZnamenitostSucess(String message, List<Znamenitost> listaZnamenitosti) {
        this.izabranaZnamenitost = listaZnamenitosti.get(0);
        prikaziPodatkeZnamenitosti(listaZnamenitosti.get(0));
    }

    @Override
    public void onLoadZnamenitostFail(String message) {
        Log.d("SetalisteTag", message);
    }

    @Override
    public void onLoadLokacijaSucess(String message, List<Lokacija> listaLokacija) {
        prikaziLokaciju(listaLokacija.get(0));
    }

    @Override
    public void onLoadLokacijaFail(String message) {
        Log.d("SetalisteTag", message);
    }

    @Override
    public void onLoadUserSuccess(String message, Korisnik currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public void onLoadUserFail(String message) {

    }
}
