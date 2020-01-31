package com.example.culturearound.PretrazivanjeZnamenitosti.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.example.culturearound.PretrazivanjeZnamenitosti.ZnamDetailsActivity;
import com.example.culturearound.R;
import com.example.database.EntitiesFirebase.Znamenitost;
import com.example.default_znamenitost.DefaultZnamenitostActivity;
import com.example.kino_znamenitost.KinoActivity;
import com.example.setaliste_znamenitost.SetalisteActivity;
import com.example.spomenik_znamenitost.SpomenikActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ZnamenitostViewHolder extends ParentViewHolder {
    private View itemView;
    private Znamenitost selectedZnamenitost = null;

    @BindView(R.id.znamenitost_name)
    TextView znamenitostName;
    @BindView(R.id.znamenitost_desc)
    TextView znamenitostDesc;
    @BindView(R.id.znamenitost_image)
    ImageView znamenitostImage;
    /**
     * Default constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     */
    public ZnamenitostViewHolder(@NonNull View itemView) {
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

    //Klik znamenitosti na recycleview-u
    @OnClick
    public void znamenitostSelected(){
        Intent intent = null;
        int kategorija = selectedZnamenitost.getIdKategorijaZnamenitosti();

        switch (kategorija){
            case 1:
                intent = postaviIntent(DefaultZnamenitostActivity.class);
                break;
            case 2:
                intent = postaviIntent(ZnamDetailsActivity.class);
                break;
            case 3:
                intent = postaviIntent(SpomenikActivity.class);
                break;
            case 4:
                intent = postaviIntent(SetalisteActivity.class);
                break;
            case 5:
                intent = postaviIntent(ZnamDetailsActivity.class);
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
                    selectedZnamenitost.getIdZnamenitosti()
            );

            itemView.getContext().startActivity(intent);
        }
    }

    private Intent postaviIntent(Class activity){
        Intent intent = new Intent(
                itemView.getContext(),
                activity
        );

        return intent;
    }
}
