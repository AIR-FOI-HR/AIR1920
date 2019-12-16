package com.example.culturearound;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.database.Entities.Korisnik;
import com.example.database.MyDatabase;

import java.util.List;

import Data.MockData;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.korisnici_list)
    ListView mListView;

    public static MyDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = MyDatabase.getInstance(this);

        ButterKnife.bind(this);
        mockData();
    }

    @OnClick(R.id.test_button)
    public void buttonClicked(View view)
    {
        final List<String> listItems = database.getDAO().getKorisnickoIme();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        mListView.setAdapter(adapter);
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
}
