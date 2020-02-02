package com.example.culturearound.PretrazivanjeZnamenitosti.recyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.core.CurrentActivity;
import com.example.culturearound.FavoritesRecyclerHolder;
import com.example.database.EntitiesFirebase.Korisnik;
import com.example.database.EntitiesFirebase.Znamenitost;
import com.example.culturearound.R;
import com.example.database.FirebaseHelper;
import com.example.database.Listeners.LoginListener;
import com.example.database.Listeners.UserListener;
import com.example.database.LoginHelper;
import com.example.database.UserHelper;

import java.util.List;

public class ZnamenitostRecyclerAdapter extends RecyclerView.Adapter<ZnamenitostViewHolder> implements UserListener{
    private Context context;
    private List<Znamenitost> znamenitosti;
    private UserHelper userHelper;
    private String userId;
    private Korisnik currentUser ;



    public List<Znamenitost> getZnamenitosti() {
        return znamenitosti;
    }

    public void setZnamenitosti(List<Znamenitost> znamenitosti) {
        this.znamenitosti = znamenitosti;
    }

    public ZnamenitostRecyclerAdapter(Context context, @NonNull List<Znamenitost> znamenitosti) {
        this.context = context;
        this.znamenitosti = znamenitosti;
        userHelper = new UserHelper(CurrentActivity.getActivity(), this);
        if(userHelper.checkIfSignedIn()){
            userId = userHelper.returnUserId();
            userHelper.findUserById(userId);
        }

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
        if(currentUser != null){
            isItInFavourites(holder.favoriteButton, holder.returnSelectedFavorite().getIdZnamenitosti());
            addItemToFavorites(position, holder);
        }
    }



    private void addItemToFavorites(int position, ZnamenitostViewHolder holder){
        ZnamenitostViewHolder znamenitostViewHolder = holder;
        Znamenitost selectedFavorite = znamenitostViewHolder.returnSelectedFavorite();

        znamenitostViewHolder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View button) {
                if(!button.isSelected()) {
                    button.setSelected(true);
                    userHelper.addItemToFavourites(userId, selectedFavorite.getIdZnamenitosti());
                }
                else {
                    button.setSelected(false);
                    userHelper.removeItemFromFavorites(userId,selectedFavorite.getIdZnamenitosti());
                }

            }

        });
    }

    private void isItInFavourites (ImageButton favoriteButton, Integer selectedId){
        List<Integer> listOfFavouritesID = currentUser.getIdoviZnamenitosti() ;
        if(listOfFavouritesID.contains(selectedId)){
            favoriteButton.setSelected(true);}
        else {
            favoriteButton.setSelected(false);
        }

        }


    @Override
    public int getItemCount() {
        return znamenitosti.size();
    }

    @Override
    public void onLoadUserSuccess(String message, Korisnik currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public void onLoadUserFail(String message) {

    }
}
