package com.example.spomenik_znamenitost.recyclerView;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.example.database.EntitiesFirebase.Slika;
import com.example.spomenik_znamenitost.R;
import com.squareup.picasso.Picasso;

public class GalerijaViewHolder extends ParentViewHolder implements View.OnClickListener{
    private SlikaGalerijeListener slikaGalerijeListener;

    private View itemView;
    public View getItemView() {
        return itemView;
    }

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
        itemView.setOnClickListener(this);
    }

    public void bindToData(Slika slika, SlikaGalerijeListener slikaGalerijeListener){
        this.slikaGalerijeListener = slikaGalerijeListener;
        this.odabranaSlika = slika;
        imgGalerija = itemView.findViewById(R.id.imgGalerija);
        Picasso.with(itemView
                .getContext())
                .load(odabranaSlika.getLokacijaSlike())
                .into(imgGalerija);
        CardView cardView = itemView.findViewById(R.id.cardViewSlikeGalerije);
        cardView.getLayoutParams().width = imgGalerija.getLayoutParams().width;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        slikaGalerijeListener.onItemClick(odabranaSlika.getLokacijaSlike());
    }
}
