package com.example.default_znamenitost.recyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.default_znamenitost.R;
import com.example.database.EntitiesFirebase.Komentar;

import java.util.List;

public class RecenzijeRecycelerAdapter extends RecyclerView.Adapter<RecenzijeViewHolder> {


    private Context context;
    private List<Komentar> komentari;


    public List<Komentar> getKomentari() {return komentari;}


    public void setKomentari(List<Komentar> komentari) {
        this.komentari = komentari;
        Log.d("Anja", "Učitani komentari");
    }


    public RecenzijeRecycelerAdapter(Context context, @NonNull List<Komentar> komentari) {
        this.context = context;
        this.komentari = komentari;
    }



    @NonNull
    @Override
    public RecenzijeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View recenzijeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recenzija_lista, parent, false);

        return new RecenzijeViewHolder(recenzijeView);

    }

    @Override
    public void onBindViewHolder(@NonNull RecenzijeViewHolder holder, int position) {
        Log.d("Anja", "Bindanje..");
        holder.bindToData(komentari.get(position), context);
        Log.d("Anja", "Bindanje..DONE");

    }

    @Override
    public int getItemCount() {
        return komentari.size();
    }
}
