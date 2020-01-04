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
import com.example.culturearound.Firebase.Listeners.LoginListener;
import com.example.culturearound.Firebase.LoginHelper;
import com.example.database.Entities.Korisnik;
import com.example.database.Entities.Lokacija;
import com.example.database.Entities.Znamenitost;

import java.util.ArrayList;
import java.util.List;

import loaders.DbDataLoader;

public class LoginActivity extends AppCompatActivity implements DataLoadedListener, LoginListener {
    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnLogin;
    private Button btnRegister;
    private Button btnForgottenPassword;
    private Button btnUnregisteredUser;

    LoginHelper loginHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = findViewById(R.id.login_email);
        txtPassword = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.login_button);
        btnRegister = findViewById(R.id.register_button);
        btnForgottenPassword = findViewById(R.id.lozinka_button);
        btnUnregisteredUser = findViewById(R.id.bez_prijave_button);


        DataLoader dataLoader = new DbDataLoader(this);
        //DataLoader dataLoader = new WsDataLoader();

        dataLoader.loadData(this);

        loginHelper = new LoginHelper(this);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInformation(txtEmail.getText().toString(), txtPassword.getText().toString());
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistryActivity.class);
                startActivity(intent);
            }
        });

        btnForgottenPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                if(!email.isEmpty()){
                    loginHelper.resetPassword(email);
                }
            }
        });

        btnUnregisteredUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pokreniPocetnuStranicu();
            }
        });
    }

    private List<Korisnik> users = new ArrayList<Korisnik>();

    private void validateInformation (String email, String password) {
        //Boolean userExist = false;

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Niste unijeli korisničke podatke.", Toast.LENGTH_LONG).show();
        }
        else {
            /*
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
            */

            //Firebase od ovdje:
            loginHelper.signOut();
            loginHelper.signIn(email, password);
        }
    }

    private void pokreniPocetnuStranicu() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onDataLoaded(List<Korisnik> korisnici, List<Znamenitost> znamenitosti, List<Lokacija> lokacije) {
        users = korisnici;
    }

    @Override
    public void onLoginSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        pokreniPocetnuStranicu();
    }

    @Override
    public void onLoginFail(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
