package com.example.culturearound;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.core.DataLoadedListener;
import com.example.core.DataLoader;
import com.example.database.Entities.Korisnik;
import com.example.database.Entities.Lokacija;
import com.example.database.Entities.Znamenitost;

import java.util.ArrayList;
import java.util.List;

import loaders.DbDataLoader;

public class LoginActivity extends AppCompatActivity implements DataLoadedListener {
    private EditText Username;
    private EditText Password;
    private Button Login;
    private Button Register;
    private Button ForgottenPassword;
    private Button UnregisteredUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Username = findViewById(R.id.login_username);
        Password = findViewById(R.id.login_password);
        Login = findViewById(R.id.login_button);
        Register = findViewById(R.id.register_button);

        DataLoader dataLoader = new DbDataLoader(this);
        //DataLoader dataLoader = new WsDataLoader();

        dataLoader.loadData(this);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInformation(Username.getText().toString(), Password.getText().toString());
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistryActivity.class);
                startActivity(intent);
            }
        });
/*
        ForgottenPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        UnregisteredUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

         */

    }

    private List<Korisnik> users = new ArrayList<Korisnik>();

    private void validateInformation (String username, String password) {
        Boolean userExist = false;

        if(username.isEmpty() || password.isEmpty() ){
            Toast.makeText(this, "Niste unijeli korisničke podatke.", Toast.LENGTH_LONG).show();
        }
        else {
            for (Korisnik user : users ){
                if(username.equals(user.getKorisnicko_ime()) && password.equals(user.getLozinka())){
                    userExist=true;
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    this.finish();
                }
            }

            if(userExist == false) {
                Toast.makeText(this, "Unijeli ste pogrešne korisničke podatke.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onDataLoaded(List<Korisnik> korisnici, List<Znamenitost> znamenitosti, List<Lokacija> lokacije) {
        users = korisnici;
    }

}
