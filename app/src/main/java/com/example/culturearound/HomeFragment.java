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

import com.example.core.CurrentActivity;
import com.example.culturearound.Firebase.Listeners.ZnamenitostListener;
import com.example.culturearound.Firebase.ZnamenitostiHelper;
import com.example.culturearound.PretrazivanjeZnamenitosti.recyclerview.ZnamenitostRecyclerAdapter;
import com.example.culturearound.Firebase.EntitiesFirebase.Znamenitost;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements View.OnClickListener, ZnamenitostListener {

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

    private List<Znamenitost> znamenitosti;
    private ZnamenitostRecyclerAdapter znamenitostRecyclerAdapter;
    private List<View> listaGumba;
    private List<View> listaOdabranihKategorija;

    private ZnamenitostiHelper znamenitostiHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("FirebaseTag", "Znamenitosti će da se rade jer smo na home fragmentu.");
        ButterKnife.bind(this, view);
        znamenitosti = new ArrayList<>();

        znamenitostRecyclerAdapter = new ZnamenitostRecyclerAdapter(getActivity(), znamenitosti);
        recyclerView.setAdapter(znamenitostRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //rad s kategorijama
        listaGumba = Arrays.asList(
                btnMuzej, btnGalerija, btnSpomenik, btnSetaliste, btnKazaliste, btnKino);
        for (View gumb: listaGumba){
            gumb.setOnClickListener(this);
            gumb.setBackground(button_yellow);
        }
        listaOdabranihKategorija = listaGumba;

        Log.d("RecyAdapter", "Inicijalizirat helper...");
        znamenitostiHelper = new ZnamenitostiHelper(CurrentActivity.getActivity(), this);
        Log.d("RecyAdapter", "Inicijalizirat dohvaćanje...");
        znamenitostiHelper.dohvatiSveZnamenitosti();
    }


    public void promijeniPrikazPodataka(){
        if (!znamenitosti.isEmpty()){
            List<Znamenitost> prikazaneZnamenitosti = new ArrayList<Znamenitost>();

            for (View kategorija: listaOdabranihKategorija){
                for (Znamenitost znamenitost: znamenitosti) {
                    Log.d("FirebaseTag", "IF (kategorije)" + znamenitost.getIdKategorijaZnamenitosti() + " jednako " + Integer.valueOf((String) kategorija.getTag()));
                    if (znamenitost.getIdKategorijaZnamenitosti() == Integer.valueOf((String) kategorija.getTag())){
                        Log.d("FirebaseTag", "TRUE - dodajem na listu.");
                        prikazaneZnamenitosti.add(znamenitost);
                    }
                }
            }
            if (prikazaneZnamenitosti.isEmpty()){
                Log.d("FirebaseTag", "Ne postoje znamenitosti u ovim kategorijama");
                Toast.makeText(getActivity(), "Ne postoje znamenitosti u ovim kategorijama", Toast.LENGTH_SHORT).show();
            }
            Log.d("RecyAdapter", "RecyAdapter - Postavi novu listu znamenitosti na adapter...");
            znamenitostRecyclerAdapter.setZnamenitosti(prikazaneZnamenitosti);
            Log.d("RecyAdapter", "RecyAdapter - Notify data change...");
            znamenitostRecyclerAdapter.notifyDataSetChanged();
        }
        else {
            Log.d("FirebaseTag", "Ni jedna znamenitost nije učitana.");
            Toast.makeText(getActivity(), "Ni jedna znamenitost nije učitana.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        //promijeniPrikazPodataka(v);
        if (v.getBackground() == button_yellow) v.setBackground(button_blue);
        else v.setBackground(button_yellow);

        listaOdabranihKategorija = new ArrayList<>();
        for (View gumb: listaGumba){
            if (gumb.getBackground() == button_yellow){
                listaOdabranihKategorija.add(gumb);
            }
        }
        promijeniPrikazPodataka();
    }

    @Override
    public void onLoadSucess(String message, List<Znamenitost> listaZnamenitosti) {
        Log.d("FirebaseTag", "Load Sucess");
        if (!listaZnamenitosti.isEmpty()){
            this.znamenitosti = listaZnamenitosti;
            Log.d("FirebaseTag", "... i sad pokreće s popunjenom listom prikaz - Load Sucess");
            promijeniPrikazPodataka();
        }
        else Log.d("FirebaseTag", "... ali vraća praznu listu - Load Sucess");
    }

    @Override
    public void onLoadFail(String message) {
        Log.d("FirebaseTag", "Load Fail");
    }
}
