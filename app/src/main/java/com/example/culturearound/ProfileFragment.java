package com.example.culturearound;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.database.EntitiesFirebase.Korisnik;
import com.example.database.Listeners.UserListener;
import com.example.database.UserHelper;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProfileFragment extends Fragment implements UserListener {
    @BindView(R.id.update_profile)
    Button btnUpdateData;

    @BindView(R.id.profile_name)
    TextView userFirstName;

    @BindView(R.id.profile_surname)
    TextView userLastName;

    @BindView(R.id.profile_email)
    TextView userMail;

    private UserHelper userHelper;

    private static final String TAG = "profil";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile,container,false);

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "Pocetak...");
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        userHelper = new UserHelper(getContext(),this);
        Log.d(TAG, "onViewCreated: "+ userHelper.userId);
        userHelper.findUserById();
    }

    private void loadData(Korisnik korisnik){
        Log.d(TAG, "Ucitano " + korisnik.getIme() + korisnik.getPrezime() + korisnik.getEmail());
        userFirstName.setText(korisnik.getIme());
        userLastName.setText(korisnik.getPrezime());
        userMail.setText(korisnik.getEmail());
    }


    @Override
    public void onLoadUserSuccess(String message, Korisnik currentUser) {
        Log.d(TAG, "Ucitano2 " + currentUser.getIme() + currentUser.getPrezime() + currentUser.getEmail());
        loadData(currentUser);
    }

    @Override
    public void onLoadUserFail(String message) {

    }
}
