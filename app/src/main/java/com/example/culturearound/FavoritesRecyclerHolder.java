package com.example.culturearound;
import android.content.Context;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.example.database.EntitiesFirebase.Znamenitost;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FavoritesRecyclerHolder extends ParentViewHolder {
    private View itemView;
    private Znamenitost selectedZnamenitost = null;

    @BindView(R.id.znamenitost_name)
    TextView znamenitostName;
    @BindView(R.id.znamenitost_desc)
    TextView znamenitostDesc;
    @BindView(R.id.znamenitost_image)
    ImageView znamenitostImage;


    public FavoritesRecyclerHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);
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

}
