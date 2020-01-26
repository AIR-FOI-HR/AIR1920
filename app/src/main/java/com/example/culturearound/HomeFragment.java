package com.example.culturearound;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.core.CurrentActivity;
import com.example.culturearound.Firebase.EntitiesFirebase.Lokacija;
import com.example.culturearound.Firebase.Listeners.LokacijaListener;
import com.example.culturearound.Firebase.Listeners.ZnamenitostListener;
import com.example.culturearound.Firebase.LokacijaHelper;
import com.example.culturearound.Firebase.ZnamenitostiHelper;
import com.example.culturearound.PretrazivanjeZnamenitosti.recyclerview.ZnamenitostRecyclerAdapter;
import com.example.culturearound.Firebase.EntitiesFirebase.Znamenitost;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements View.OnClickListener, ZnamenitostListener, LokacijaListener {
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
    @BindView(R.id.btnLokacija)
    Button btnLokacija;
    @BindView(R.id.home_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.trazilica)
    SearchView trazilica;

    @BindDrawable(R.drawable.button_kategorija_on)
    Drawable button_kategorija_on;
    @BindDrawable(R.drawable.button_kategorija_off)
    Drawable button_kategorija_off;

    private List<Znamenitost> znamenitosti;
    private ZnamenitostRecyclerAdapter znamenitostRecyclerAdapter;
    private List<View> listaGumbaKategorije;
    private List<View> listaOdabranihKategorija;
    private List<Lokacija> listaLokacija;
    private List<Lokacija> listaOdabranihLokacija;

    private ZnamenitostiHelper znamenitostiHelper;
    private LokacijaHelper lokacijaHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
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

        //rad s tražilicom
        trazilica.setOnQueryTextListener(
            new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    trazilica.clearFocus();
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    pretraziTrazilicom();
                    return false;
                }
            }
        );
        trazilica.setOnCloseListener(
            new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    Log.d("trazilicaTag", "Close");
                    trazilica.clearFocus();
                    promijeniPrikazPodataka();
                    return false;
                }
            }
        );

        //rad s kategorijama
        listaGumbaKategorije = Arrays.asList(
                btnMuzej, btnGalerija, btnSpomenik, btnSetaliste, btnKazaliste, btnKino);
        for (View gumb: listaGumbaKategorije){
            gumb.setOnClickListener(this);
            gumb.setBackground(button_kategorija_on);
        }
        listaOdabranihKategorija = listaGumbaKategorije;

        //rad s lokacijama
        listaLokacija = new ArrayList<>();
        listaOdabranihLokacija = new ArrayList<>();
        btnLokacija.setOnClickListener(this::onClick);
        btnLokacija.setBackground(button_kategorija_off);
        lokacijaHelper = new LokacijaHelper(CurrentActivity.getActivity(), this);

        //rad s znamenitostima
        znamenitostiHelper = new ZnamenitostiHelper(CurrentActivity.getActivity(), this);
        znamenitostiHelper.dohvatiSveZnamenitosti();
    }

    private void pretraziTrazilicom(){
        Log.d("trazilicaTag", "On Click");
        List<Znamenitost> prikazaneZnamenitosti = new ArrayList<Znamenitost>();
        for (Znamenitost znamenitost: znamenitosti){
            if (znamenitost.getNaziv().toLowerCase().contains(trazilica.getQuery().toString().toLowerCase()))
                prikazaneZnamenitosti.add(znamenitost);
        }
        znamenitostRecyclerAdapter.setZnamenitosti(prikazaneZnamenitosti);
        znamenitostRecyclerAdapter.notifyDataSetChanged();
    }

    //promjenom odabira lokacije ili kategorija mijenjamo prikaz podataka unutar recyclerView-a
    public void promijeniPrikazPodataka(){
        if (!znamenitosti.isEmpty()){
            List<Znamenitost> prikazaneZnamenitosti = new ArrayList<Znamenitost>();

            if (!listaOdabranihKategorija.isEmpty())
                prikazaneZnamenitosti = filtrirajZnamenitostiPremaKategoriji(prikazaneZnamenitosti);

            if (!listaOdabranihLokacija.isEmpty() && !prikazaneZnamenitosti.isEmpty())
                prikazaneZnamenitosti = filtrirajZnamenitostiPremaLokaciji(prikazaneZnamenitosti);

            if (prikazaneZnamenitosti.isEmpty()){
                Log.d("FirebaseTag", "Ne postoje tražene znamenitosti");
                Toast.makeText(getActivity(), "Ne postoje tražene znamenitosti", Toast.LENGTH_SHORT).show();
            }
            znamenitostRecyclerAdapter.setZnamenitosti(prikazaneZnamenitosti);
            znamenitostRecyclerAdapter.notifyDataSetChanged();
        }
        else {
            Log.d("FirebaseTag", "Ni jedna znamenitost nije učitana.");
            Toast.makeText(getActivity(), "Ni jedna znamenitost nije učitana.", Toast.LENGTH_SHORT).show();
        }
    }

    private List<Znamenitost> filtrirajZnamenitostiPremaKategoriji(List<Znamenitost> prikazaneZnamenitosti){
        for (View kategorija: listaOdabranihKategorija){
            for (Znamenitost znamenitost: znamenitosti) {
                if (znamenitost.getIdKategorijaZnamenitosti() == Integer.valueOf((String) kategorija.getTag())){
                    prikazaneZnamenitosti.add(znamenitost);
                }
            }
        }
        return prikazaneZnamenitosti;
    }

    private List<Znamenitost> filtrirajZnamenitostiPremaLokaciji(List<Znamenitost> prikazaneZnamenitosti){
        List<Znamenitost> filtriraneZnamenitosti = new ArrayList<>();
        for (Znamenitost znamenitost: prikazaneZnamenitosti){
            for (Lokacija lokacija: listaOdabranihLokacija){
                if (znamenitost.getIdLokacija() == lokacija.getIdLokacija()) filtriraneZnamenitosti.add(znamenitost);
            }
        }
        return filtriraneZnamenitosti;
    }

    private void odabirLokacija(){
        int brojLokacija = listaLokacija.size();
        String[] poljeLokacija = new String[brojLokacija];
        final boolean[] oznaceneLokacije = new boolean[brojLokacija];
        int brojac = 0;
        for (Lokacija pojedinaLokacija: listaLokacija){
            poljeLokacija[brojac] = pojedinaLokacija.getNaziv();
            oznaceneLokacije[brojac] = false;
            brojac++;
        }
        //Stvaranje forme odabira Lokacija
        AlertDialog.Builder visestrukiOdabirLokacija = new AlertDialog.Builder(getActivity());
        visestrukiOdabirLokacija.setTitle("Odaberite lokacije");
        visestrukiOdabirLokacija.setMultiChoiceItems(poljeLokacija, oznaceneLokacije, new DialogInterface.OnMultiChoiceClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                oznaceneLokacije[which] = isChecked;
            }
        });
        visestrukiOdabirLokacija.setPositiveButton("Odaberi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tekstGumba = "";
                for (int i = 0; i<brojLokacija; i++){
                    if (oznaceneLokacije[i]){
                        for (Lokacija lokacija: listaLokacija){
                            if (poljeLokacija[i] == lokacija.getNaziv())
                                listaOdabranihLokacija.add(lokacija);
                        }
                        if (!btnLokacija.getBackground().equals(button_kategorija_on))
                            btnLokacija.setBackground(button_kategorija_on);
                        tekstGumba += poljeLokacija[i] + " ";
                    }
                }
                if (tekstGumba != "") btnLokacija.setText(tekstGumba);
                promijeniPrikazPodataka();
            }
        });
        visestrukiOdabirLokacija.setNeutralButton("Otkaži", null);
        AlertDialog dialog = visestrukiOdabirLokacija.create();
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v != btnLokacija){
            if (v.getBackground() == button_kategorija_on) v.setBackground(button_kategorija_off);
            else v.setBackground(button_kategorija_on);

            listaOdabranihKategorija = new ArrayList<>();
            for (View gumb: listaGumbaKategorije){
                if (gumb.getBackground() == button_kategorija_on){
                    listaOdabranihKategorija.add(gumb);
                }
            }
            promijeniPrikazPodataka();
        }
        else if (listaLokacija.isEmpty()){
            lokacijaHelper.dohvatiSveLokacije();
        }
        else {
            if (listaOdabranihLokacija.isEmpty()){
                odabirLokacija();
            }
            else {
                btnLokacija.setBackground(button_kategorija_off);
                btnLokacija.setText("Odaberi gradove");
                listaOdabranihLokacija.clear();
            }
            promijeniPrikazPodataka();
        }
    }

    @Override
    public void onLoadZnamenitostSucess(String message, List<Znamenitost> listaZnamenitosti) {
        Log.d("ZnamenitostTag", message);
        if (!listaZnamenitosti.isEmpty()){
            this.znamenitosti = listaZnamenitosti;
            promijeniPrikazPodataka();
        }
    }

    @Override
    public void onLoadZnamenitostFail(String message) {
        Log.d("ZnamenitostTag", message);
    }

    @Override
    public void onLoadLokacijaSucess(String message, List<Lokacija> listaLokacija) {
        this.listaLokacija = listaLokacija;
        Log.d("LokacijaTag", message);
        odabirLokacija();
    }

    @Override
    public void onLoadLokacijaFail(String message) {
        Log.d("LokacijaTag", message);
    }
}
