package be.com.learn.adminsys.b3q1_androidproject_jm.oldControllers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import be.com.learn.adminsys.b3q1_androidproject_jm.R;
import be.com.learn.adminsys.b3q1_androidproject_jm.oldModels.Bloc;
import be.com.learn.adminsys.b3q1_androidproject_jm.oldModels.DataCollector;
import be.com.learn.adminsys.b3q1_androidproject_jm.oldView.BlocViewController;

public class BlocListActivity extends AppCompatActivity implements BlocViewController.BlocViewControllerInterface {

    public static final String EXTRA_PARENT_BLOC = "parent_bloc";
    private BlocViewController blocViewController;
    private BlocAdapter blocAdapter;
    private List<Bloc> allBlocs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloc_list);

        // Initialisation du ViewController
        blocViewController = new BlocViewController(findViewById(R.id.main), this);

        // Utiliser DataCollector pour initialiser les données des blocs
        DataCollector dataCollector = new DataCollector();
        allBlocs = dataCollector.collectAllBlocs();

        // Récupérer le bloc parent si présent
        Intent intent = getIntent();
        Bloc parentBloc = null;
        if (intent != null && intent.hasExtra(EXTRA_PARENT_BLOC)) {
            parentBloc = (Bloc) intent.getSerializableExtra(EXTRA_PARENT_BLOC);
            if (parentBloc != null) {
                Log.d("BlocListActivity", "Bloc parent reçu : " + parentBloc.getName());
            }
        }

        // Obtenir la liste des blocs à afficher en fonction du bloc parent
        List<Bloc> blocsToDisplay = getBlocsToDisplay(parentBloc);

        // Configurer l'adaptateur avec les blocs à afficher et définir l'action de clic
        blocAdapter = new BlocAdapter(blocsToDisplay, bloc -> {
            Log.d("BlocListActivity", "Bloc sélectionné : " + bloc.getName());
            if (bloc.getSubBlocs() != null && !bloc.getSubBlocs().isEmpty()) {
                // Lancer une nouvelle instance de BlocListActivity pour afficher les sous-blocs
                Intent newIntent = new Intent(BlocListActivity.this, BlocListActivity.class);
                newIntent.putExtra(EXTRA_PARENT_BLOC, bloc);
                startActivity(newIntent);
            } else {
                blocViewController.afficherMessage("Aucun sous-bloc disponible pour " + bloc.getName());
            }
        });

        // Utiliser le ViewController pour afficher les blocs
        blocViewController.afficherBlocs(blocsToDisplay, blocAdapter);
    }

    /**
     * Obtenir la liste des blocs à afficher en fonction du bloc parent.
     */
    private List<Bloc> getBlocsToDisplay(Bloc parentBloc) {
        List<Bloc> blocsToDisplay = new ArrayList<>();
        if (parentBloc == null) {
            // Afficher les blocs principaux (ceux sans parent)
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

    @Override
    public void btnAddBlocClicked() {
        blocViewController.afficherMessage("Ajouter un bloc");
    }

    @Override
    public void btnModifyStudentClicked() {
        blocViewController.afficherMessage("Modifier un étudiant");
    }
}
