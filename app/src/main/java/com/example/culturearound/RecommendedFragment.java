package com.example.culturearound;



import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.core.CurrentActivity;

import com.example.database.Listeners.ZnamenitostListener;

import com.example.database.ZnamenitostiHelper;
import com.example.culturearound.PretrazivanjeZnamenitosti.recyclerview.ZnamenitostRecyclerAdapter;
import com.example.database.EntitiesFirebase.Znamenitost;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;



import butterknife.BindView;
import butterknife.ButterKnife;

public class RecommendedFragment extends Fragment implements ZnamenitostListener {

    @BindView(R.id.home_recycler)
    RecyclerView recyclerView;


    private List<Znamenitost> znamenitosti;
    private ZnamenitostRecyclerAdapter znamenitostRecyclerAdapter;


    private ZnamenitostiHelper znamenitostiHelper;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recommended,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        znamenitosti = new ArrayList<>();

        //početno postavljanje recyclerView-a
        znamenitostRecyclerAdapter = new ZnamenitostRecyclerAdapter(getActivity(), znamenitosti);
        recyclerView.setAdapter(znamenitostRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));




        //rad s znamenitostima
        znamenitostiHelper = new ZnamenitostiHelper(CurrentActivity.getActivity(), this);
        znamenitostiHelper.dohvatiSveZnamenitosti();
    }





    private List<Znamenitost> sortiranjeZnamenitosti(List<Znamenitost> prikazaneZnamenitosti){

        Collections.sort(prikazaneZnamenitosti);



        return prikazaneZnamenitosti;
    }



    @Override
    public void onLoadZnamenitostSucess(String message, List<Znamenitost> listaZnamenitosti) {
        Log.d("ZnamenitostTag", message);
        if (!listaZnamenitosti.isEmpty()){
            this.znamenitosti = listaZnamenitosti;
            znamenitosti= sortiranjeZnamenitosti(znamenitosti);

            znamenitostRecyclerAdapter.setZnamenitosti(znamenitosti);
            znamenitostRecyclerAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onLoadZnamenitostFail(String message) {
        Log.d("ZnamenitostTag", message);
    }

}
