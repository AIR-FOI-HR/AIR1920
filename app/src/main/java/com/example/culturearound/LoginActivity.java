package com.example.culturearound;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Dao;

import com.example.core.CurrentActivity;
import com.example.core.DataLoadedListener;
import com.example.core.DataLoader;
import com.example.database.Entities.Korisnik;
import com.example.database.Entities.Lokacija;
import com.example.database.Entities.Znamenitost;
import com.example.database.MyDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import loaders.DbDataLoader;

public class LoginActivity extends AppCompatActivity implements DataLoadedListener {
    private EditText Username;
    private EditText Password;
    private Button Login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Username = findViewById(R.id.login_username);
        Password = findViewById(R.id.login_password);
        Login = findViewById(R.id.login_button);

        DataLoader dataLoader = new DbDataLoader(this);
        //DataLoader dataLoader = new WsDataLoader();

        dataLoader.loadData(this);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInformation(Username.getText().toString(), Password.getText().toString());
            }
        });
    }

    private List<Korisnik> users = new ArrayList<Korisnik>();

    private void validateInformation (String username, String password) {
        if(username.isEmpty() || password.isEmpty() ){

        }
        else {
            for (Korisnik user : users ){
                if(username.equals(user.getKorisnicko_ime()) && password.equals(user.getLozinka())){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        }
    }

    @Override
    public void onDataLoaded(List<Korisnik> korisnici, List<Znamenitost> znamenitosti, List<Lokacija> lokacije) {
        users = korisnici;
    }
}
