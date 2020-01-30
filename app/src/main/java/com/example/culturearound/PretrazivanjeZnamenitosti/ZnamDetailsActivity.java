package com.example.culturearound.PretrazivanjeZnamenitosti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.database.Listeners.ZnamenitostListener;
import com.example.database.ZnamenitostiHelper;
import com.example.culturearound.R;
import com.example.database.EntitiesFirebase.Znamenitost;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZnamDetailsActivity extends AppCompatActivity implements ZnamenitostListener {
    @BindView(R.id.textNaziv)
    TextView textNaziv;
    @BindView(R.id.imageZnamenitost)
    ImageView imageZnamenitost;
    @BindView(R.id.textAdresa)
    TextView textAdresa;
    @BindView(R.id.textOpisZnamenitosti)
    TextView textOpisZnamenitosti;

    private ZnamenitostiHelper znamenitostiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_znam_details);
        ButterKnife.bind(this);
        znamenitostiHelper = new ZnamenitostiHelper((Context) this, this);
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
        textNaziv.setText(znamenitost.getNaziv());
        Picasso.with(this)
                .load(znamenitost.getLokacijaSlike())
                .into(imageZnamenitost);
        textAdresa.setText(znamenitost.getAdresa());
        textOpisZnamenitosti.setText(znamenitost.getOpis());
    }

    @Override
    public void onLoadZnamenitostSucess(String message, List<Znamenitost> listaZnamenitosti) {
        prikaziPodatkeZnamenitosti(listaZnamenitosti.get(0));
    }

    @Override
    public void onLoadZnamenitostFail(String message) {
        Log.d("ZnamenitostTag", "Load Fail");
    }
}
