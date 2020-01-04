package com.example.culturearound.Firebase;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class FirebaseHelper {

    //instanca FirebaseAutorizacije - usluga Authentication
    protected FirebaseAuth mAuth;
    //kontekst koristimo za toast i provjeru spajanja na internet
    protected Context mContext;
    //instanca FirebaseDatabase - usluga Database
    FirebaseDatabase database;
    //referenca na podatak unutar baze podataka
    DatabaseReference mDatabase;


    protected Boolean provjeriDostupnostMreze() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            Log.d("FirebaseTag", "Internet dostupan.");
            return true;
        }
        else{
            Toast.makeText(mContext, "Internet nije dostupan!", Toast.LENGTH_LONG).show();
            Log.d("FirebaseTag", "Internet nedostupan.");
            return false;
        }
    }
}
