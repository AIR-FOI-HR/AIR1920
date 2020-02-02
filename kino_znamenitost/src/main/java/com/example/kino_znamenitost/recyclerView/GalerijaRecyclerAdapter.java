package com.example.kino_znamenitost.recyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.database.EntitiesFirebase.Slika;
import com.example.kino_znamenitost.R;

import java.util.List;

public class GalerijaRecyclerAdapter extends RecyclerView.Adapter<GalerijaViewHolder> {
    private Context context;

    private List<Slika> slike;

    public List<Slika> getSlike() {
        return slike;
    }
    public void setSlike(List<Slika> slike) {
        this.slike = slike;
    }

    public GalerijaRecyclerAdapter(Context context, @NonNull List<Slika> slike){
        this.context = context;
        this.slike = slike;
    }

    @NonNull
    @Override
    public GalerijaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View galerijaView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.galerija_list_item, parent,false);
        return new GalerijaViewHolder(galerijaView);
    }

    @Override
    public void onBindViewHolder(@NonNull GalerijaViewHolder holder, int position) {
        holder.bindToData(slike.get(position), context);
    }

    @Override
    public int getItemCount() {
        return slike.size();
    }
}
