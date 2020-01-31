package com.example.culturearound.FavoritiRecycler;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.database.EntitiesFirebase.Znamenitost;
import com.example.culturearound.R;

import java.util.List;

public class FavoritesRecyclerAdapter extends RecyclerView.Adapter<FavoritesRecyclerHolder> {
    private Context context;
    private List<Znamenitost> znamenitosti;


    public void setZnamenitosti(List<Znamenitost> znamenitosti) {
        this.znamenitosti = znamenitosti;
    }

    public FavoritesRecyclerAdapter(Context context, @NonNull List<Znamenitost> znamenitosti) {
        this.context = context;
        this.znamenitosti = znamenitosti;
    }

    @NonNull
    @Override
    public FavoritesRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View znamenitostView = LayoutInflater.from(parent.getContext()).inflate(R.layout.znamenitost_list_item, parent, false);
        return new FavoritesRecyclerHolder(znamenitostView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesRecyclerHolder holder, int position) {
        holder.bindToData(znamenitosti.get(position), context);
    }


    @Override
    public int getItemCount() {
        return znamenitosti.size();
    }
}
