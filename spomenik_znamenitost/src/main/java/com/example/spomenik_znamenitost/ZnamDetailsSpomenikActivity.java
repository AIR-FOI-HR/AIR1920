package com.example.spomenik_znamenitost;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.database.EntitiesFirebase.Lokacija;
import com.example.database.EntitiesFirebase.Znamenitost;
import com.example.database.Listeners.LokacijaListener;
import com.example.database.Listeners.ZnamenitostListener;
import com.example.database.LokacijaHelper;
import com.example.database.ZnamenitostiHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ZnamDetailsSpomenikActivity extends AppCompatActivity implements ZnamenitostListener, LokacijaListener {
    private ZnamenitostiHelper znamenitostiHelper;
    private LokacijaHelper lokacijaHelper;

    ImageView imgNaslovnaSlika;
    TextView txtAdresaZnamenitosti;
    TextView txtLokacijaZnamenitosti;
    TextView txtOpisZnamenitosti;
    RecyclerView recyclerViewGalerija;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_znam_details_spomenik);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnFavourite);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Dodaj u favourite", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        znamenitostiHelper = new ZnamenitostiHelper((Context) this, this);
        lokacijaHelper = new LokacijaHelper((Context) this, this);

        imgNaslovnaSlika = (ImageView) findViewById(R.id.imgNaslovnaSlika);
        txtAdresaZnamenitosti = (TextView) findViewById(R.id.txtAdresaZnamenitosti);
        txtLokacijaZnamenitosti = (TextView) findViewById(R.id.txtLokacijaZnamenitosti);
        txtOpisZnamenitosti = (TextView) findViewById(R.id.txtOpisZnamenitosti);
        recyclerViewGalerija = (RecyclerView) findViewById(R.id.recyclerViewGalerija);
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
        setTitle(znamenitost.getNaziv());
        Picasso.with(this)
                .load(znamenitost.getLokacijaSlike())
                .into(imgNaslovnaSlika);
        txtAdresaZnamenitosti.setText(znamenitost.getAdresa());

        lokacijaHelper.dohvatiZnamenitostPremaId(znamenitost.getIdLokacija());

        txtOpisZnamenitosti.setText(znamenitost.getOpis());

        //recyclerview komentari
    }

    private void prikaziLokaciju(Lokacija lokacija) {
        txtLokacijaZnamenitosti.setText(lokacija.getNaziv());
    }

    @Override
    public void onLoadZnamenitostSucess(String message, List<Znamenitost> listaZnamenitosti) {
        prikaziPodatkeZnamenitosti(listaZnamenitosti.get(0));
    }

    @Override
    public void onLoadZnamenitostFail(String message) {
        Log.d("SpomenikTag", "message");
    }

    @Override
    public void onLoadLokacijaSucess(String message, List<Lokacija> listaLokacija) {
        prikaziLokaciju(listaLokacija.get(0));
    }

    @Override
    public void onLoadLokacijaFail(String message) {
        Log.d("SpomenikTag", "message");
    }
}
