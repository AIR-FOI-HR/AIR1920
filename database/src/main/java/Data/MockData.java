package Data;

import android.content.Context;

import com.example.database.DAO;
import com.example.database.Entities.Korisnik;
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
    }
}
