package com.example.kino_znamenitost.recyclerView;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.example.database.EntitiesFirebase.Slika;
import com.example.kino_znamenitost.R;
import com.squareup.picasso.Picasso;

public class GalerijaViewHolder extends ParentViewHolder {
    private View itemView;
    private Slika odabranaSlika = null;
    ImageView imgGalerija;

    /**
     * Default constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     */
    public GalerijaViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
    }

    public void bindToData(Slika slika, Context context){
        this.odabranaSlika = slika;
        imgGalerija = itemView.findViewById(R.id.imgGalerija);
        Picasso.with(itemView
                .getContext())
                .load(slika.getLokacijaSlike())
                .into(imgGalerija);
        CardView cardView = itemView.findViewById(R.id.cardViewSlikeGalerije);
        cardView.getLayoutParams().width = imgGalerija.getLayoutParams().width;
    }
}
