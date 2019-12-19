package com.example.culturearound;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.core.DataLoadedListener;
import com.example.core.DataLoader;
import com.example.culturearound.PretrazivanjeZnamenitosti.recyclerview.ZnamenitostRecyclerAdapter;
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

    @BindView(R.id.main_recycler)
    RecyclerView recyclerView;

    public static MyDatabase database;

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
        recyclerView.setAdapter(new ZnamenitostRecyclerAdapter(this, znamenitosti));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
