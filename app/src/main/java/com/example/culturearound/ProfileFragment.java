package com.example.culturearound;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.database.EntitiesFirebase.Korisnik;
import com.example.database.Listeners.UserListener;
import com.example.database.UserHelper;
import com.example.culturearound.ProfilKorisnika.UpdateDataDialog;
import com.squareup.picasso.Picasso;


import butterknife.BindView;
import butterknife.ButterKnife;


public class ProfileFragment extends Fragment implements UserListener, View.OnClickListener {
    @BindView(R.id.update_profile)
    Button btnUpdateData;
    @BindView(R.id.profile_userPicture)
    ImageView userPicture;

    @BindView(R.id.profile_name)
    TextView userFirstName;

    @BindView(R.id.profile_surname)
    TextView userLastName;

    @BindView(R.id.profile_email)
    TextView userMail;

    private UserHelper userHelper;

    private String firstName, lastName, email, pictureUrl;

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

        btnUpdateData.setOnClickListener(this);

        userHelper = new UserHelper(getContext(),this);
        Log.d(TAG, "onViewCreated: "+ userHelper.userId);
        userHelper.findUserById();


    }

    private void loadData(Korisnik korisnik){
        Log.d(TAG, "Ucitano " + korisnik.getIme() + korisnik.getPrezime() + korisnik.getEmail());

        firstName = korisnik.getIme();
        lastName = korisnik.getPrezime();
        email = korisnik.getEmail();
        pictureUrl = korisnik.getLokacijaSlike();

        userFirstName.setText(firstName);
        userLastName.setText(lastName);
        userMail.setText(email);
        Picasso.with(getActivity())
                .load(pictureUrl)
                .into(userPicture);

    }

    private void openDialogUpdateData() {
        Log.d(TAG, "OTVORENO " + "DIJALOOOOG");

        Bundle args = new Bundle();
        args.putString("firstName", firstName);
        args.putString("lastName", lastName);
        args.putString("email", email);
        args.putString("url", pictureUrl);

        UpdateDataDialog updateDataDialog = new UpdateDataDialog();
        updateDataDialog.setArguments(args);

        FragmentManager fragManager = getActivity().getSupportFragmentManager();
        updateDataDialog.show(fragManager,"update user data dialog");
    }



    @Override
    public void onLoadUserSuccess(String message, Korisnik currentUser) {
        Log.d(TAG, "Ucitano2 " + currentUser.getIme() + currentUser.getPrezime() + currentUser.getEmail());
        loadData(currentUser);
    }

    @Override
    public void onLoadUserFail(String message) {

    }

    @Override
    public void onClick(View v) {
        openDialogUpdateData();
    }
}
