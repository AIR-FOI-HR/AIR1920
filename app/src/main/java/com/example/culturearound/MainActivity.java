package com.example.culturearound;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.core.DataLoadedListener;
import com.example.core.DataLoader;
import com.example.culturearound.PretrazivanjeZnamenitosti.recyclerview.ZnamenitostRecyclerAdapter;
import com.example.culturearound.PretrazivanjeZnamenitosti.recyclerview.ZnamenitostViewHolder;
import com.example.database.Entities.Korisnik;
import com.example.database.Entities.Lokacija;
import com.example.database.Entities.Znamenitost;
import com.example.database.MyDatabase;

import java.util.ArrayList;
import java.util.List;

import Data.MockData;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import loaders.DbDataLoader;

public class MainActivity extends AppCompatActivity implements DataLoadedListener {
    @BindView(R.id.imageView1)
    ImageView image1;
    @BindView(R.id.imageView2)
    ImageView image2;
    @BindView(R.id.imageView3)
    ImageView image3;
    @BindView(R.id.imageView4)
    ImageView image4;
    @BindView(R.id.main_recycler)
    RecyclerView recyclerView;

    public static MyDatabase database;
    private List<Znamenitost> znamenitosti;
    private ZnamenitostRecyclerAdapter znamenitostRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = MyDatabase.getInstance(this);

        ButterKnife.bind(this);
        mockData();
        loadData();
    }

    public void loadData()
    {
        DataLoader dataLoader = new DbDataLoader();
        dataLoader.loadData(this);
    }
    private void mockData(){
        List<Korisnik> korisnik = database.getDAO().loadAllKorisnik();
        List<Znamenitost> znamenitosti = database.getDAO().loadAllZnamenitosti();

        //for (Znamenitost z: znamenitosti) { database.getDAO().deleteZnamenitosti(z); }


        if(!znamenitosti.isEmpty()){
            for(Znamenitost z : znamenitosti){
                Log.d("Znamenitosti", "Znamenitost: " + z.getNaziv());
            }
        }else {
            MockData.writeData(this);
        }

        if (!korisnik.isEmpty()) {
            for (Korisnik k : korisnik) {
                Log.d("AIRAIR","Korisnik: " + k.getIme());
                Log.d("AIRAIR","Korisnik: " + k.getPrezime());
            }
        }else {
            MockData.writeData(this);
        }
    }

    @Override
    public void onDataLoaded(List<Korisnik> korisnici, List<Znamenitost> znamenitosti, List<Lokacija> lokacije) {
        this.znamenitosti = znamenitosti;
        znamenitostRecyclerAdapter = new ZnamenitostRecyclerAdapter(this, znamenitosti);

        recyclerView.setAdapter(znamenitostRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public void kategorijaButton(View view) {
        if(!znamenitosti.isEmpty()){

            List<Znamenitost> prikazaneZnamenitosti = new ArrayList<>();
            for (Znamenitost znamenitost: znamenitosti) {
                if(znamenitost.getId_kategorija_znamenitosti() == Integer.valueOf((String) view.getTag()))
                    prikazaneZnamenitosti.add(znamenitost);
            }

            if (!prikazaneZnamenitosti.isEmpty()){
                znamenitostRecyclerAdapter.setZnamenitosti(prikazaneZnamenitosti);
                znamenitostRecyclerAdapter.notifyDataSetChanged();
            }
            else
                Toast.makeText(this, "Ne postoje znamenitosti u ovoj kategoriji", Toast.LENGTH_SHORT).show();
        }
    }
}
