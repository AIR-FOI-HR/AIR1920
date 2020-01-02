package com.example.culturearound.PretrazivanjeZnamenitosti.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.culturearound.R;
import com.example.database.Entities.Znamenitost;

import java.util.List;

public class ZnamenitostRecyclerAdapter extends RecyclerView.Adapter<ZnamenitostViewHolder> {
    private Context context;

    public List<Znamenitost> getZnamenitosti() {
        return znamenitosti;
    }

    public void setZnamenitosti(List<Znamenitost> znamenitosti) {
        this.znamenitosti = znamenitosti;
    }

    private List<Znamenitost> znamenitosti;


    public ZnamenitostRecyclerAdapter(Context context, @NonNull List<Znamenitost> znamenitosti) {
        this.context = context;
        this.znamenitosti = znamenitosti;
    }

    @NonNull
    @Override
    public ZnamenitostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View znamenitostView = LayoutInflater.from(parent.getContext()).inflate(R.layout.znamenitost_list_item, parent, false);
        return new ZnamenitostViewHolder(znamenitostView);
    }

    @Override
    public void onBindViewHolder(@NonNull ZnamenitostViewHolder holder, int position) {
        holder.bindToData(znamenitosti.get(position), context);
    }


    @Override
    public int getItemCount() {
        return znamenitosti.size();
    }
}
