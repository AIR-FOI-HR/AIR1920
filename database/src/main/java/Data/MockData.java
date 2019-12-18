package Data;

import android.content.Context;

import com.example.database.DAO;
import com.example.database.Entities.KategorijaZnamenitosti;
import com.example.database.Entities.Korisnik;
import com.example.database.Entities.Znamenitost;
import com.example.database.MyDatabase;

public class MockData {
    private static DAO dao;

    public static void writeData(Context context){
        dao = MyDatabase.getInstance(context).getDAO();

        Korisnik korisnik = new Korisnik();
        korisnik.setIme("Korisnik Ime");
        korisnik.setPrezime("Korisnik Prezime");
        korisnik.setEmail("korisnik1@gmail.com");
        korisnik.setKorisnicko_ime("korisnik1");
        korisnik.setLozinka("lozinka");

        //korisnik.setKorisnicko_ime(dao.insertKorisnici(korisnik));
        dao.insertKorisnici(korisnik);

        //kategorija znamenitosti
        KategorijaZnamenitosti kategorijaZnamenitosti = new KategorijaZnamenitosti();
        kategorijaZnamenitosti.setNaziv("Muzej");
        kategorijaZnamenitosti.setId_kategorija_znamenitosti((int)dao.insertKategorijaZnamenitosti(kategorijaZnamenitosti)[0]);

        //znamenitost
        Znamenitost znamenitost = new Znamenitost();
        znamenitost.setNaziv("Mona Lisa");
        znamenitost.setOpis("Stara cool slika");
        znamenitost.setAdresa("Ulica muzeja 22");
        znamenitost.setId_znamenitost((int)dao.insertZnamenitosti(znamenitost)[0]);


    }
}
