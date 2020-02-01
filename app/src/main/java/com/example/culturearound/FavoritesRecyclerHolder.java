package com.example.culturearound;
import android.content.Context;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.example.culturearound.FavoritesRecyclerAdapter;
import com.example.culturearound.R;
import com.example.database.EntitiesFirebase.Korisnik;
import com.example.database.EntitiesFirebase.Znamenitost;
import com.example.database.FirebaseHelper;
import com.example.database.Listeners.UserFavouriteZnamenitostListener;
import com.example.database.Listeners.UserListener;
import com.example.database.Listeners.ZnamenitostListener;
import com.example.database.UserHelper;
import com.example.database.ZnamenitostiHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;




public class FavoritesRecyclerHolder extends ParentViewHolder implements UserListener, UserFavouriteZnamenitostListener, View.OnClickListener{
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


    public FavoritesRecyclerHolder(@NonNull View itemView, @NonNull FavoritesRecyclerAdapter adapter) {
        super(itemView);
        this.itemView = itemView;
        this.adapter = adapter;

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

    @Override
    public void onFavouriteZnamenitostRemoved(Integer znamenitostId) {

    }
}
