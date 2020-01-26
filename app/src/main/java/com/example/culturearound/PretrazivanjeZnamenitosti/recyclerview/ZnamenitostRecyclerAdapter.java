package com.example.culturearound.PretrazivanjeZnamenitosti.recyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.culturearound.Firebase.EntitiesFirebase.Znamenitost;
import com.example.culturearound.R;

import java.util.List;

public class ZnamenitostRecyclerAdapter extends RecyclerView.Adapter<ZnamenitostViewHolder> {
    private Context context;
    private List<Znamenitost> znamenitosti;

    public List<Znamenitost> getZnamenitosti() {
        return znamenitosti;
    }

    public void setZnamenitosti(List<Znamenitost> znamenitosti) {
        Log.d("RecyAdapter", "Setting znamenitosti...");
        this.znamenitosti = znamenitosti;
        Log.d("RecyAdapter", "Setting znamenitosti DONE.");
    }

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
        Log.d("RecyAdapter", "Bindat na viewHolder...");
        holder.bindToData(znamenitosti.get(position), context);
        Log.d("RecyAdapter", "Bindat na viewHolder DONE.");
    }


    @Override
    public int getItemCount() {
        return znamenitosti.size();
    }
}
