package com.example.culturearound;
import android.content.Context;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.example.database.EntitiesFirebase.Korisnik;
import com.example.database.EntitiesFirebase.Znamenitost;
import com.example.database.Listeners.UserListener;
import com.example.database.UserHelper;
import com.example.default_znamenitost.DefaultZnamenitostActivity;
import com.example.kino_znamenitost.KinoActivity;
import com.example.setaliste_znamenitost.SetalisteActivity;
import com.example.spomenik_znamenitost.SpomenikActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FavoritesRecyclerHolder extends ParentViewHolder implements UserListener{
    private View itemView;

    @BindView(R.id.znamenitost_name)
    TextView znamenitostName;
    @BindView(R.id.znamenitost_desc)
    TextView znamenitostDesc;
    @BindView(R.id.znamenitost_image)
    ImageView znamenitostImage;

    public ImageButton favoriteButton;

    private UserHelper userHelper;

    private Znamenitost selectedItem ;
    private String userId;
    private FavoritesRecyclerAdapter adapter;


    public FavoritesRecyclerHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;

        ButterKnife.bind(this, itemView);

        favoriteButton= itemView.findViewById(R.id.favoritesButton);

        userHelper = new UserHelper(itemView.getContext(), this);
        userId = userHelper.returnUserId();


    }


    public void bindToData(Znamenitost znamenitost, Context context){

        this.selectedItem = znamenitost;
        String nazivZnamenitosti = znamenitost.getNaziv();
        String opisZnamenitosti = znamenitost.getOpis();
        znamenitostName.setText(nazivZnamenitosti);
        znamenitostDesc.setText(opisZnamenitosti);

        String urlSlike = znamenitost.getLokacijaSlike();

        Picasso.with(itemView
                .getContext())
                .load(urlSlike)
                .into(znamenitostImage);

    }


    //Klik znamenitosti na recycleview-u
    @OnClick
    public void znamenitostSelected(){
        Intent intent = null;
        int kategorija = selectedItem.getIdKategorijaZnamenitosti();

        switch (kategorija){
            case 1:
                intent = postaviIntent(DefaultZnamenitostActivity.class);
                break;
            case 2:
                intent = postaviIntent(DefaultZnamenitostActivity.class);
                break;
            case 3:
                intent = postaviIntent(SpomenikActivity.class);
                break;
            case 4:
                intent = postaviIntent(SetalisteActivity.class);
                break;
            case 5:
                intent = postaviIntent(DefaultZnamenitostActivity.class);
                break;
            case 6:
                intent = postaviIntent(KinoActivity.class);
                break;
            default:
                intent = postaviIntent(DefaultZnamenitostActivity.class);
        }

        if (!intent.equals(null)){
            intent.putExtra(
                    "id_znamenitost",
                    selectedItem.getIdZnamenitosti()
            );

            itemView.getContext().startActivity(intent);
        }
    }


    public Znamenitost returnSelectedFavorite (){
        return selectedItem;
    }


    private Intent postaviIntent(Class activity){
        Intent intent = new Intent(
                itemView.getContext(),
                activity
        );

        return intent;
    }

    @Override
    public void onLoadUserSuccess(String message, Korisnik currentUser) {

    }



    @Override
    public void onLoadUserFail(String message) {

    }
}
