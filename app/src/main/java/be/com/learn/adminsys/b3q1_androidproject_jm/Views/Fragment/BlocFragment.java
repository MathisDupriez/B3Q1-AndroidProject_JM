package be.com.learn.adminsys.b3q1_androidproject_jm.Views.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import android.view.Gravity;
import android.widget.PopupWindow;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import be.com.learn.adminsys.b3q1_androidproject_jm.Controllers.BlocController;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Bloc;
import be.com.learn.adminsys.b3q1_androidproject_jm.Database.AppDatabase;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Manager.BlocManager;
import be.com.learn.adminsys.b3q1_androidproject_jm.R;
import be.com.learn.adminsys.b3q1_androidproject_jm.Views.Adapter.BlocAdapter;

public class BlocFragment extends Fragment {

    private RecyclerView recyclerViewBlocs;
    private BlocAdapter blocAdapter;
    private BlocController blocController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bloc, container, false);

        // Initialiser les vues
        recyclerViewBlocs = view.findViewById(R.id.recyclerViewBlocs);
        recyclerViewBlocs.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Initialisation du modèle et du contrôleur
        AppDatabase db = AppDatabase.getInstance(requireContext());
        BlocManager blocManager = new BlocManager(db);
        blocAdapter = new BlocAdapter(new ArrayList<>(), bloc -> navigateToCourses(bloc));
        recyclerViewBlocs.setAdapter(blocAdapter);
        blocController = new BlocController(blocManager, requireActivity(), blocAdapter);

        // Charger les blocs
        blocController.loadBlocs();

        // Configurer le bouton d'ajout
        view.findViewById(R.id.addBlocButton).setOnClickListener(this::showAddBlocDialog);

        return view;
    }

    private void showAddBlocDialog(View v) {
        // Affichage du popup pour ajouter un bloc
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View popupView = inflater.inflate(R.layout.popup_add_bloc, null);

        PopupWindow popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);

        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

        EditText editTextBlocName = popupView.findViewById(R.id.editTextBlocName);
        Button confirmButton = popupView.findViewById(R.id.confirmButton);
        Button cancelButton = popupView.findViewById(R.id.cancelButton);

        confirmButton.setOnClickListener(view -> {
            String blocName = editTextBlocName.getText().toString();
            if (!blocName.isEmpty()) {
                blocController.addBloc(blocName);
                popupWindow.dismiss();
            } else {
                Toast.makeText(requireContext(), "Le nom du bloc ne peut pas être vide", Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener(view -> popupWindow.dismiss());
    }

    private void navigateToCourses(Bloc bloc) {
        Bundle args = new Bundle();
        args.putSerializable("selectedBloc", bloc);
        CourseFragment fragment = new CourseFragment();
        fragment.setArguments(args);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
