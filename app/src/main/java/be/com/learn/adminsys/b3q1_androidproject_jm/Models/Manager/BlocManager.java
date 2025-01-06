package be.com.learn.adminsys.b3q1_androidproject_jm.Models.Manager;

import java.util.List;

import be.com.learn.adminsys.b3q1_androidproject_jm.Database.AppDatabase;
import be.com.learn.adminsys.b3q1_androidproject_jm.Database.DAOs.BlocDao;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Bloc;

public class BlocManager {

    private BlocDao blocDao;

    public BlocManager(AppDatabase db) {
        this.blocDao = db.blocDao();
    }

    // Récupérer tous les blocs
    public List<Bloc> getAllBlocs() {
        return blocDao.getAllBlocs();
    }

    // Ajouter un bloc
    public void addBloc(String blocName) {
        Bloc addedBloc = new Bloc(blocName);
        blocDao.insert(addedBloc);
    }
}
