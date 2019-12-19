package com.example.culturearound;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.core.DataLoadedListener;
import com.example.core.DataLoader;
import com.example.database.DAO;
import com.example.database.Entities.Korisnik;
import com.example.database.Entities.Lokacija;
import com.example.database.Entities.Znamenitost;
import com.example.database.MyDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import loaders.DbDataLoader;

public class RegistryActivity extends AppCompatActivity implements DataLoadedListener {
    private EditText Ime;
    private EditText Prezime;
    private EditText Korime;
    private EditText Email;
    private EditText Lozinka;
    private EditText PonoviLozinku;
    private Button Registriraj;
    //private static DAO dao;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);

        Ime = findViewById(R.id.re_ime);
        Prezime = findViewById(R.id.re_prezime);
        Korime = findViewById(R.id.re_username);
        Email=findViewById(R.id.re_email);
        Lozinka = findViewById(R.id.re_password);
        PonoviLozinku = findViewById(R.id.re_password2);
        Registriraj = findViewById(R.id.register_button);

        DataLoader dataLoader = new DbDataLoader(this);

        dataLoader.loadData(this);


        Registriraj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInformation(Ime.getText().toString(), Prezime.getText().toString(), Korime.getText().toString(),Email.getText().toString(),Lozinka.getText().toString(),PonoviLozinku.getText().toString());
            }
        });

    }


    private static DAO dao;

    public static void writeData(Context context,String ime, String prezime, String korime, String email, String lozinka){
        dao = MyDatabase.getInstance(context).getDAO();

        Korisnik korisnik = new Korisnik();
        korisnik.setIme(ime);
        korisnik.setPrezime(prezime);
        korisnik.setEmail(email);
        korisnik.setKorisnicko_ime(korime);
        korisnik.setLozinka(lozinka);
        dao.insertKorisnici(korisnik);


    }



    private List<Korisnik> users = new ArrayList<Korisnik>();

    private void validateInformation (String ime, String prezime, String korime, String email, String lozinka, String ponLozinku) {
        Boolean userNotExist=true;

        if(ime.isEmpty() || prezime.isEmpty() || korime.isEmpty() || email.isEmpty() || lozinka.isEmpty() || ponLozinku.isEmpty()  ){
            Toast.makeText(this, "Niste unijeli korisničke podatke.", Toast.LENGTH_LONG).show();
        }
        else {
            if(lozinka.equals(ponLozinku)){

                for(Korisnik user:users){
                    if(korime.equals(user.getKorisnicko_ime())|| email.equals(user.getEmail())){
                        userNotExist=false;
                    }
                    else {
                        userNotExist= true;
                    }
                }

                if(userNotExist==false){
                    Toast.makeText(this, "Korisničko ime ili email već postoji.", Toast.LENGTH_LONG).show();
                }
                else{

                    Toast.makeText(this, "Moze", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegistryActivity.this, MainActivity.class);
                    startActivity(intent);
                    this.finish();
                    writeData(this, ime, prezime,korime,email,lozinka);
                }
            }
            else{
                Toast.makeText(this, "Lozinka i ponovljena lozinka nisu jednake.", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public void onDataLoaded(List<Korisnik> korisnici, List<Znamenitost> znamenitosti, List<Lokacija> lokacije) {
        users = korisnici;
    }
}

