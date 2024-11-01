package be.com.learn.adminsys.b3q1_androidproject_jm.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import be.com.learn.adminsys.b3q1_androidproject_jm.R;
import be.com.learn.adminsys.b3q1_androidproject_jm.models.Bloc;

import java.util.ArrayList;
import java.util.List;

public class BlocListActivity extends AppCompatActivity {
    public static final String EXTRA_PARENT_BLOC = "parent_bloc";

    private RecyclerView recyclerViewBlocs;
    private BlocAdapter blocAdapter;
    private List<Bloc> allBlocs; // Liste complète des blocs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloc_list);

        recyclerViewBlocs = findViewById(R.id.recyclerViewBlocs);
        recyclerViewBlocs.setLayoutManager(new LinearLayoutManager(this));

        // Initialiser les données des blocs
        initializeBlocs();

        // Récupérer le bloc parent si présent
        Intent intent = getIntent();
        Bloc parentBloc = null;
        if (intent != null && intent.hasExtra(EXTRA_PARENT_BLOC)) {
            parentBloc = (Bloc) intent.getSerializableExtra(EXTRA_PARENT_BLOC);
            if (parentBloc != null) {
                Log.d("BlocListActivity", "Bloc parent reçu : " + parentBloc.getName());
            }
        }

        // Obtenir les blocs à afficher en fonction du bloc parent
        List<Bloc> blocsToDisplay = getBlocsToDisplay(parentBloc);

        // Configurer l'adaptateur avec les objets Bloc
        blocAdapter = new BlocAdapter(blocsToDisplay, new BlocAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Bloc bloc) {
                Log.d("BlocListActivity", "Bloc sélectionné : " + bloc.getName());

                if (bloc.getSubBlocs() != null && !bloc.getSubBlocs().isEmpty()) {
                    // Lancer BlocListActivity avec le bloc sélectionné comme parent
                    Intent intent = new Intent(BlocListActivity.this, BlocListActivity.class);
                    intent.putExtra(EXTRA_PARENT_BLOC, bloc);
                    startActivity(intent);
                } else {
                    // Aucun sous-bloc à afficher
                    Toast.makeText(BlocListActivity.this, "Aucun sous-bloc disponible pour " + bloc.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        recyclerViewBlocs.setAdapter(blocAdapter);
    }

    /**
     * Initialiser les données des blocs
     */
    private void initializeBlocs() {
        allBlocs = new ArrayList<>();

        // Création des blocs et sous-blocs
        // Bloc Parent 1
        Bloc bloc1 = new Bloc("bloc1", "Bloc 1", null, new ArrayList<>(), null);
        // Sous-bloc 1.1
        Bloc subBloc11 = new Bloc("bloc1.1", "SubBloc 1.1", null, new ArrayList<>(), bloc1);
        // Sous-bloc 1.2
        Bloc subBloc12 = new Bloc("bloc1.2", "SubBloc 1.2", null, new ArrayList<>(), bloc1);
        bloc1.getSubBlocs().add(subBloc11);
        bloc1.getSubBlocs().add(subBloc12);

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
    }

    /**
     * Obtenir la liste des blocs à afficher en fonction du bloc parent
     */
    private List<Bloc> getBlocsToDisplay(Bloc parentBloc) {
        List<Bloc> blocsToDisplay = new ArrayList<>();
        if (parentBloc == null) {
            // Afficher les blocs principaux (sans parent)
            for (Bloc bloc : allBlocs) {
                if (bloc.getParentBloc() == null) {
                    blocsToDisplay.add(bloc);
                }
            }
        } else {
            // Afficher les sous-blocs du bloc parent
            if (parentBloc.getSubBlocs() != null) {
                blocsToDisplay.addAll(parentBloc.getSubBlocs());
            }
        }
        return blocsToDisplay;
    }

    /**
     * Trouver un bloc par son nom
     */
    private Bloc getBlocByName(String blocName) {
        for (Bloc bloc : allBlocs) {
            if (bloc.getName().equals(blocName)) {
                return bloc;
            }
            // Recherche récursive dans les sous-blocs
            Bloc found = findBlocRecursively(bloc, blocName);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    /**
     * Recherche récursive d'un bloc par son nom au sein des sous-blocs
     */
    private Bloc findBlocRecursively(Bloc parent, String blocName) {
        if (parent.getSubBlocs() != null) {
            for (Bloc subBloc : parent.getSubBlocs()) {
                if (subBloc.getName().equals(blocName)) {
                    return subBloc;
                }
                Bloc found = findBlocRecursively(subBloc, blocName);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }
}
