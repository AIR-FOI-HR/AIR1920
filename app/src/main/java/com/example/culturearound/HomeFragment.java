package com.example.culturearound;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.core.DataLoadedListener;
import com.example.core.DataLoader;
import com.example.culturearound.PretrazivanjeZnamenitosti.recyclerview.ZnamenitostRecyclerAdapter;
import com.example.database.Entities.Korisnik;
import com.example.database.Entities.Lokacija;
import com.example.database.Entities.Slika;
import com.example.database.Entities.Znamenitost;
import com.example.database.MyDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Data.MockData;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import loaders.DbDataLoader;

public class HomeFragment extends Fragment implements DataLoadedListener, View.OnClickListener {

    @BindView(R.id.btnMuzej)
    Button btnMuzej;
    @BindView(R.id.btnGalerija)
    Button btnGalerija;
    @BindView(R.id.btnSpomenik)
    Button btnSpomenik;
    @BindView(R.id.btnSetaliste)
    Button btnSetaliste;
    @BindView(R.id.btnKazaliste)
    Button btnKazaliste;
    @BindView(R.id.btnKino)
    Button btnKino;
    @BindView(R.id.home_recycler)
    RecyclerView recyclerView;

    @BindDrawable(R.drawable.button_yellow)
    Drawable button_yellow;
    @BindDrawable(R.drawable.button_blue)
    Drawable button_blue;

    public static MyDatabase database;
    private List<Znamenitost> znamenitosti;
    private ZnamenitostRecyclerAdapter znamenitostRecyclerAdapter;
    private List<View> listaGumba;

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

        listaGumba = Arrays.asList(
                btnMuzej, btnGalerija, btnSpomenik, btnSetaliste, btnKazaliste, btnKino);
        for (View gumb: listaGumba){
            gumb.setOnClickListener(this);
            gumb.setBackground(button_yellow);
        }
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

    public void promijeniPrikazPodataka(List<View> listaOdabranihKategorija){
        if(!znamenitosti.isEmpty()){
            List<Znamenitost> prikazaneZnamenitosti = new ArrayList<>();

            for (View kategorija: listaOdabranihKategorija){
                for (Znamenitost znamenitost: znamenitosti) {
                    if(znamenitost.getId_kategorija_znamenitosti() == Integer.valueOf((String) kategorija.getTag()))
                        prikazaneZnamenitosti.add(znamenitost);
                }
            }
            if (prikazaneZnamenitosti.isEmpty()){
                Toast.makeText(getActivity(), "Ne postoje znamenitosti u ovim kategorijama", Toast.LENGTH_SHORT).show();
            }
            znamenitostRecyclerAdapter.setZnamenitosti(prikazaneZnamenitosti);
            znamenitostRecyclerAdapter.notifyDataSetChanged();
        }
        else Toast.makeText(getActivity(), "Ni jedna znamenitost nije učitana.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        //promijeniPrikazPodataka(v);
        if (v.getBackground() == button_yellow) v.setBackground(button_blue);
        else v.setBackground(button_yellow);

        List<View> listaOdabranihKategorija = new ArrayList<>();
        for (View gumb: listaGumba){
            if (gumb.getBackground() == button_yellow){
                listaOdabranihKategorija.add(gumb);
            }
        }
        promijeniPrikazPodataka(listaOdabranihKategorija);
    }
}
