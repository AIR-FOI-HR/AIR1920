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
import com.example.database.Entities.Znamenitost;
import com.example.database.MyDatabase;
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

        String urlSlike = MyDatabase.getInstance(context).getDAO()
                .loadSlikaById(znamenitost.getId_slika()).getImg_url();;

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

        Intent intent = new Intent(
                itemView.getContext(),
                ZnamDetailsActivity.class
        );
        intent.putExtra(
                "id_znamenitost",
                selectedZnamenitost.getId_znamenitost()
        );

        itemView.getContext().startActivity(intent);
    }
}
