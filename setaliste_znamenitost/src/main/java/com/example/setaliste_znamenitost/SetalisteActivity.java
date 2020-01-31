package com.example.setaliste_znamenitost;

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
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SetalisteActivity extends AppCompatActivity implements ZnamenitostListener, LokacijaListener {

    private TextView txtNaziv;
    private ZnamenitostiHelper znamenitostiHelper;
    private LokacijaHelper lokacijaHelper;
    private ImageView imgNaslovnaSlika;
    private TextView txtAdresaZnamenitosti;
    private TextView txtLokacijaZnamenitosti;
    private TextView txtOpisZnamenitosti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setaliste);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Dodaj u favorite", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
}
