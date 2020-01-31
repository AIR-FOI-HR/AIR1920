package com.example.culturearound.PretrazivanjeZnamenitosti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.core.CurrentActivity;

import com.example.culturearound.Firebase.EntitiesFirebase.Komentar;
import com.example.culturearound.Firebase.Listeners.RecenzijaListener;
import com.example.culturearound.Firebase.Listeners.ZnamenitostListener;
import com.example.culturearound.Firebase.RecenzijeHelper;
import com.example.culturearound.Firebase.ZnamenitostiHelper;
import com.example.culturearound.PretrazivanjeZnamenitosti.recyclerview.RecenzijeRecycelerAdapter;
import com.example.culturearound.R;
import com.example.culturearound.Firebase.EntitiesFirebase.Znamenitost;

import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.firebase.auth.FirebaseAuth;

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

    @BindView(R.id.sveOcjena)
    RatingBar rateSve;

    private EditText UbaciRecenziju;
    private EditText UbaciOcjenu;
    private Button Recenzija;
    int znamenitostID;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uID = user.getUid();


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



        UbaciRecenziju = findViewById(R.id.upisiRecenziju);
        UbaciOcjenu= findViewById(R.id.upisiOcjenu);
        Recenzija = findViewById(R.id.upisiRecenzijuBtn);

        Recenzija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInformation(
                        UbaciRecenziju.getText().toString(),
                        UbaciOcjenu.getText().toString(),
                        znamenitostID

                        );

            }
        });

    }

    private void validateInformation (String ubaciRecenziju,String Ubaciocjenu,int znamenitostID) {
        Log.d("Anja3", ubaciRecenziju);
        Log.d("Anja3",Ubaciocjenu);
       recenzijeHelper.zapisiRecenziju(ubaciRecenziju,Integer.parseInt(Ubaciocjenu),znamenitostID,uID);
        Toast.makeText(this, "Uspješna recenzija", Toast.LENGTH_LONG).show();

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
        znamenitostID=znamenitost.getIdZnamenitosti();


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
            prikaziRecenzije(listaKomentara);

            if(recenzijeHelper.ukupno!=0){
            rateSve.setRating((float)recenzijeHelper.ukupno);}
            else{
                rateSve.setRating(0);
            }
            Log.d("Anja1","Cudo bozje: "+ recenzijeHelper.ukupno);
        }
    }

    @Override
    public void onLoadRecenzijaFail(String message) {
        Log.d("RecenzijaTag", "Load Fail");
    }
}