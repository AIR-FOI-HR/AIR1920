package com.example.culturearound.ProfilKorisnika;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.core.CurrentActivity;
import com.example.culturearound.R;
import com.example.database.EntitiesFirebase.Komentar;
import com.example.database.EntitiesFirebase.Znamenitost;
import com.example.database.UserHelper;

import java.util.List;

public class ProfileRecyclerAdapter extends RecyclerView.Adapter<ProfileRecyclerHolder> {

    private Context context;
    private List<Komentar> komentari;


    public List<Komentar> getKomentari() {return komentari;}


    public void setKomentari(List<Komentar> komentari) {
        this.komentari = komentari;
        Log.d("Anja", "Učitani komentari");
    }

    public ProfileRecyclerAdapter(Context context, @NonNull List<Komentar> komentari) {
        this.context = context;
        this.komentari = komentari;
    }


    @NonNull
    @Override
    public ProfileRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View profileRecylerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recenzija_lista, parent, false);
        return new ProfileRecyclerHolder(profileRecylerView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileRecyclerHolder holder, int position) {
        holder.bindToData(komentari.get(position), context);
    }

    @Override
    public int getItemCount() {
        return komentari.size();
    }
}
