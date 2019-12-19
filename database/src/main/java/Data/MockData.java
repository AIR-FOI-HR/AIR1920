package Data;

import android.content.Context;

import com.example.database.DAO;
import com.example.database.Entities.KategorijaZnamenitosti;
import com.example.database.Entities.Korisnik;
import com.example.database.Entities.Lokacija;
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

        kategorijaZnamenitosti = new KategorijaZnamenitosti();
        kategorijaZnamenitosti.setNaziv("Galerija");
        kategorijaZnamenitosti.setId_kategorija_znamenitosti((int)dao.insertKategorijaZnamenitosti(kategorijaZnamenitosti)[0]);

        kategorijaZnamenitosti = new KategorijaZnamenitosti();
        kategorijaZnamenitosti.setNaziv("Spomenici");
        kategorijaZnamenitosti.setId_kategorija_znamenitosti((int)dao.insertKategorijaZnamenitosti(kategorijaZnamenitosti)[0]);

        kategorijaZnamenitosti = new KategorijaZnamenitosti();
        kategorijaZnamenitosti.setNaziv("Šetalište");
        kategorijaZnamenitosti.setId_kategorija_znamenitosti((int)dao.insertKategorijaZnamenitosti(kategorijaZnamenitosti)[0]);

        //lokacija
        Lokacija novaLokacija = new Lokacija();
        novaLokacija.setNaziv("Varaždin");
        novaLokacija.setId_slika(2);
        novaLokacija.setId_lokacija((int)dao.insertLokacije(novaLokacija)[0]);

        novaLokacija = new Lokacija();
        novaLokacija.setNaziv("Zagreb");
        novaLokacija.setId_slika(2);
        novaLokacija.setId_lokacija((int)dao.insertLokacije(novaLokacija)[0]);

        novaLokacija = new Lokacija();
        novaLokacija.setNaziv("Rijeka");
        novaLokacija.setId_slika(2);
        novaLokacija.setId_lokacija((int)dao.insertLokacije(novaLokacija)[0]);

        novaLokacija = new Lokacija();
        novaLokacija.setNaziv("Osijek");
        novaLokacija.setId_slika(2);
        novaLokacija.setId_lokacija((int)dao.insertLokacije(novaLokacija)[0]);

        novaLokacija = new Lokacija();
        novaLokacija.setNaziv("Dubrovnik");
        novaLokacija.setId_slika(2);
        novaLokacija.setId_lokacija((int)dao.insertLokacije(novaLokacija)[0]);



        //znamenitost
        Znamenitost znamenitost = new Znamenitost();
        znamenitost.setNaziv("Spomenik");
        znamenitost.setOpis("Spomenik");
        znamenitost.setAdresa("Ulica spomenika");
        znamenitost.setId_slika(3);
        znamenitost.setId_kategorija_znamenitosti(3);
        znamenitost.setId_lokacija(1);
        znamenitost.setId_znamenitost((int)dao.insertZnamenitosti(znamenitost)[0]);

        znamenitost = new Znamenitost();
        znamenitost.setNaziv("Kip Grgura Ninskog1");
        znamenitost.setOpis("Kip Grgura Ninskog opis");
        znamenitost.setAdresa("Franjevački trg, Varaždin");
        znamenitost.setId_slika(3);
        znamenitost.setId_kategorija_znamenitosti(3);
        znamenitost.setId_lokacija(1);
        znamenitost.setId_znamenitost((int)dao.insertZnamenitosti(znamenitost)[0]);

        znamenitost = new Znamenitost();
        znamenitost.setNaziv("Gradski muzej Varaždin1");
        znamenitost.setOpis("Gradski muzej Varaždin opis");
        znamenitost.setAdresa("Šetalište Josipa Jurja Strossmayera 1, Varaždin");
        znamenitost.setId_slika(3);
        znamenitost.setId_kategorija_znamenitosti(1);
        znamenitost.setId_lokacija(1);
        znamenitost.setId_znamenitost((int)dao.insertZnamenitosti(znamenitost)[0]);

        znamenitost = new Znamenitost();
        znamenitost.setNaziv("Šetalište Josipa Jurja Strossmayera");
        znamenitost.setOpis("Šetalište Josipa Jurja Strossmayera");
        znamenitost.setAdresa("Šetalište Josipa Jurja Strossmayera");
        znamenitost.setId_slika(3);
        znamenitost.setId_kategorija_znamenitosti(4);
        znamenitost.setId_lokacija(1);
        znamenitost.setId_znamenitost((int)dao.insertZnamenitosti(znamenitost)[0]);

        znamenitost = new Znamenitost();
        znamenitost.setNaziv("JosjedanMutej2");
        znamenitost.setOpis("Gradski muzej Varaždin opis");
        znamenitost.setAdresa("Šetalište Josipa Jurja Strossmayera 1, Varaždin");
        znamenitost.setId_slika(3);
        znamenitost.setId_kategorija_znamenitosti(1);
        znamenitost.setId_lokacija(1);
        znamenitost.setId_znamenitost((int)dao.insertZnamenitosti(znamenitost)[0]);

        znamenitost = new Znamenitost();
        znamenitost.setNaziv("JosjedanMutej3");
        znamenitost.setOpis("Gradski muzej Varaždin opis");
        znamenitost.setAdresa("JosjedanMutej3");
        znamenitost.setId_slika(3);
        znamenitost.setId_kategorija_znamenitosti(1);
        znamenitost.setId_lokacija(1);
        znamenitost.setId_znamenitost((int)dao.insertZnamenitosti(znamenitost)[0]);
    }
}
