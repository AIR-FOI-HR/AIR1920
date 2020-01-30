package com.example.culturearound.PretrazivanjeZnamenitosti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.core.CurrentActivity;
import com.example.culturearound.Firebase.EntitiesFirebase.Komentar;
import com.example.culturearound.Firebase.Listeners.RecenzijaListener;
import com.example.culturearound.Firebase.Listeners.ZnamenitostListener;
import com.example.culturearound.Firebase.RecenzijeHelper;
import com.example.culturearound.Firebase.ZnamenitostiHelper;
import com.example.culturearound.PretrazivanjeZnamenitosti.recyclerview.RecenzijeRecycelerAdapter;
import com.example.culturearound.R;
import com.example.culturearound.Firebase.EntitiesFirebase.Znamenitost;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.core.CurrentActivity.getActivity;

public class ZnamDetailsActivity extends AppCompatActivity implements ZnamenitostListener, RecenzijaListener {
    @BindView(R.id.textNaziv)
    TextView textNaziv;
    @BindView(R.id.imageZnamenitost)
    ImageView imageZnamenitost;
    @BindView(R.id.textAdresa)
    TextView textAdresa;
    @BindView(R.id.textOpisZnamenitosti)
    TextView textOpisZnamenitosti;
    @BindView(R.id.listaRecenzije)
    RecyclerView recyclerView;



    private List<Komentar> komentari;
    private RecenzijeRecycelerAdapter recenzijeRecycelerAdapter;


    private ZnamenitostiHelper znamenitostiHelper;
    private RecenzijeHelper recenzijeHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_znam_detais);
        ButterKnife.bind(this);
        znamenitostiHelper = new ZnamenitostiHelper((Context) this, this);

        komentari = new ArrayList<>();
        recenzijeRecycelerAdapter = new RecenzijeRecycelerAdapter(getActivity(), komentari);
        recyclerView.setAdapter(recenzijeRecycelerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        recenzijeHelper = new RecenzijeHelper(CurrentActivity.getActivity(), this);


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

        recenzijeHelper.dohvatiRecenzijePremaId(znamenitost.getIdZnamenitosti());
    }

    private void prikaziRecenzije(List<Komentar> listaKomentara){
        recenzijeRecycelerAdapter.setKomentari(listaKomentara);
        Log.d("Anja","Ovdje staje jelda?");
        recenzijeRecycelerAdapter.notifyDataSetChanged();
        Log.d("Anja", "Maybe not...");
    }



    @Override
    public void onLoadZnamenitostSucess(String message, List<Znamenitost> listaZnamenitosti) {
        prikaziPodatkeZnamenitosti(listaZnamenitosti.get(0));
    }

    @Override
    public void onLoadZnamenitostFail(String message) {
        Log.d("ZnamenitostTag", "Load Fail");
    }

    @Override
    public void onLoadRecenzijaSucess(String message, List<Komentar> listaKomentara) {
        Log.d("RecenzijaTag", message);
        if (!listaKomentara.isEmpty()){
            //this.komentari = listaKomentara;
            prikaziRecenzije(listaKomentara);
        }
    }

    @Override
    public void onLoadRecenzijaFail(String message) {
        Log.d("RecenzijaTag", "Load Fail");
    }
}