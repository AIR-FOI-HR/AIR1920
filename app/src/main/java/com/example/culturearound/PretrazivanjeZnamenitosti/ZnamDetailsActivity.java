package com.example.culturearound.PretrazivanjeZnamenitosti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.core.DataLoader;
import com.example.culturearound.MainActivity;
import com.example.culturearound.R;
import com.example.database.Entities.Korisnik;
import com.example.database.Entities.Lokacija;
import com.example.database.Entities.Znamenitost;
import com.example.database.MyDatabase;

import java.util.List;

import butterknife.ButterKnife;
import loaders.DbDataLoader;

public class ZnamDetailsActivity extends AppCompatActivity {

    public static MyDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_znam_details);

        //Bundle b = getIntent().getExtras();
        //int id = b.getInt("id");

        ButterKnife.bind(this);
    }

    @Override
    protected void onResume(){
        super.onResume();

        Intent intent = getIntent();
        int znamId = intent.getIntExtra("id_znamenitost", -1);

        if(znamId != -1){
            Znamenitost znamenitost1 =
                    MainActivity.database.getDAO().loadZnamenitostiById(znamId);
            Toast.makeText(this, znamenitost1.getNaziv(), Toast.LENGTH_LONG);
        }

    }

}
