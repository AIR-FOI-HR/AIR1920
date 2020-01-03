package com.example.culturearound.Firebase;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginHelper extends FirebaseHelper{

    public LoginHelper(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mContext = context;
    }

    //Provjera je li korisnik ulogiran
    public boolean checkIfSignedIn(){
        if(mAuth.getUid() == null) {
            Toast.makeText(mContext,"Nije ulogiran korisnik", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            Toast.makeText(mContext,"Dobrodošao " + mAuth.getUid(), Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    //Login sa emailom i passwordom
    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(mContext,"Prijava uspješna.", Toast.LENGTH_SHORT).show();
                            Log.d("FirebaseTag", "createUserWithEmail:success");
                        }
                        else {
                            Toast.makeText(mContext, "Prijava neuspješna.", Toast.LENGTH_SHORT).show();
                            Log.w("FirebaseTag", "createUserWithEmail:failure", task.getException());
                        }
                    }
                });
    }
}
