package com.example.core;

import com.example.database.Entities.Korisnik;
import com.example.database.Entities.Lokacija;
import com.example.database.Entities.Znamenitost;

import java.util.List;

public interface DataLoadedListener {

    //ostale dodati po potrebi, update DbDataLoader klasu

    void onDataLoaded(
            List<Korisnik> korisnici,
            List<Znamenitost> znamenitosti,
            List<Lokacija> lokacije);

}
