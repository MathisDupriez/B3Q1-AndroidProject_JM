package be.com.learn.adminsys.b3q1_androidproject_jm.Fragments;

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
import java.util.List;
import java.util.concurrent.Executors;

import android.view.Gravity;
import android.widget.PopupWindow;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import be.com.learn.adminsys.b3q1_androidproject_jm.Controllers.BlocAdapter;
import be.com.learn.adminsys.b3q1_androidproject_jm.Database.AppDatabase;
import be.com.learn.adminsys.b3q1_androidproject_jm.Database.DAOs.BlocDao;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Bloc;


import be.com.learn.adminsys.b3q1_androidproject_jm.R;

public class BlocFragment extends Fragment {

    private RecyclerView recyclerViewBlocs;
    private BlocAdapter blocAdapter;
    private List<Bloc> blocs = new ArrayList<>();
    private AppDatabase db;
    private BlocDao blocDao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bloc, container, false);

        recyclerViewBlocs = view.findViewById(R.id.recyclerViewBlocs);
        recyclerViewBlocs.setLayoutManager(new LinearLayoutManager(requireContext()));

        db = AppDatabase.getInstance(requireContext());
        blocDao = db.blocDao();

        view.findViewById(R.id.addBlocButton).setOnClickListener(this::showAddBlocDialog);

        loadBlocs();
        return view;
    }

    private void loadBlocs() {
        Executors.newSingleThreadExecutor().execute(() -> {
            // Récupération des blocs depuis la base de données en arrière-plan
            blocs = blocDao.getAllBlocs();

            requireActivity().runOnUiThread(() -> {
                // Mise à jour de l'adaptateur sur le thread principal
                blocAdapter = new BlocAdapter(blocs, bloc -> navigateToCourses(bloc));
                recyclerViewBlocs.setAdapter(blocAdapter);
            });
        });
    }

    private void showAddBlocDialog(View v) {
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
                addBloc(blocName);
                popupWindow.dismiss();
            } else {
                Toast.makeText(requireContext(), "Le nom du bloc ne peut pas être vide", Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener(view -> popupWindow.dismiss());
    }

    private void addBloc(String blocName) {
        Executors.newSingleThreadExecutor().execute(() -> {
            // Ajout du bloc en arrière-plan
            Bloc addedBloc = new Bloc(blocName);
            blocDao.insert(addedBloc);

            // Conversion et ajout à la liste métier
            loadBlocs();
            requireActivity().runOnUiThread(() -> {
                // Mise à jour de l'adaptateur sur le thread principal
                blocAdapter.notifyDataSetChanged();
            });
        });
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
