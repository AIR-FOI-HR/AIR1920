package com.example.database;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public abstract class FirebaseHelper {
    //instanca FirebaseAutorizacije - usluga Authentication
    protected FirebaseAuth mAuth;
    //kontekst koristimo za toast i provjeru spajanja na internet
    protected Context mContext;
    //instanca FirebaseDatabase - usluga Database
    protected FirebaseDatabase database;
    //referenca na podatke unutar baze podataka
    protected DatabaseReference mDatabase;
    //referenca za upite

    protected Query mQuery;

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public boolean checkIfSignedIn(){
        if(mAuth.getUid() == null) {
            Toast.makeText(mContext,"Nije ulogiran korisnik", Toast.LENGTH_SHORT).show();
            Log.d("FirebaseTag", "Nije ulogiran korisnik");
            return false;
        }
        else {
            Log.d("FirebaseTag", "Ulogiran korisnik");
            return true;
        }
    }


    protected Boolean provjeriDostupnostMreze() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        else{
            Toast.makeText(mContext, "Internet nije dostupan!", Toast.LENGTH_LONG).show();
            Log.d("FirebaseTag", "Internet nedostupan.");
            return false;
        }
    }
}
