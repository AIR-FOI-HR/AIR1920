package com.example.culturearound;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.core.CurrentActivity;
import com.example.culturearound.PretrazivanjeZnamenitosti.recyclerview.ZnamenitostRecyclerAdapter;
import com.example.database.EntitiesFirebase.Znamenitost;
import com.example.database.Listeners.ZnamenitostListener;
import com.example.database.ZnamenitostiHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesFragment extends Fragment implements ZnamenitostListener {

    @BindView(R.id.favorites_recycler)
    RecyclerView recyclerView;

    private List<Znamenitost> znamenitosti;
    private FavoritesRecyclerAdapter favoritesRecyclerAdapter;
    private ZnamenitostiHelper znamenitostiHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        znamenitosti = new ArrayList<>();

        //početno postavljanje recyclerView-a
        favoritesRecyclerAdapter = new FavoritesRecyclerAdapter(CurrentActivity.getActivity(), znamenitosti);
        recyclerView.setAdapter(favoritesRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        znamenitostiHelper = new ZnamenitostiHelper(CurrentActivity.getActivity(), this);
        znamenitostiHelper.dohvatiSveZnamenitosti();
    }


    @Override
    public void onLoadZnamenitostSucess(String message, List<Znamenitost> listaZnamenitosti) {
        if (!listaZnamenitosti.isEmpty()){
            this.znamenitosti = listaZnamenitosti;
            favoritesRecyclerAdapter.setZnamenitosti(znamenitosti);
            favoritesRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoadZnamenitostFail(String message) {

    }
}
