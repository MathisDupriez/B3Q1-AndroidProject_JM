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

import be.com.learn.adminsys.b3q1_androidproject_jm.Controller.BlocAdapter;
import be.com.learn.adminsys.b3q1_androidproject_jm.Database.DatabaseCollector;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.NewBloc;
import be.com.learn.adminsys.b3q1_androidproject_jm.R;

public class BlocFragment extends Fragment {

    private RecyclerView recyclerViewBlocs;
    private BlocAdapter blocAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bloc, container, false);
        recyclerViewBlocs = view.findViewById(R.id.recyclerViewBlocs);
        recyclerViewBlocs.setLayoutManager(new LinearLayoutManager(requireContext()));

        loadBlocs();
        return view;
    }

    private void loadBlocs() {
        // Simule une liste de blocs (remplace par des données réelles si nécessaire)
        List<NewBloc> blocs = DatabaseCollector.getBlocs();
        // ajoute des cours à chaque bloc
        blocAdapter = new BlocAdapter(blocs, bloc -> navigateToCourses(bloc));
        recyclerViewBlocs.setAdapter(blocAdapter);
    }

    private void navigateToCourses(NewBloc bloc) {
        // Naviguer vers CourseFragment avec le bloc sélectionné
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
