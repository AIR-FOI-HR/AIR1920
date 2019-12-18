package loaders;

import com.example.core.DataLoadedListener;
import com.example.core.DataLoader;
import com.example.culturearound.MainActivity;
import com.example.database.DAO;
import com.example.database.Entities.Korisnik;
import com.example.database.Entities.Lokacija;
import com.example.database.Entities.Znamenitost;

import java.util.List;

public class DbDataLoader implements DataLoader {
    private boolean dataLoaded = false;

    @Override
    public void loadData(DataLoadedListener listener) {
        DAO dao = MainActivity.database.getDAO();

        List<Korisnik> korisnici = dao.loadAllKorisnik();
        List<Znamenitost> znamenitosti = dao.loadAllZnamenitosti();
        List<Lokacija> lokacije = dao.loadAllLokacije();

        dataLoaded = true;

        //ako se dodaju novi potrebni podaci u DataLoadedListener ovdje ih treba isto dodati
        listener.onDataLoaded(korisnici, znamenitosti, lokacije);
    }

    @Override
    public boolean isDataLoaded() {
        return dataLoaded;
    }
}
