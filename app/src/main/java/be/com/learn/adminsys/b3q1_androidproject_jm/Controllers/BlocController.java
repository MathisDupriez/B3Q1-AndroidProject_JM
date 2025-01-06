package be.com.learn.adminsys.b3q1_androidproject_jm.Controllers;

import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Bloc;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Manager.BlocManager;
import be.com.learn.adminsys.b3q1_androidproject_jm.Views.Adapter.BlocAdapter;
import java.util.List;
import java.util.concurrent.Executors;

public class BlocController {

    private BlocManager blocManager;
    private FragmentActivity activity;
    private BlocAdapter blocAdapter;

    public BlocController(BlocManager blocManager, FragmentActivity activity, BlocAdapter blocAdapter) {
        this.blocManager = blocManager;
        this.activity = activity;
        this.blocAdapter = blocAdapter;
    }

    // Charger les blocs depuis le modèle et les mettre à jour dans la vue
    public void loadBlocs() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Bloc> blocs = blocManager.getAllBlocs();
            // Revenir sur le thread principal pour mettre à jour l'adaptateur
            activity.runOnUiThread(() -> blocAdapter.updateBlocs(blocs));
        });
    }

    // Ajouter un bloc et mettre à jour la vue
    public void addBloc(String blocName) {
        if (!blocName.isEmpty()) {
            Executors.newSingleThreadExecutor().execute(() -> {
                blocManager.addBloc(blocName);
                loadBlocs();  // Recharger les blocs après ajout
            });
        } else {
            activity.runOnUiThread(() -> Toast.makeText(activity, "Le nom du bloc ne peut pas être vide", Toast.LENGTH_SHORT).show());
        }
    }
}
