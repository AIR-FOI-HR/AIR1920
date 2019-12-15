package com.example.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.database.Entities.Favourites;
import com.example.database.Entities.KategorijaZnamenitosti;
import com.example.database.Entities.Komentar;
import com.example.database.Entities.Korisnik;
import com.example.database.Entities.KorisnikLokacija;
import com.example.database.Entities.Lokacija;
import com.example.database.Entities.OcjenaKomentar;
import com.example.database.Entities.Slika;
import com.example.database.Entities.SlikaGalerije;
import com.example.database.Entities.Znamenitost;

@Database(version = 1, entities = {Korisnik.class, Favourites.class, KategorijaZnamenitosti.class, Komentar.class,
        KorisnikLokacija.class, Lokacija.class, OcjenaKomentar.class, Slika.class, SlikaGalerije.class, Znamenitost.class},
        exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    public static final String NAME = "main";
    public static final int VERSION = 1;

    private static MyDatabase INSTANCE = null;

    public synchronized static MyDatabase getInstance(final Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    MyDatabase.class,
                    MyDatabase.NAME
            ).allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    //public abstract DAO getDAO();
}
