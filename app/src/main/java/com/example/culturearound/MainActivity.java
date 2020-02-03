package com.example.culturearound;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.core.CurrentActivity;

import com.example.database.EntitiesFirebase.Korisnik;
import com.example.database.Listeners.UserListener;
import com.example.database.UserHelper;

import com.example.maps.MapModule;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.List;


public class MainActivity extends AppCompatActivity implements UserListener{

    private UserHelper userHelper;

    DialogInterface.OnClickListener dialogClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        userHelper = new UserHelper(this, this);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_location, R.id.navigation_profile, R.id.navigation_favorites, R.id.navigation_recommended)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        //postavljanje listenera i postavljanje pocetnog fragmenta kod prvog otvaranja (fragment pocetne stranice)
        navView.setOnNavigationItemSelectedListener(navigationListener);

        navView.setSelectedItemId(R.id.navigation_home);

        CurrentActivity.setActivity(this);
        if(!userHelper.checkIfSignedIn()){
            dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            pokrenutiPrijavu();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };
        }
    }

    private void pokrenutiPrijavu(){
        Log.d("Dijalog", "Pokretanje prijave");
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

    //kreiranje listenera za klik na određenu ikonu u navigaciji

    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;
            MapModule mapModule = new MapModule();

            // ovisno o odabiru ikone, instancira odgovarajuci fragment

            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = new HomeFragment();
                    break;

                case R.id.navigation_profile:
                    if (dialogClickListener != null){
                        AlertDialog.Builder builder = new AlertDialog.Builder(CurrentActivity.getActivity());
                        builder.setMessage("Morate se prijaviti ako želite pristupiti pregledu vlastitog profila.Želite li se prijaviti?").setPositiveButton("Da", dialogClickListener)
                                .setNegativeButton("Ne", dialogClickListener).show();
                        return false;
                    }
                    selectedFragment = new ProfileFragment();
                    break;

                case R.id.navigation_location:
                    //selectedFragment = new LocationFragment();
                    selectedFragment = mapModule;
                    break;

                case R.id.navigation_favorites:
                    if (dialogClickListener != null){
                        AlertDialog.Builder builder = new AlertDialog.Builder(CurrentActivity.getActivity());
                        builder.setMessage("Morate se prijaviti ako želite pristupiti favoritima. Želite li se prijaviti?").setPositiveButton("Da", dialogClickListener)
                                .setNegativeButton("Ne", dialogClickListener).show();
                        return false;
                    }
                    selectedFragment = new FavoritesFragment();
                    break;

                case R.id.navigation_recommended:
                    selectedFragment = new RecommendedFragment();
                    break;
            }

            //mijenjanje prethodno otvorenog fragmenta s novoodabranim
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, selectedFragment).commit();
            return true;
        }
    };


    @Override
    public void onLoadUserSuccess(String message, Korisnik currentUser) {

    }

    @Override
    public void onLoadUserFail(String message) {

    }
}
