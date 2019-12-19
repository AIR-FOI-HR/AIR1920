package loaders;

import com.example.core.DataLoader;
import com.example.culturearound.MainActivity;
import com.example.database.Entities.Znamenitost;

import java.util.List;

public class DataLoaderFactory {
    public static DataLoader getDataLoader(){
        DataLoader dataLoader = null;
        List<Znamenitost> znamenitosti = MainActivity.database.getDAO().loadAllZnamenitosti();
        if(!znamenitosti.isEmpty()){
            dataLoader = new DbDataLoader();
        } else {
            //Napraviti WsDataLoader
            //dataLoader = new WsDataLoader();
        }
        return dataLoader;
    }
}
