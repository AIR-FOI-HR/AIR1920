package com.example.culturearound;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.core.CurrentActivity;
import com.example.core.DataLoadedListener;
import com.example.core.DataLoader;
import com.example.culturearound.PretrazivanjeZnamenitosti.recyclerview.ZnamenitostRecyclerAdapter;
import com.example.database.Entities.Korisnik;
import com.example.database.Entities.Lokacija;
import com.example.database.Entities.Slika;
import com.example.database.Entities.Znamenitost;
import com.example.database.MyDatabase;

import java.util.ArrayList;
import java.util.List;

import Data.MockData;
import butterknife.BindView;
import butterknife.ButterKnife;
import loaders.DbDataLoader;

public class HomeFragment extends Fragment implements DataLoadedListener, View.OnClickListener {

    @BindView(R.id.homeImageView1)
    ImageView image1;
    @BindView(R.id.homeImageView2)
    ImageView image2;
    @BindView(R.id.homeImageView3)
    ImageView image3;
    @BindView(R.id.homeImageView4)
    ImageView image4;
    @BindView(R.id.home_recycler)
    RecyclerView recyclerView;

    public static MyDatabase database;
    private List<Znamenitost> znamenitosti;
    private ZnamenitostRecyclerAdapter znamenitostRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        database = MyDatabase.getInstance(getActivity());

        //dohvat podataka
        ButterKnife.bind(this, view);
        mockData();
        loadData();

        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
        image3.setOnClickListener(this);
        image4.setOnClickListener(this);
    }

    public void loadData()
    {
        /*
        final List<String> listItems = database.getDAO().getKorisnickoIme();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        mListView.setAdapter(adapter);
        */
        DataLoader dataLoader = new DbDataLoader(getActivity());
        //DataLoader dataLoader = new WsDataLoader();
        dataLoader.loadData(this);
    }
    private void mockData(){
        List<Korisnik> korinici = database.getDAO().loadAllKorisnik();
        List<Znamenitost> znamenitosti = database.getDAO().loadAllZnamenitosti();
        List<Slika> slike = database.getDAO().loadAllSlike();



        if(!znamenitosti.isEmpty()){
            for(Znamenitost z : znamenitosti){
                Log.d("Znamenitosti", "Znamenitost: " + z.getNaziv());
            }
        }else {
            MockData.writeData(getActivity());
        }

        if (!korinici.isEmpty()) {
            for (Korisnik k : korinici) {
                Log.d("AIRAIR","Korisnik: " + k.getIme());
                Log.d("AIRAIR","Korisnik: " + k.getPrezime());
            }
        }else {
            MockData.writeData(getActivity());
        }
    }

    @Override
    public void onDataLoaded(List<Korisnik> korisnici, List<Znamenitost> znamenitosti, List<Lokacija> lokacije) {
        this.znamenitosti = znamenitosti;
        znamenitostRecyclerAdapter = new ZnamenitostRecyclerAdapter(getActivity(), znamenitosti);

        recyclerView.setAdapter(znamenitostRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void kategorijaButton(View view) {
        if(!znamenitosti.isEmpty()){

            List<Znamenitost> prikazaneZnamenitosti = new ArrayList<>();
            for (Znamenitost znamenitost: znamenitosti) {
                if(znamenitost.getId_kategorija_znamenitosti() == Integer.valueOf((String) view.getTag()))
                    prikazaneZnamenitosti.add(znamenitost);
            }

            if (!prikazaneZnamenitosti.isEmpty()){
                znamenitostRecyclerAdapter.setZnamenitosti(prikazaneZnamenitosti);
                znamenitostRecyclerAdapter.notifyDataSetChanged();
            }
            else
                Toast.makeText(getActivity(), "Ne postoje znamenitosti u ovoj kategoriji", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homeImageView1: case R.id.homeImageView2: case R.id.homeImageView3: case R.id.homeImageView4:
                kategorijaButton(v);
                break;
        }
    }
}
