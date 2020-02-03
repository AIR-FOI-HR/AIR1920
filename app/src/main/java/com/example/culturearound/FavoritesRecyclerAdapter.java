package com.example.culturearound;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.core.CurrentActivity;
import com.example.culturearound.FavoritesRecyclerHolder;
import com.example.culturearound.R;
import com.example.database.EntitiesFirebase.Korisnik;
import com.example.database.EntitiesFirebase.Znamenitost;
import com.example.database.Listeners.UserListener;
import com.example.database.UserHelper;

import java.util.List;

public class FavoritesRecyclerAdapter extends RecyclerView.Adapter<FavoritesRecyclerHolder> implements UserListener {

    private Context context;
    private List<Znamenitost> znamenitosti;
    private UserHelper userHelper;
    private String userId;



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
        userHelper = new UserHelper(CurrentActivity.getActivity(), this);
        userId = userHelper.returnUserId();
        return new FavoritesRecyclerHolder(znamenitostView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesRecyclerHolder holder, int position) {
        holder.bindToData(znamenitosti.get(position), context);
        removeItem(position,holder);
    }

    private void removeItem(int position, FavoritesRecyclerHolder holder){
        FavoritesRecyclerHolder favoritesRecyclerHolder = (FavoritesRecyclerHolder) holder;
        Znamenitost selectedFavorite = favoritesRecyclerHolder.returnSelectedFavorite();
        favoritesRecyclerHolder.favoriteButton.setSelected(true);

            favoritesRecyclerHolder.favoriteButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View button) {
                    if(button.isSelected()) {
                        button.setSelected(false);
                        userHelper.removeItemFromFavorites(userId, selectedFavorite.getIdZnamenitosti());
                    }
                    else {
                        button.setSelected(true);
                    }

                }

            });
        }


    @Override
    public int getItemCount() {
        return znamenitosti.size();
    }

    @Override
    public void onLoadUserSuccess(String message, Korisnik currentUser) {

    }

    @Override
    public void onLoadUserFail(String message) {
    }
}
