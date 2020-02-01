package com.example.culturearound;
import android.content.Context;

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
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;




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

    public Znamenitost returnSelectedFavorite (){
        return selectedItem;
    }



    @Override
    public void onLoadUserSuccess(String message, Korisnik currentUser) {

    }



    @Override
    public void onLoadUserFail(String message) {

    }
}
