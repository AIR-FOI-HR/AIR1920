package com.example.culturearound.PretrazivanjeZnamenitosti.recyclerview;

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



    public void bindToData(Znamenitost znamenitost){
        this.selectedZnamenitost = znamenitost;
        znamenitostName.setText(znamenitost.getNaziv());
        znamenitostDesc.setText(znamenitost.getOpis());
        //dobavljanje slike je zasad hardkodirano
        Picasso.with(itemView
                .getContext())
                .load("https://www.middleweb.com/wp-content/uploads/2018/04/yoda-300x222.jpg")
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
