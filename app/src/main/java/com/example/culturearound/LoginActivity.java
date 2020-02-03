package com.example.culturearound;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.database.Listeners.LoginListener;
import com.example.database.LoginHelper;
import com.example.database.EntitiesFirebase.Korisnik;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements LoginListener {
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

        loginHelper = new LoginHelper(this);

        if (loginHelper.checkIfSignedIn()) pokreniPocetnuStranicu();

        setContentView(R.layout.activity_login);

        txtEmail = findViewById(R.id.login_email);
        txtPassword = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.login_button);
        btnRegister = findViewById(R.id.register_button);
        btnForgottenPassword = findViewById(R.id.lozinka_button);
        btnUnregisteredUser = findViewById(R.id.bez_prijave_button);

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

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Niste unijeli korisničke podatke.", Toast.LENGTH_LONG).show();
        }
        else {
            loginHelper.signIn(email, password);
        }
    }

    private void pokreniPocetnuStranicu() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
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
