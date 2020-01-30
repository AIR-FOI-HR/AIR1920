package com.example.culturearound;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.core.CurrentActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity  {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_location, R.id.navigation_profile, R.id.navigation_favorites, R.id.navigation_recommended)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        //postavljanje listenera i postavljanje pocetnog fragmenta kod prvog otvaranja (fragment pocetne stranice)
        navView.setOnNavigationItemSelectedListener(navigationListener);

        navView.setSelectedItemId(R.id.navigation_home);

        CurrentActivity.setActivity(this);
    }

    //kreiranje listenera za klik na određenu ikonu u navigaciji

    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;

            // ovisno o odabiru ikone, instancira odgovarajuci fragment

            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = new HomeFragment();
                    break;

                case R.id.navigation_profile:
                    selectedFragment = new ProfileFragment();
                    break;

                case R.id.navigation_location:
                    selectedFragment = new LocationFragment();
                    System.out.println(("HOLA"));
                    break;

                    case R.id.navigation_favorites:
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



}
