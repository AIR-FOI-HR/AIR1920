package com.example.culturearound;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.core.DataLoadedListener;
import com.example.core.DataLoader;
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

    @BindView(R.id.korisnici_list)
    ListView mListView;

    public static MyDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = MyDatabase.getInstance(this);

        ButterKnife.bind(this);
        //mockData();
    }

    @OnClick(R.id.test_button)
    public void buttonClicked(View view)
    {
        /*
        final List<String> listItems = database.getDAO().getKorisnickoIme();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        mListView.setAdapter(adapter);
        */
        DataLoader dataLoader = new DbDataLoader();
        //DataLoader dataLoader = new WsDataLoader();
        dataLoader.loadData(this);
    }


    private void mockData(){
        List<Korisnik> korisnik = database.getDAO().loadAllKorisnik();
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
        final List<String> listItems = new ArrayList<>();

        for (Korisnik k: korisnici) {
            listItems.add(k.getKorisnicko_ime());
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems.toArray());
        mListView.setAdapter(adapter);
    }
}
