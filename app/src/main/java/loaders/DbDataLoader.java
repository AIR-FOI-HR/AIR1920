package loaders;

import android.content.Context;

import com.example.core.DataLoadedListener;
import com.example.core.DataLoader;
import com.example.culturearound.MainActivity;
import com.example.database.DAO;
import com.example.database.Entities.Korisnik;
import com.example.database.Entities.Lokacija;
import com.example.database.Entities.Znamenitost;
import com.example.database.MyDatabase;

import java.util.List;

public class DbDataLoader implements DataLoader {
    private boolean dataLoaded = false;

    public static MyDatabase database;
    Context context;

    public DbDataLoader(Context context) {
        this.context = context;
    }

    @Override
    public void loadData(DataLoadedListener listener) {
        DAO dao = MyDatabase.getInstance(context).getDAO();

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
