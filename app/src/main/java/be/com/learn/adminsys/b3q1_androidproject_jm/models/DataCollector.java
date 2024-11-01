package be.com.learn.adminsys.b3q1_androidproject_jm.models;

import java.util.ArrayList;
import java.util.List;

public class DataCollector {

    // Méthode pour initialiser et retourner la liste complète des blocs avec leurs sous-blocs
    public List<Bloc> collectAllBlocs() {
        List<Bloc> allBlocs = new ArrayList<>();

        // Création des blocs et sous-blocs
        // Bloc Parent 1
        Bloc bloc1 = new Bloc("bloc1", "Bloc 1", null, new ArrayList<>(), null);
        // Sous-bloc 1.1
        Bloc subBloc11 = new Bloc("bloc1.1", "SubBloc 1.1", null, new ArrayList<>(), bloc1);
        // Sous-bloc 1.2
        Bloc subBloc12 = new Bloc("bloc1.2", "SubBloc 1.2", null, new ArrayList<>(), bloc1);
        bloc1.getSubBlocs().add(subBloc11);
        bloc1.getSubBlocs().add(subBloc12);

        // Sous-bloc 1.2.1
        Bloc subBloc121 = new Bloc("bloc1.2.1", "SubBloc 1.2.1", null, new ArrayList<>(), subBloc12);
        subBloc12.getSubBlocs().add(subBloc121);

        // Bloc Parent 2
        Bloc bloc2 = new Bloc("bloc2", "Bloc 2", null, new ArrayList<>(), null);
        // Sous-bloc 2.1
        Bloc subBloc21 = new Bloc("bloc2.1", "SubBloc 2.1", null, new ArrayList<>(), bloc2);
        bloc2.getSubBlocs().add(subBloc21);

        // Bloc Parent 3 sans sous-blocs
        Bloc bloc3 = new Bloc("bloc3", "Bloc 3", null, new ArrayList<>(), null);

        // Ajouter les blocs principaux à la liste
        allBlocs.add(bloc1);
        allBlocs.add(bloc2);
        allBlocs.add(bloc3);

        return allBlocs;
    }
}
