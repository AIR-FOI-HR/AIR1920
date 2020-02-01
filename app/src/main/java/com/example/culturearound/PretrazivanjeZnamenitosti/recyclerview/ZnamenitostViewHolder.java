package com.example.culturearound.PretrazivanjeZnamenitosti.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.example.culturearound.FavoritesRecyclerAdapter;
import com.example.culturearound.PretrazivanjeZnamenitosti.ZnamDetailsActivity;
import com.example.culturearound.R;
import com.example.database.EntitiesFirebase.Korisnik;
import com.example.database.EntitiesFirebase.Znamenitost;
import com.example.database.Listeners.UserListener;
import com.example.database.UserHelper;
import com.example.spomenik_znamenitost.ZnamDetailsSpomenikActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ZnamenitostViewHolder extends ParentViewHolder implements UserListener {
    private View itemView;
    private Znamenitost selectedZnamenitost = null;

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
    /**
     * Default constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     */
    public ZnamenitostViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);

        favoriteButton= itemView.findViewById(R.id.favoritesButton);

        userHelper = new UserHelper(itemView.getContext(), this);
        userId = userHelper.returnUserId();
    }



    public void bindToData(Znamenitost znamenitost, Context context){
        this.selectedZnamenitost = znamenitost;
        znamenitostName.setText(znamenitost.getNaziv());
        znamenitostDesc.setText(znamenitost.getOpis());

        String urlSlike = znamenitost.getLokacijaSlike();

        Picasso.with(itemView
                .getContext())
                .load(urlSlike)
                .into(znamenitostImage);
    }

    //Klik znamenitosti na recycleview-u
    @OnClick
    public void znamenitostSelected(){
        Toast.makeText(
                itemView.getContext(),
                selectedZnamenitost.getNaziv(),
                Toast.LENGTH_SHORT
        ).show();

        Intent intent = null;
        int kategorija = selectedZnamenitost.getIdKategorijaZnamenitosti();

        switch (kategorija){
            case 1:
                intent = postaviIntent(ZnamDetailsActivity.class);
                break;
            case 2:
                intent = postaviIntent(ZnamDetailsActivity.class);
                break;
            case 3:
                intent = postaviIntent(ZnamDetailsSpomenikActivity.class);
                break;
            case 4:
                intent = postaviIntent(ZnamDetailsActivity.class);
                break;
            case 5:
                intent = postaviIntent(ZnamDetailsActivity.class);
                break;
            case 6:
                intent = postaviIntent(ZnamDetailsActivity.class);
                break;
            default:
                intent = postaviIntent(ZnamDetailsActivity.class);
        }

        if (!intent.equals(null)){
            intent.putExtra(
                    "id_znamenitost",
                    selectedZnamenitost.getIdZnamenitosti()
            );

            itemView.getContext().startActivity(intent);
        }
    }

    public Znamenitost returnSelectedFavorite (){
        return selectedZnamenitost;
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
