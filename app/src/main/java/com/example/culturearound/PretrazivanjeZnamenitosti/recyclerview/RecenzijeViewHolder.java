package com.example.culturearound.PretrazivanjeZnamenitosti.recyclerview;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.content.Context;
import androidx.annotation.NonNull;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.example.culturearound.Firebase.EntitiesFirebase.Komentar;
import com.example.culturearound.R;

public class RecenzijeViewHolder extends ParentViewHolder {
   // private View itemView;
    //private Komentar selectedKomentar=null;


    @BindView(R.id.recenzija_opis)
    TextView opis;

    @BindView(R.id.recenzija_ocjena)
    TextView ocjena;


    /**
     * Default constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     */
    public RecenzijeViewHolder(@NonNull View itemView) {
        super(itemView);
        //this.itemView=itemView;
        ButterKnife.bind(this, itemView);
    }



    public void bindToData (Komentar komentar, Context context){
        //this.selectedKomentar=komentar;
        Log.d("Anja", "Setaj--opis");
        Log.d("Anja", komentar.getOpis()+komentar.getIdKomentar());
        opis.setText(komentar.getOpis());

        Log.d("Anja", "Setaj--ocjenu");
        ocjena.setText(Integer.toString(komentar.getOcjena()));
        Log.d("Anja", "Setano sve");

    }






}
