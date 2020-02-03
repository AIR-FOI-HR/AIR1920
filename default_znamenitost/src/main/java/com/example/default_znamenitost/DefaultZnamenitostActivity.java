package com.example.default_znamenitost;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.database.EntitiesFirebase.Komentar;
import com.example.database.EntitiesFirebase.Korisnik;
import com.example.database.EntitiesFirebase.Lokacija;
import com.example.database.EntitiesFirebase.Znamenitost;
import com.example.database.Listeners.LokacijaListener;
import com.example.database.Listeners.UserListener;
import com.example.database.Listeners.ZnamenitostListener;
import com.example.database.Listeners.RecenzijaListener;
import com.example.database.LokacijaHelper;
import com.example.database.RecenzijeHelper;
import com.example.database.UserHelper;
import com.example.database.ZnamenitostiHelper;
import com.example.default_znamenitost.recyclerView.GalerijaRecyclerAdapter;
import com.example.default_znamenitost.recyclerView.RecenzijeRecycelerAdapter;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.BlurTransformation;

public class DefaultZnamenitostActivity extends AppCompatActivity implements ZnamenitostListener, LokacijaListener, RecenzijaListener, UserListener
{
    private ZnamenitostiHelper znamenitostiHelper;
    private LokacijaHelper lokacijaHelper;
    private RecenzijeHelper recenzijeHelper;

    ImageView imgNaslovnaSlika;
    TextView txtAdresaZnamenitosti;
    TextView txtLokacijaZnamenitosti;
    TextView txtOpisZnamenitosti;
    RecyclerView recyclerViewGalerija;


    RecyclerView recyclerView;
    RatingBar rateSve;
    private EditText ubaciRecenziju;
    private EditText ubaciOcjenu;
    private Button btnRecenzija;
    private UserHelper userHelper;
    private Znamenitost izabranaZnamenitost;


    private List<Komentar> komentari;
    private RecenzijeRecycelerAdapter recenzijeRecycelerAdapter;
    private Korisnik currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_znamenitost);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userHelper = new UserHelper(this, this);
        if(userHelper.checkIfSignedIn()){
            userHelper.findUserById(userHelper.returnUserId());
        }



        recyclerView = findViewById(R.id.listaRecenzije);
        rateSve = findViewById(R.id.sveOcjena);
        ubaciRecenziju = findViewById(R.id.upisiRecenziju);
        ubaciOcjenu = findViewById(R.id.upisiOcjenu);
        btnRecenzija = findViewById(R.id.upisiRecenzijuBtn);

        imgNaslovnaSlika = (ImageView) findViewById(R.id.imgNaslovnaSlika);
        txtAdresaZnamenitosti = (TextView) findViewById(R.id.txtAdresaZnamenitosti);
        txtLokacijaZnamenitosti = (TextView) findViewById(R.id.txtLokacijaZnamenitosti);
        txtOpisZnamenitosti = (TextView) findViewById(R.id.txtOpisZnamenitosti);
        recyclerViewGalerija = (RecyclerView) findViewById(R.id.recyclerViewGalerija);

        recenzijeHelper = new RecenzijeHelper((Context) this, this);
        znamenitostiHelper = new ZnamenitostiHelper((Context) this, this);
        lokacijaHelper = new LokacijaHelper((Context) this, this);

        if (!recenzijeHelper.checkIfSignedIn()){
            iskljuciDodavanjeKomentara();
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

    private void iskljuciDodavanjeKomentara(){
        ubaciRecenziju.setFocusable(false);
        ubaciOcjenu.setFocusable(false);
        ubaciRecenziju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Potrebno se je ulogirat za dodavanje komentara.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        ubaciOcjenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Potrebno se je ulogirat za dodavanje komentara.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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

    private void prikaziPodatkeZnamenitosti(final Znamenitost znamenitost){
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle(znamenitost.getNaziv());
        Picasso.with(this)
                .load(znamenitost.getLokacijaSlike())
                .transform(new BlurTransformation(this, 25, 1))
                .into(imgNaslovnaSlika);
        txtAdresaZnamenitosti.setText(znamenitost.getAdresa());
        lokacijaHelper.dohvatiZnamenitostPremaId(znamenitost.getIdLokacija());
        txtOpisZnamenitosti.setText(znamenitost.getOpis());

        //početno postavljanje recyclerView-a GALERIJE
        GalerijaRecyclerAdapter galerijaRecyclerAdapter = new GalerijaRecyclerAdapter(this, znamenitost.getListaSlikaGalerije());
        recyclerViewGalerija.setAdapter(galerijaRecyclerAdapter);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewGalerija.setLayoutManager(layoutManager);

        //početno postavljenje recyclerView-a RECENZIJA/komentara
        komentari = new ArrayList<>();
        recenzijeRecycelerAdapter = new RecenzijeRecycelerAdapter((Context) this, komentari);
        recyclerView.setAdapter(recenzijeRecycelerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //dohvaćanje Recenzija
        recenzijeHelper.dohvatiRecenzijePremaId(znamenitost.getIdZnamenitosti());

        btnRecenzija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!recenzijeHelper.checkIfSignedIn()){
                    Snackbar.make(v, "Potrebno se je ulogirat za dodavanje komentara.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                provjeraUnosa(
                        ubaciRecenziju.getText().toString(),
                        ubaciOcjenu.getText().toString(),
                        znamenitost.getIdZnamenitosti());
            }
        });

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

    private void prikaziLokaciju(Lokacija lokacija) {
        txtLokacijaZnamenitosti.setText(lokacija.getNaziv());
    }

    private void prikaziRecenzije(List<Komentar> listaKomentara){
        recenzijeRecycelerAdapter.setKomentari(listaKomentara);
        recenzijeRecycelerAdapter.notifyDataSetChanged();
    }

    private void provjeraUnosa (String ubaciRecenziju,String Ubaciocjenu,int znamenitostID) {

        if(Ubaciocjenu.length()==0){
            Toast.makeText(this, "Ocjena se mora unijeti", Toast.LENGTH_LONG).show();
        }
        else{int ocjena=Integer.parseInt(Ubaciocjenu);
            if(ocjena<1|| ocjena>5){
                Toast.makeText(this, "Ocjene su od 1-5", Toast.LENGTH_LONG).show();
            }
            else{
                if(ubaciRecenziju.length()== 0){
                    ubaciRecenziju="Nema komentara";
                    recenzijeHelper.zapisiRecenziju(ubaciRecenziju,ocjena,znamenitostID);
                }
                else{
                    recenzijeHelper.zapisiRecenziju(ubaciRecenziju,ocjena,znamenitostID);
                }
            }}
    }



    //Znamenitost Listener
    @Override
    public void onLoadZnamenitostSucess(String message, List<Znamenitost> listaZnamenitosti) {
        izabranaZnamenitost = listaZnamenitosti.get(0);
        prikaziPodatkeZnamenitosti(listaZnamenitosti.get(0));
    }

    @Override
    public void onLoadZnamenitostFail(String message) {
        Log.d("SpomenikTag", message);
    }

    //Lokacija listener
    @Override
    public void onLoadLokacijaSucess(String message, List<Lokacija> listaLokacija) {
        prikaziLokaciju(listaLokacija.get(0));
    }

    @Override
    public void onLoadLokacijaFail(String message) {
        Log.d("SpomenikTag", message);
    }

    //Recenzija Listener
    @Override
    public void onLoadRecenzijaSucess(String message, List<Komentar> listaKomentara) {
        if (!listaKomentara.isEmpty()){
            prikaziRecenzije(listaKomentara);
            if(recenzijeHelper.ukupno!=0){
                rateSve.setRating((float)recenzijeHelper.ukupno);}
            else{
                rateSve.setRating(0);
            }
        }
    }

    @Override
    public void onOcjenaKomentaraSucess(String message, String mojaOcjenaKomentara) {
        //generirana listener metoda koja se ovdje ne koristi
    }

    @Override
    public void onLoadRecenzijaFail(String message) {

    }

    @Override
    public void onLoadUserSuccess(String message, Korisnik currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public void onLoadUserFail(String message) {

    }
}
