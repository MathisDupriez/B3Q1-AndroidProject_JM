package be.com.learn.adminsys.b3q1_androidproject_jm.Fragment;

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
import java.util.HashMap;
import java.util.List;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import be.com.learn.adminsys.b3q1_androidproject_jm.Controller.BlocAdapter;
import be.com.learn.adminsys.b3q1_androidproject_jm.Database.DatabaseCollector;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.NewBloc;
import be.com.learn.adminsys.b3q1_androidproject_jm.R;

public class BlocFragment extends Fragment {

    private RecyclerView recyclerViewBlocs;
    private BlocAdapter blocAdapter;
    private List<NewBloc> blocs = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bloc, container, false);

        recyclerViewBlocs = view.findViewById(R.id.recyclerViewBlocs);
        recyclerViewBlocs.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Initialisation du bouton
        view.findViewById(R.id.addBlocButton).setOnClickListener(v -> showAddBlocDialog(v));

        loadBlocs();
        return view;
    }

    private void loadBlocs() {
        // Charge les blocs depuis la base de données ou crée une liste vide
        blocs = DatabaseCollector.getBlocs();
        blocAdapter = new BlocAdapter(blocs, bloc -> navigateToCourses(bloc));
        recyclerViewBlocs.setAdapter(blocAdapter);
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
                NewBloc newBloc = new NewBloc(blocName, new HashMap<>(), new ArrayList<>());
                blocs.add(newBloc);
                blocAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
            } else {
                Toast.makeText(requireContext(), "Le nom du bloc ne peut pas être vide", Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener(view -> popupWindow.dismiss());
    }

    private void navigateToCourses(NewBloc bloc) {
        // Navigue vers le fragment CourseFragment
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