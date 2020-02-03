package com.example.culturearound;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.core.CurrentActivity;
import com.example.culturearound.ProfilKorisnika.ProfileRecyclerAdapter;
import com.example.database.EntitiesFirebase.Komentar;
import com.example.database.EntitiesFirebase.Korisnik;
import com.example.database.EntitiesFirebase.Znamenitost;
import com.example.database.Listeners.RecenzijaListener;
import com.example.database.Listeners.UserListener;
import com.example.database.Listeners.ZnamenitostListener;
import com.example.database.RecenzijeHelper;
import com.example.database.UserHelper;
import com.example.culturearound.ProfilKorisnika.UpdateDataDialog;
import com.example.database.ZnamenitostiHelper;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProfileFragment extends Fragment implements UserListener, View.OnClickListener, RecenzijaListener {

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

    @BindView(R.id.logoutBtn)
    ImageView logout;

    @BindView(R.id.recenzije)
    RecyclerView recyclerView;

    @BindView(R.id.level_points)
    TextView points;

    @BindView(R.id.slikaGemifikacije)
    ImageView slikaGem;


    @BindView(R.id.level_name)
    TextView levelIme;



    private UserHelper userHelper;
    private RecenzijeHelper recenzijeHelper;
    private ProfileRecyclerAdapter profileRecyclerAdapter;
    private String firstName, lastName, email, pictureUrl;
    private String userId;
    private List<Komentar> listOfComments;
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
        listOfComments = new ArrayList<>();

        profileRecyclerAdapter = new ProfileRecyclerAdapter(CurrentActivity.getActivity(),listOfComments);
        recyclerView.setAdapter(profileRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        userPicture.bringToFront();
        btnUpdateData.setOnClickListener(this);
        logout.setOnClickListener(this);
        userHelper = new UserHelper(getContext(),this);
        userId = userHelper.returnUserId();
        recenzijeHelper = new RecenzijeHelper(getContext(), this);
        Log.d(TAG, "onViewCreated: "+ userId);
        userHelper.findUserById(userId);

    }

    int point;

    private void loadData(Korisnik korisnik){
        Log.d("ucitaj", "Ucitano " + korisnik.getIme() + korisnik.getPrezime());

        firstName = korisnik.getIme();
        lastName = korisnik.getPrezime();
        email = korisnik.getEmail();
        pictureUrl = korisnik.getLokacijaSlike();

        point = korisnik.getProgressbar();
        points.setText(Integer.toString(point));

        userFirstName.setText(firstName);
        userLastName.setText(lastName);
        userMail.setText(email);
        Picasso.with(getActivity())
                .load(pictureUrl)
                .into(userPicture);


        recenzijeHelper.dohvatiRecenzijePremaKorisniku(korisnik.getUid());
        //ZnamenitostiHelper znamenitostiHelper = new ZnamenitostiHelper(CurrentActivity.getActivity(), this);
        //znamenitostiHelper.dohvatiMojeZnamenitosti();


        if(point<=100){

            levelIme.setText("Level 1-Izgubljeni početnik");
            slikaGem.setImageResource(R.drawable.slika1);

        }

        else if(point>100 && point<=200){

            levelIme.setText("Level 2-Šokirani paničar ");
            slikaGem.setImageResource(R.drawable.slika2);

        }

        else if(point>200 && point<=300){

            levelIme.setText("Level 3-Zaigrani amater");
            slikaGem.setImageResource(R.drawable.slika3);

        }

        else if(point>300 && point<=400){

            levelIme.setText("Level 4-Zbunjeni laik");
            slikaGem.setImageResource(R.drawable.slika4);

        }

        else if(point>400 && point<=500){

            levelIme.setText("Level 5-Socijalni istraživač");
            slikaGem.setImageResource(R.drawable.slika5);

        }

        else if(point>500 && point<=600){

            levelIme.setText("Level 6-Treasure Hunter");
            slikaGem.setImageResource(R.drawable.slika6);

        }

        else if(point>600 && point<=700){

            levelIme.setText("Level 7-Veseli profić");
            slikaGem.setImageResource(R.drawable.slika7);

        }

        else if(point>700 && point<=800){

            levelIme.setText("Level 8-Veliki avanturista");
            slikaGem.setImageResource(R.drawable.slika8);

        }

        else if(point>800 && point<=900){

            levelIme.setText("Level 9-Društveni manijak");
            slikaGem.setImageResource(R.drawable.slika9);

        }

        else if(point>900){

            levelIme.setText("Level 10-Kralj putovanja");
            slikaGem.setImageResource(R.drawable.slika10);

        }
    }


    private void openDialogUpdateData() {

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
        loadData(currentUser);


    }

    @Override
    public void onLoadUserFail(String message) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.update_profile:
                openDialogUpdateData();
                break;

            case R.id.logoutBtn:
                userHelper.signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;

        }


    }

    @Override
    public void onLoadRecenzijaSucess(String message, List<Komentar> listaKomentara) {
        if(!listaKomentara.isEmpty()){
        listOfComments =listaKomentara;
        Log.d("LukaEna", "Prvi komentar " + listaKomentara.get(0).getOpis());

        profileRecyclerAdapter.setKomentari(listOfComments);
        profileRecyclerAdapter.notifyDataSetChanged();}
    }

    @Override
    public void onOcjenaKomentaraSucess(String message, String mojaOcjenaKomentara) {

    }

    @Override
    public void onLoadRecenzijaFail(String message) {

    }

}
