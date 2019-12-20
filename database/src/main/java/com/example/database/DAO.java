package com.example.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

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

import java.util.List;

@Dao
public interface DAO {
    //CRD nad Favourites - medjutablica
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertFavourites(Favourites... favourites);
    @Query("SELECT Z.* FROM znamenitost Z, favourites F " +
            "WHERE Z.id_znamenitost = F.id_znamenitost " +
            "AND :korime = F.korisnicko_ime")
    public List<Znamenitost> loadFavouriteZnamenitostiByKorisnik(int korime);
    @Delete public void deleteFavourite(Favourites... favourites);

    //CRUD nad KategorijaZnamenitosti
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertKategorijaZnamenitosti(KategorijaZnamenitosti... kategorijeZnamenitosti);
    @Query("SELECT * FROM kategorijaZnamenitosti")
    public List<KategorijaZnamenitosti> loadAllKategorijaZnamenitosti();
    @Update public void updateKategorijaZnamenitosti(KategorijaZnamenitosti... kategorijeZnamenitosti);
    @Delete public void deleteKategorijaZnamenitosti(KategorijaZnamenitosti... kategorijeZnamenitosti);

    //CRUD na Komentarima
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertKomentari(Komentar... komentari);
    @Query("SELECT * FROM komentar")
    public List<Komentar> loadAllKomentari();
    @Query("SELECT * FROM komentar WHERE id_komentar = :korIme")
    public List<Komentar> loadKomentariByKorisnik(int korIme);
    @Query("SELECT * FROM komentar WHERE id_znamenitost = :idZnamenitost")
    public List<Komentar> loadKomentariByZnamenitost(int idZnamenitost);
    @Update public void updateKomentari(Komentar... komentari);
    @Delete public void deleteKomentari(Komentar... komentari);

    //CRUD nad Korisnik
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertKorisnici(Korisnik... korisnici);
    @Query("SELECT * FROM korisnik")
    public List<Korisnik> loadAllKorisnik();
    @Query("SELECT * FROM korisnik WHERE korisnicko_ime = :korIme")
    public List<Korisnik> loadKorisnik(int korIme);
    @Query("SELECT korisnicko_ime FROM korisnik")
    public List<String> getKorisnickoIme();
    @Update public void updateKorisnici(Korisnik... korisnici);
    @Delete public void deleteKorisnici(Korisnik... korisnici);

    //CRD nad KorisnikLokacija - medjutablica
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertKorisnickuLokaciju(KorisnikLokacija... korisnikoveLokacije);
    @Query("SELECT KL.* FROM korisnikLokacija KL, lokacija L WHERE KL.id_lokacija = L.id_lokacija AND KL.korisnicko_ime = :korime")
    public List<KorisnikLokacija> loadLokacijaByKorisnik(String korime);
    @Delete public void deleteLokacija(KorisnikLokacija... korisnikoveLokacije);

    //CRUD nad Lokacija
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertLokacije(Lokacija... lokacije);
    @Query("SELECT * FROM lokacija")
    public List<Lokacija> loadAllLokacije();
    @Update public void updateLokacije(Lokacija... lokacije);
    @Delete public void deleteLokacije(Lokacija... lokacije);

    //CRD nad OcjenaKomentar - medjutablica
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertOcjenaKomentar(OcjenaKomentar... ocjenaKomentara);
    @Query("SELECT * FROM ocjenaKomentar WHERE id_komentar = :id_komentar")
    public List<OcjenaKomentar> loadOcjeneKomentaraByKomentar(int id_komentar);
    @Delete public void deleteLokacija(OcjenaKomentar... ocjenaKomentara);

    //CRUD nad Slika
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertSlika(Slika... slike);
    @Query("SELECT * FROM slika")
    public List<Slika> loadAllSlike();
    @Update public void updateSlika(Slika... slike);
    @Delete public void deleteSlika(Slika... slike);

    //CRD nad SlikaGalerije - medjutablica
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertSlikeGalerije(SlikaGalerije... slikeGalerije);
    @Query("SELECT * FROM slikaGalerije WHERE id_znamenitost = :id_znamenitost")
    public List<SlikaGalerije> loadSlikeGalerijeByZnamenitost(int id_znamenitost);
    @Delete public void deleteSlikeGalerije(SlikaGalerije... slikeGalerije);

    //CRUD nad znamenitostima
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertZnamenitosti(Znamenitost... znamenitosti);
    @Query("SELECT * FROM znamenitost WHERE id_znamenitost = :id_znam")
    public Znamenitost loadZnamenitostiById(int id_znam);
    @Query("SELECT * FROM znamenitost")
    public List<Znamenitost> loadAllZnamenitosti();
    @Update public void updateZnamenitosti(Znamenitost... znamenitosti);
    @Delete public void deleteZnamenitosti(Znamenitost... znamenitosti);
}
