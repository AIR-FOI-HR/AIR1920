package com.example.database;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.database.Entities.Korisnik;

import java.util.List;

public interface DAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)


    @Query("SELECT * FROM korisnik")
    public List<Korisnik> loadAllKorisnik();

    @Query("SELECT * FROM korisnik WHERE korisnicko_ime = :korIme")
    public List<Korisnik> loadKorisnik(int korIme);


}
